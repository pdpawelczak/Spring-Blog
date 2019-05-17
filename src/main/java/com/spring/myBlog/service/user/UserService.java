package com.spring.myBlog.service.user;

import com.spring.myBlog.model.User;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface UserService {

    void saveUser(User user);
    User getUser(long userId);
    User findByUsername(String username);
    List<User> getUsers (User user);
    Sort sortByUsername(User user);
    void banUser(long userId);
    void unbanUser(long userId);
    List<User> searchUsers(String searchName);
}
