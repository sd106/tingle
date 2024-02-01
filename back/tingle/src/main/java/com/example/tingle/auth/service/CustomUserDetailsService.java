package com.example.tingle.auth.service;

import com.example.tingle.auth.dto.CustomUserDetails;
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
        // 유저가 있는지 확인
        UserEntity userData = userRepository.findByUsername(username);
        System.out.println("username = " + username);

        // 시큐리티 세션 = Authentication = UserDetails
        if (userData != null){
            System.out.println("userData.getUsername() = " + userData.getUsername());
            return new CustomUserDetails(userData);

            /**  리턴하면 UserDetails가 Authentication 안에 들어감
             *   그리고 세션 내부에 Authentication이 들어감
             */
        }
        System.out.println("CustomUserDetailsService.loadUserByUsername@@@");
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
