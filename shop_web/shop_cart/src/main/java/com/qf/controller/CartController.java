package com.qf.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.aop.IsLogin;
import com.qf.entity.Cart;
import com.qf.entity.Constant;
import com.qf.entity.User;
import com.qf.service.ICartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Reference
    private ICartService cartService;

    @RequestMapping("/addCart")
    @IsLogin
    @ResponseBody
    public  String addcart(Cart cart, User user, HttpServletResponse response, @CookieValue(name = Constant.CART_TOKEN,required = false) String cartToken){
        if (StringUtils.isEmpty(cartToken)){
            cartToken= UUID.randomUUID().toString();
            Cookie cookie = new Cookie("xxxxxx",cartToken);
            cookie.setMaxAge(60*60*24*5);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
        }
        System.out.println(cart);
        System.out.println(user);
        System.out.println("3"+cartToken);
        cartService.addCart(cartToken,user,cart);
        return "ok";
    }

    @RequestMapping(value = "/findCartCount")
    @ResponseBody
    @IsLogin
    public String findCartCount(User user,@CookieValue(name = Constant.CART_TOKEN,required = false) String cartToken,String callback){
        int count = cartService.findCartCount(cartToken,user);
        System.out.println(count);
        return callback != null? callback+"('"+count+"')":count+"";
    }

    @RequestMapping(value = "/getCartList")
    public String getCartList(User user, @CookieValue(name = Constant.CART_TOKEN,required = false) String cartToken, ModelMap map){
        List<Cart> cartList = cartService.getCartList(cartToken,user);

        // 2.去重复
        Set<Integer> set = new HashSet<Integer>();
        Map<Integer,Cart> cartMap= new HashMap<>();

        for (Cart cart:cartList){
            if (set.add(cart.getGid())){
                cartMap.put(cart.getGid(),cart);
            }else{
                // 如果进来这里说明有重复的
                Integer num = cartMap.get(cart.getGid()).getNum(); // 之前商品数量
                cart.setNum(num+cart.getNum()); // 算出新的数量
                cart.setSubTotal(cart.getNum()*cart.getGoods().getGprice());
                cartMap.put(cart.getGid(),cart); // 覆盖之前的购物车对象
            }
        }
        if(!cartMap.isEmpty()){
            cartList.clear(); // 清空之前的集合

            /*Set<String> strings = map.keySet();
            for(String key:strings){
                map.get(key);
            }*/

            Set<Map.Entry<Integer, Cart>> entries = cartMap.entrySet();
            for(Map.Entry<Integer, Cart> entri :entries){
                entri.getKey();
                cartList.add(entri.getValue()) ; // 把map中的数据放入到集合中
            }
        }

        // 算出总价
        Double totalPrice = 0.0;
        for(Cart cart:cartList){
            totalPrice+=cart.getSubTotal();
        }

        map.put("cartList",cartList);
        map.put("totalPrice",totalPrice);
        return "cartList";
    }

    }
