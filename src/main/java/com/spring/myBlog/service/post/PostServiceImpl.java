package com.spring.myBlog.service.post;

import com.spring.myBlog.model.Post;
import com.spring.myBlog.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepository postRepository;

    @Override
    @Transactional
    public Page<Post> getPosts(Pageable pageable) {
        Sort.Order order = Sort.Order.desc("createDate");
        return postRepository.findAll(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(order)));
    }

    @Override
    @Transactional
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public Post getPost(int theId) {
        return postRepository.findById((long)theId);
    }

    @Override
    public void deletePost(int theId) {
        postRepository.delete(getPost(theId));
    }


}
