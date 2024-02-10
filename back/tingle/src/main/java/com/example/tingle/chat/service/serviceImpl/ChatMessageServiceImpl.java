package com.example.tingle.chat.service.serviceImpl;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.tingle.chat.dto.ChatMessageDto;
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
        StarEntity star = starRepository.findById(starId)
                .orElseThrow(() -> new NotFoundException("Could not found star id : " + starId));

        List<ChatMessageEntity> chatMessageEntities = chatMessageRepository.findMessagesByChatRoom(star.getId());

        List<ChatMessageDto> chatMessageDtos = new ArrayList<>();

        for(ChatMessageEntity entity : chatMessageEntities) {
            ChatMessageDto chatMessageDto = new ChatMessageDto();

            chatMessageDto.setId(entity.getId());
            chatMessageDto.setMessage(entity.getMessage());
            chatMessageDto.setCreatedDate(entity.getCreatedDate());
            chatMessageDto.setDirection(entity.getDirection());
            chatMessageDto.setUserId(entity.getUser().getId());
            chatMessageDto.setRoomId(entity.getChatRoom().getId());

            chatMessageDtos.add(chatMessageDto);
        }

        return chatMessageDtos;
    }

    @Override
    public void saveMessages(ChatMessageDto chatMessageDto) {
        UserEntity user = userRepository.findById(chatMessageDto.getUserId())
                .orElseThrow(() -> new NotFoundException("Could not found user id : " + chatMessageDto.getUserId()));

        ChatRoomEntity chatRoomEntity = chatRoomRepository.findTheRoomByStarId(chatMessageDto.getRoomId());
                //.orElseThrow(() -> new NotFoundException("Could not found user id : " + chatMessageDto.getUserId()));

        ChatMessageEntity chatMessageEntity = ChatMessageEntity.builder()
                .id(chatMessageDto.getId())
                .message(chatMessageDto.getMessage())
                .chatRoom(chatRoomEntity)
                .direction(chatMessageDto.getDirection())
                .user(user)
                .build();

        chatMessageRepository.save(chatMessageEntity);
    }

    @Override
    public void deleteMessages(Long starId) {
        StarEntity star = starRepository.findById(starId)
                .orElseThrow(() -> new NotFoundException("Could not found star id : " + starId));

        chatMessageRepository.deleteMessagesByChatRoom(star.getId());
    }
}
