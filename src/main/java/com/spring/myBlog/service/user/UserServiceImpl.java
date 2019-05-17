package com.spring.myBlog.service.user;

import com.spring.myBlog.repository.RoleRepository;
import com.spring.myBlog.repository.UserRepository;
import com.spring.myBlog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;


    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(roleRepository.findByName("USER")));
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public User getUser(long userId) {
        return userRepository.getOne(userId);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public List<User> getUsers(User user) {
        return userRepository.findAll();
    }

    @Override
    public Sort sortByUsername(User user) {
        return Sort.by("username");
    }

    @Override
    public void banUser(long userId) {
        User userFromDb = userRepository.getOne(userId);
        userFromDb.setEnabled(false);
        userRepository.save(userFromDb);

    }

    @Override
    public void unbanUser(long userId) {
        User userFromDb = userRepository.getOne(userId);
        userFromDb.setEnabled(true);
        userRepository.save(userFromDb);
    }

    @Override
    public List<User> searchUsers(String searchName) {
        if(searchName != null && searchName.trim().length() > 0){
            List<User> customers = userRepository.findAll();
            return customers.stream().filter(user -> user.getUsername().toLowerCase().contains(searchName.toLowerCase()) ||
                    user.getEmail().toLowerCase().contains(searchName.toLowerCase()))
                    .collect(Collectors.toList());

        }
        else{
            return userRepository.findAll();
        }
    }
}
