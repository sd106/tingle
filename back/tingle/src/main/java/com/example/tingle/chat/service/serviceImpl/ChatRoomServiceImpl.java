package com.example.tingle.chat.service.serviceImpl;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.tingle.chat.dto.ChatEnterDto;
import com.example.tingle.chat.dto.ChatMessageDto;
import com.example.tingle.chat.dto.ChatRoomDto;
import com.example.tingle.chat.dto.ChatUserDto;
import com.example.tingle.chat.entity.ChatEnterEntity;
import com.example.tingle.chat.entity.ChatMessageEntity;
import com.example.tingle.chat.entity.ChatRoomEntity;
import com.example.tingle.chat.repository.ChatEnterRepository;
import com.example.tingle.chat.repository.ChatMessageRepository;
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

import java.time.LocalDateTime;
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

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Override
    public List<ChatRoomDto> findRoomsByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + userId));

        List<ChatEnterEntity> chatEnterEntities = chatEnterRepository.findEntersByUserId(user.getId());

        List<ChatRoomDto> chatRoomDtos = new ArrayList<>();

        for(ChatEnterEntity entity : chatEnterEntities) {
            List<ChatMessageDto> chatMessageDtos = new ArrayList<>();
            ChatMessageEntity chatMessage = chatMessageRepository.findTheMessageByChatRoom(entity.getChatRoom().getId());

            if (chatMessage != null) {
                ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                        .id(chatMessage.getId())
                        .userId(chatMessage.getUser().getId())
                        .message(chatMessage.getMessage())
                        .direction(chatMessage.getDirection())
                        .createdDate(chatMessage.getCreatedDate())
                        .roomId(chatMessage.getChatRoom().getId())
                        .build();

                chatMessageDtos.add(chatMessageDto);
            }

            ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                    .id(entity.getChatRoom().getId())
                    .starId(entity.getChatRoom().getStar().getId())
                    .chatMessage(chatMessageDtos)
                    .build();

            chatRoomDtos.add(chatRoomDto);
        }

        return chatRoomDtos;
    }

    public List<ChatUserDto> findChatRoomsUserInfoByUserId(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + userId));

        List<ChatEnterEntity> chatEnterEntities = chatEnterRepository.findEntersByUserId(user.getId());

        List<ChatUserDto> chatUserDtos = new ArrayList<>();

        for(ChatEnterEntity entity : chatEnterEntities) {
            ChatUserDto chatUserDto = ChatUserDto.builder()
                    .id(entity.getChatRoom().getStar().getId())
                    .username(entity.getChatRoom().getStar().getUsername())
                    .picture(entity.getChatRoom().getStar().getPicture())
                    .build();

            chatUserDtos.add(chatUserDto);
        }

        return chatUserDtos;
    }

    @Override
    public ChatRoomDto findTheRoomByStarId(Long starId) {
        StarEntity star = starRepository.findById(starId)
                .orElseThrow(() -> new NotFoundException("Could not found star id : " + starId));

        // 채팅방의 유무를 확인
        ChatRoomEntity chatroom = chatRoomRepository.findTheRoomByStarId(star.getId());
                //.orElseThrow(() -> new NotFoundException("Could not found star id : " + starId));\

        List<ChatMessageDto> chatMessageDtos = new ArrayList<>();
        ChatMessageEntity chatMessage = chatMessageRepository.findTheMessageByChatRoom(chatroom.getId());

        if (chatMessage != null) {
            ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                    .id(chatMessage.getId())
                    .userId(chatMessage.getUser().getId())
                    .message(chatMessage.getMessage())
                    .direction(chatMessage.getDirection())
                    .createdDate(chatMessage.getCreatedDate())
                    .roomId(chatMessage.getChatRoom().getId())
                    .build();

            chatMessageDtos.add(chatMessageDto);
        }

        ChatRoomDto chatRoomDto = ChatRoomDto.builder()
                .id(chatroom.getId())
                .starId(chatroom.getStar().getId())
                .chatMessage(chatMessageDtos)
                .build();

        return chatRoomDto;
    }

    @Override
    public void saveTheRoomByStarEmail(String starEmail) {
        StarEntity star = starRepository.findByEmail(starEmail)
                .orElseThrow(() -> new NotFoundException("Could not found starEmail : " + starEmail));

        ChatRoomEntity chatRoomEntity = ChatRoomEntity.builder()
                .star(star)
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

        // 채팅 추가 상태 확인
        ChatEnterEntity chatEnter = chatEnterRepository.findEntersByIds(user.getId(), star.getId());
                //.orElseThrow(() -> new NotFoundException("Could not found id"));

        chatEnterRepository.deleteById(chatEnter.getId());
    }

    @Override
    public void EnterByUserId(Long starId, Long userId) {
        StarEntity star = starRepository.findById(starId)
                .orElseThrow(() -> new NotFoundException("Could not found star id : " + starId));
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + userId));

        // 채탕방 상태 확인
        ChatRoomEntity chatRoom = chatRoomRepository.findTheRoomByStarId (star.getId());
        //.orElseThrow(() -> new NotFoundException("Could not found IDs"));

        // 팔로우 상태 확인
        FollowEntity follow = followRepository.findByUserEntityIdAndStarEntityId(user.getId(), star.getId());
                //.orElseThrow(() -> new NotFoundException("Could not found IDs"));

        // 스타 추가
        ChatEnterEntity chatEnterEntity = ChatEnterEntity.builder()
                .chatRoom(chatRoom)
                .follow(follow)
                .build();

        chatEnterRepository.save(chatEnterEntity);
    }
}
