package com.spring.myBlog.controller;

import com.spring.myBlog.service.comment.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller

public class CommentController {

    @Autowired
    CommentServiceImpl commentService;


}
