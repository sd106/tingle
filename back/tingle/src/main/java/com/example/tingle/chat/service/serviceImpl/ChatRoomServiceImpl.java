package com.example.tingle.chat.service.serviceImpl;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.tingle.chat.dto.ChatRoomDto;
import com.example.tingle.chat.entity.ChatEnterEntity;
import com.example.tingle.chat.entity.ChatRoomEntity;
import com.example.tingle.chat.repository.ChatEnterRepository;
import com.example.tingle.chat.repository.ChatRoomRepository;
import com.example.tingle.chat.service.ChatRoomService;
import com.example.tingle.follow.entity.FollowEntity;
import com.example.tingle.follow.repository.FollowRepository;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StarRepository starRepository;

    @Autowired
    private ChatEnterRepository chatEnterRepository;

    @Override
    public List<ChatRoomDto> findRoomsByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + userId));

        return null;
    }

    @Override
    public ChatRoomDto findTheRoomByStarId(Long starId) {
        StarEntity star = starRepository.findById(starId)
                .orElseThrow(() -> new NotFoundException("Could not found star id : " + starId));

        return null;
    }

    @Override
    public void saveTheRoomByStarId(Long starId) {
        StarEntity star = starRepository.findById(starId)
                .orElseThrow(() -> new NotFoundException("Could not found star id : " + starId));

        ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                .star(star)
                .chatMessageList(new ArrayList<>())
                .build();

        chatRoomRepository.save(chatRoomEntity);
    }

    @Override
    public void deleteByStarId(Long starId) {
        StarEntity star = starRepository.findById(starId)
                .orElseThrow(() -> new NotFoundException("Could not found star id : " + starId));

        chatRoomRepository.deleteById(star.getId());
    }

    @Override
    public void ExitByUserId(Long starId, Long userId) {
        StarEntity star = starRepository.findById(starId)
                .orElseThrow(() -> new NotFoundException("Could not found star id : " + starId));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + userId));

        chatEnterRepository.deleteEnter(star.getId(), user.getId());
    }

    @Override
    public void EnterByUserId(Long starId, Long userId) {
        StarEntity star = starRepository.findById(starId)
                .orElseThrow(() -> new NotFoundException("Could not found star id : " + starId));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + userId));
        FollowEntity follow = followRepository.findByUserEntityIdAndStarEntityId(user.getId(), star.getId())
                .orElseThrow(() -> new NotFoundException("Could not found IDs"));

        ChatEnterEntity chatEnterEntity = ChatEnterEntity.builder()
                .follow(follow)
                .build();

        chatEnterRepository.saveEnter(chatEnterEntity, userId);
    }
}
