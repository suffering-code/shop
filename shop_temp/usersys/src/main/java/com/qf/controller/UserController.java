package com.qf.controller;

import com.qf.entity.User;
import com.qf.service_api.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/getUserList")
    public String getUserList(Model model){
        model.addAttribute("userList",userService.getUserList());
        return "userList";
    }

    @RequestMapping(value = "/getUserById/{id}")
    public String  getUserById(@PathVariable Integer id, Model model){
        model.addAttribute("user",userService.getUserById(id));
        return "updateUser";
    }

    @RequestMapping(value = "/deleteUser/{id}")
    public String  deleteUser(@PathVariable Integer id,Model model){
        userService.deleteUser(id);
        return "redirect:../getUserList";
    }


    @RequestMapping(value = "/updateUser")
    public String  updateUser(User user){
        userService.updateUser(user);
        return "redirect:getUserList";
    }

    @RequestMapping(value = "/addUser")
    public String  addUser(User user){
        user.setUsername("qf1");
        user.setPassword("pw1");
        userService.addUser(user);
        return "redirect:getUserList";
    }


}
