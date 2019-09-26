package com.qf.controller;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.google.gson.Gson;
import com.qf.entity.Constant;
import com.qf.entity.Email;
import com.qf.entity.ResultEntity;
import com.qf.entity.User;
import com.qf.service.ICartService;
import com.qf.service.IUserService;
import com.qf.utils.PasswordUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value = "/sso")
public class EmailController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Reference
    private IUserService userService;

    @Autowired
    private RedisTemplate redisTemplate;
    @Reference
    private ICartService cartService;

    @RequestMapping("/sendEmail")
    @ResponseBody
    public void sendEmail(String email){
        int code = (int) (Math.random() * 10000) + 1000;
        String content = "验证码:%d,如果非本人操作，请忽略.";
        content = String.format(content,code);
        Email email1 = new Email("4399验证码",content,email);
        System.out.println(code);
        redisTemplate.opsForValue().set(email+Constant.EMAIL_CODE,code,30, TimeUnit.SECONDS);
       // rabbitTemplate.convertAndSend("emailExchange","",email1);

    }

    @RequestMapping(value = "/register")
    @ResponseBody
    public ResultEntity<String> register(User user, Integer code) {

        // 1.校验验证码
        Integer redisCode = (Integer) redisTemplate.opsForValue().get(user.getEmail() + Constant.EMAIL_CODE);
        System.out.println(redisCode);
        if (redisCode != null && redisCode.equals(code)) {

            int state = userService.register(user); // 判断用户名和邮箱是否被注册，
            System.out.println(state);
            if (state == 1) {
                return ResultEntity.FAILD("邮箱已经被注册");
            } else if (state == 2) {
                return ResultEntity.FAILD("用户名已经被注册");
            } else if (state == 3) {
                return ResultEntity.SEUCCESS_URL("http://localhost:8084/toLogin");
            }
        }
        return ResultEntity.FAILD("验证码失效");
    }

    @RequestMapping(value = "/findEmailByUsername")
    public String findEmailByUsername(String username, Model model) {
        User user = userService.getUserByUsername(username);
        if (user!=null){
            String email = user.getEmail();
            String emailTemp = email.replace(email.substring(4, email.indexOf("@")), "*****");

            String emailUrl = email.replace(email.substring(0, email.indexOf("@") + 1), "mail.");
            String toke = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(username+Constant.PWTOKEN,toke,5,TimeUnit.MINUTES);
            String passwordUrl ="http://127.0.0.1:8084/toChangePassword?username="+username+"&token="+toke;

            Email emailEntity = new Email();
            emailEntity.setTilte("技嘉aero15x");
            emailEntity.setContent("密码修改连接:<a href = '" + passwordUrl + "'>点击这里这里修改</a>");
            emailEntity.setTo(email);

            rabbitTemplate.convertAndSend(Constant.EMAILEXCHANGE,"",emailEntity);

            model.addAttribute("msg", "连接已发送到" + emailTemp + "的邮箱，请<a href='http://" + emailUrl + "'>登录</a>");
        }
        else {
            model.addAttribute("msg", "该【" + username + "】不存在");
        }
        return "updatePassword";
    }

    @RequestMapping(value = "/changePassword")
    public String changePassword(String newPassword, String username, String token) {
        System.out.println("SSOController.changePassword");
        // 1.先验证
        String redisToke = (String) redisTemplate.opsForValue().get(username+Constant.PWTOKEN);
        System.out.println(redisToke);
        if (redisToke != null && redisToke.equals(token)) {
            User user = userService.getUserByUsername(username);
            user.setPassword(newPassword);
            userService.udpate(user); // 修改密码
            redisTemplate.delete(username + Constant.PWTOKEN);
            // sso/chxxxx
            return "redirect:/toLogin";
        }
        return "error";
    }
    @RequestMapping(value = "/login")
    @ResponseBody
    public ResultEntity<String> login(String username, String password, HttpServletResponse response,String returnUrl,
     @CookieValue(name = Constant.CART_TOKEN,required = false) String cartToken) {

        // 1.验证用户
        User user = userService.login(username);

        if (user != null) {

            // 密码的对比
            if (PasswordUtils.checkpw(password, user.getPassword())) {

                if(!StringUtils.isEmpty(cartToken)){
                    int state = cartService.mergeCart(cartToken,user);

                    if(state > 0){
                        // 干掉之前的cookie
                        Cookie cookie = new Cookie(Constant.CART_TOKEN,"");
                        cookie.setMaxAge(0);
                        response.addCookie(cookie);
                    }
                }

                // 生成一个凭证
                String loginToken = UUID.randomUUID().toString();

                // 把凭证存到reids中
                redisTemplate.opsForValue().set(loginToken, user, 5, TimeUnit.DAYS);

                // 创建cookie把凭证保存到cookie中
                Cookie cookie = new Cookie(Constant.LOGIN_TOKEN, loginToken);
                cookie.setPath("/");
                cookie.setMaxAge(60 * 60 * 24 * 5);

                response.addCookie(cookie);
                if(StringUtils.isEmpty(returnUrl)){
//                  return ResultEntity.SEUCCESS_URL(returnUrl);
                    returnUrl = "http://localhost:8081/";
                }
                return ResultEntity.SEUCCESS_URL(returnUrl);
            } else {
                return ResultEntity.FAILD("用户名或密码错误");
            }
        } else {
            return ResultEntity.FAILD("用户名不存在");
        }
    }

    @RequestMapping(value = "/checkLogin")
    @ResponseBody
    public String checkLogin(String callback, @CookieValue(name = Constant.LOGIN_TOKEN,required = false)String loginToken){
        String userJSONstr = "";

        if (loginToken!=null){
            User user = (User) redisTemplate.opsForValue().get(loginToken);
            if (user!=null){
                userJSONstr = new Gson().toJson(user);
            }
        }
        return callback!=null?callback+"('"+userJSONstr+"')":userJSONstr;
    }

    @RequestMapping(value = "/logout")
    public String logout(String callback,@CookieValue(name = Constant.LOGIN_TOKEN,required = false)String loginToken,HttpServletResponse response){

        if (loginToken!=null){
            redisTemplate.delete(loginToken);
            Cookie cookie  = new Cookie(Constant.LOGIN_TOKEN,"");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return "redirect:../toLogin";
    }
}