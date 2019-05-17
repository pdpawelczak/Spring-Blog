package com.spring.myBlog.controller;

import com.spring.myBlog.model.Post;
import com.spring.myBlog.service.post.PostServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@Slf4j
public class PostController {

    @Autowired
    PostServiceImpl postService;

    @GetMapping("/newPost")
    public String newPost(Model model, Principal principal, Pageable pageable){
        Post post = new Post();
        model.addAttribute("post", post);
        model.addAttribute("postList", postService.getPosts(pageable));
        model.addAttribute("lastPostList", postService.getPosts(PageRequest.of(0,5)));
        return "postForm";
    }

    @PostMapping("/savePost")
    public String savePost(@ModelAttribute("post") Post post){
        postService.savePost(post);
        return "redirect:/home";
    }

    @GetMapping("/deletePost")
    public String delete(@RequestParam("postId") int theId) {
        postService.deletePost(theId);
        return "redirect:/home";
    }

    @GetMapping("/updatePost")
    public String updatePost(@RequestParam("postId") int theId, Model model, Pageable pageable){
        Post post = postService.getPost(theId);
        model.addAttribute("post", post);
        model.addAttribute("postList", postService.getPosts(pageable));
        return "postForm";
    }
}
