package com.spring.myBlog.service.user;

import com.spring.myBlog.model.User;
import com.spring.myBlog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        UserBuilder userBuilder;
        if(user != null){
            userBuilder = org.springframework.security.core.userdetails.User.withUsername(username);
            userBuilder.password(user.getPassword());
            userBuilder.roles(user.getRoleNames());
            if(!userBuilder.build().isEnabled() || !user.isEnabled()){
                throw new DisabledException("User is disabled!");
            }
        }else{
            throw new UsernameNotFoundException("User not found!");
        }
        return userBuilder.build();
    }
}
