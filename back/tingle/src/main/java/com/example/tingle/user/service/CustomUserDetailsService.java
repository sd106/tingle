package com.example.tingle.user.service;

import com.example.tingle.user.dto.CustomUserDetails;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userData = userRepository.findByUsername(username);
        System.out.println("username = " + username);
        if (userData != null){
            System.out.println("userData.getUsername() = " + userData.getUsername());
            return new CustomUserDetails(userData);
        }
        System.out.println("CustomUserDetailsService.loadUserByUsername@@@");
        throw new UsernameNotFoundException("User not found with username: " + username);
    }

}
