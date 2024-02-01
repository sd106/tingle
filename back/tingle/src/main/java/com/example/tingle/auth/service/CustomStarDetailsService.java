package com.example.tingle.auth.service;

import com.example.tingle.auth.dto.CustomStarDetails;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.repository.StarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomStarDetailsService implements UserDetailsService {

    @Autowired
    private StarRepository starRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 유저가 있는지 확인
        StarEntity starData = starRepository.findByUsername(username);
        System.out.println("username = " + username);

        // 시큐리티 세션 = Authentication = UserDetails
        if (starData != null){
            System.out.println("userData.getUsername() = " + starData.getUsername());
            System.out.println("UserDetail Athentication에 넣습니다");
            return new CustomStarDetails(starData);

            /**  리턴하면 UserDetails가 Authentication 안에 들어감
             *   그리고 세션 내부에 Authentication이 들어감
             */
        }
        System.out.println("CustomUserDetailsService.loadUserByUsername@@@");
        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
