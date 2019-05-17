package com.spring.myBlog.service.comment;

import com.spring.myBlog.model.Comment;
import com.spring.myBlog.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepository commentRepository;

    @Override
    @Transactional
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }
}
