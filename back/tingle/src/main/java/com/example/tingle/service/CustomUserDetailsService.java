package com.example.tingle.service;

import com.example.tingle.dto.CustomUserDetails;
import com.example.tingle.dto.LoginDto;
import com.example.tingle.entity.UserEntity;
import com.example.tingle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

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
