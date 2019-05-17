package com.spring.myBlog.config;

import com.spring.myBlog.service.user.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@Order(1001)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    LoginRedirectHandler loginRedirectHandler;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder);

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/", "/login", "/home", "/css/**", "/js/**", "/webjars/**", "/error", "/style.css", "/login", "/register**","/newUser",
                        "/saveUser", "/UserTable.css").permitAll()
                .antMatchers("/post/newPost", "/newPost", "/savePost", "/deletePost", "/updatePost**", "/showUsers", "/banUser", "/unbanUser", "/searchUser").hasRole("ADMIN")
                .antMatchers("/saveComment", "/readMore**").hasAnyRole("ADMIN", "USER")
                .anyRequest().anonymous()
                .and()
                .formLogin().loginPage("/login").successHandler(loginRedirectHandler).permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

        http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS);

    }
}
