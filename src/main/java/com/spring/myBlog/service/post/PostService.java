package com.spring.myBlog.service.post;


import com.spring.myBlog.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    Page<Post> getPosts (Pageable pageable);
    void savePost(Post post);
    Post getPost(int theId);
    void deletePost(int theId);

}
