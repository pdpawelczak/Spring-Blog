package com.spring.myBlog.controller;

import com.spring.myBlog.model.Comment;
import com.spring.myBlog.model.Post;
import com.spring.myBlog.service.comment.CommentServiceImpl;
import com.spring.myBlog.service.post.PostServiceImpl;
import com.spring.myBlog.service.user.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;


@Controller
public class HomeController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    PostServiceImpl postService;

    @Autowired
    CommentServiceImpl commentService;

    @Autowired
    UserServiceImpl userService;



    @GetMapping("/home")
    public String home(Pageable pageable, Model model, Principal principal) {
        model.addAttribute("showActiveUserName", principal);
        model.addAttribute("postList", postService.getPosts(pageable));
        model.addAttribute("lastPostList", postService.getPosts(PageRequest.of(0,5)));
        return "home";
    }

    @GetMapping("/readMore")
    public String readMore(@RequestParam("postId") int theId, Model model, Principal principal, Pageable pageable){
        Post post = postService.getPost(theId);
        model.addAttribute("post", post);
        model.addAttribute("postList", postService.getPosts(pageable));
        model.addAttribute("lastPostList", postService.getPosts(PageRequest.of(0,5)));

        Comment comment = new Comment();
        comment.setPost(post);
        comment.setUser(userService.findByUsername(principal.getName()));
        logger.warn(post.getComments().toString());
        model.addAttribute("comment", comment);
        model.addAttribute("postId", theId);

        return "readMore";
    }

    @PostMapping("/saveComment")
    public String savePost(@ModelAttribute("comment") Comment comment, RedirectAttributes redirectAttributes){
        commentService.saveComment(comment);
        redirectAttributes.addAttribute("postId", comment.getPost().getId());
        return "redirect:/readMore";
    }



}
