package com.myapp.blog.blogappapi.Security;

import com.myapp.blog.blogappapi.entities.User;
import com.myapp.blog.blogappapi.exceptions.ResourceNotFoundException;
import com.myapp.blog.blogappapi.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       //load user from db

        User user= userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User","email : "+ username,0));



        return user;
    }
}
