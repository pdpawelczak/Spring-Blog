package com.spring.myBlog.controller;

import com.spring.myBlog.model.User;
import com.spring.myBlog.repository.UserRepository;
import com.spring.myBlog.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
public class AdminPanelController {

    @Autowired
    UserServiceImpl userService;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/showUsers")
    public String showUsers(Model model, User user){

        model.addAttribute("userList", userService.getUsers(user));
        return "showUsers";
    }

    @GetMapping("/banUser")
    public String ban(@RequestParam("userId") long userId){

//        userService.banUser(userService.getUser(userId));
        userService.banUser(userId);

        return "redirect:/showUsers";
    }

    @GetMapping("/unbanUser")
    public String unban(@RequestParam("userId") long userId){
        userService.unbanUser(userId);
        return "redirect:/showUsers";
    }

    @GetMapping("/searchUser")
    public String searchUser(@RequestParam("searchName") String searchName, Model model){
        List<User> users = userService.searchUsers(searchName);
        model.addAttribute("userList", users);
        return "/showUsers";
    }
}
