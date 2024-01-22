package com.example.tingle.user.service;

import com.example.tingle.user.dto.joinDto;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public void joinProcess(joinDto joinDTO) {

        //db에 이미 동일한 이름을 가진 회원이 존재하는가?
        boolean isUser = userRepository.existsByUsername(joinDTO.getUsername());
        if (isUser) {
            return ;
        }

        UserEntity data = new UserEntity();
        data.setUsername(joinDTO.getUsername());
        data.setPassword(bCryptPasswordEncoder.encode(joinDTO.getPassword()));
        data.setEmail(joinDTO.getEmail());
        data.setRole("ROLE_USER");

        userRepository.save(data);
    }
}
