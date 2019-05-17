package com.spring.myBlog.controller;

import com.spring.myBlog.model.User;
import com.spring.myBlog.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegisterController {

    @Autowired
    UserService userService;


    @GetMapping("/register")
    public String register(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/saveUser")
    public String saveUser(User user){
        userService.saveUser(user);
        return "redirect:/login";
    }


}
