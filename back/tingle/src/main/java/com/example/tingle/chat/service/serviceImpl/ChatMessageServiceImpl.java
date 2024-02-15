package com.example.tingle.chat.service.serviceImpl;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.tingle.chat.dto.ChatMessageDto;
import com.example.tingle.chat.dto.request.ChatMessageRequest;
import com.example.tingle.chat.entity.ChatMessageEntity;
import com.example.tingle.chat.entity.ChatRoomEntity;
import com.example.tingle.chat.repository.ChatMessageRepository;
import com.example.tingle.chat.repository.ChatRoomRepository;
import com.example.tingle.chat.service.ChatMessageService;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private StarRepository starRepository;

    @Override
    public List<ChatMessageDto> findMessagesByStarId(Long starId) {
        ChatRoomEntity chatRoom = chatRoomRepository.findTheRoomByStarId(starId);
        //.orElseThrow(() -> new NotFoundException("Could not found user id : " + chatMessageDto.getUserId()));

        List<ChatMessageEntity> chatMessageEntities = chatMessageRepository.findMessagesByChatRoom(chatRoom.getId());

        List<ChatMessageDto> chatMessageDtos = new ArrayList<>();

        for(ChatMessageEntity entity : chatMessageEntities) {
            ChatMessageDto chatMessageDto = ChatMessageDto.builder()
                    .id(entity.getId())
                    .roomId(entity.getChatRoom().getId())
                    .createdDate(entity.getCreatedDate())
                    .userId(entity.getUser().getId())
                    .message(entity.getMessage())
                    .direction(entity.getDirection())
                    .build();

            chatMessageDtos.add(chatMessageDto);
        }

        return chatMessageDtos;
    }

    @Override
    public void saveMessages(ChatMessageRequest chatMessageRequest) {
        UserEntity user = userRepository.findById(chatMessageRequest.getUserId())
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + chatMessageRequest.getUserId()));

        ChatRoomEntity chatRoom = chatRoomRepository.findTheRoomByRoomId(chatMessageRequest.getRoomId());
                //.orElseThrow(() -> new NotFoundException("Could not found user id : " + chatMessageDto.getUserId()));

        ChatMessageEntity chatMessageEntity = ChatMessageEntity.builder()
                .message(chatMessageRequest.getMessage())
                .chatRoom(chatRoom)
                .direction(chatMessageRequest.getDirection())
                .user(user)
                .build();

        chatMessageRepository.save(chatMessageEntity);
    }

    @Override
    public void deleteMessages(Long starId) {
        StarEntity star = starRepository.findById(starId)
                .orElseThrow(() -> new NotFoundException("Could not found star id : " + starId));

        ChatRoomEntity chatRoom = chatRoomRepository.findTheRoomByStarId(star.getId());
        //.orElseThrow(() -> new NotFoundException("Could not found user id : " + chatMessageDto.getUserId()));

        chatMessageRepository.deleteMessagesByChatRoom(chatRoom.getId());
    }
}
