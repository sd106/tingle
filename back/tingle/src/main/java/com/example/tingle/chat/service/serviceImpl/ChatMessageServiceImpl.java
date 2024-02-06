package com.example.tingle.chat.service.serviceImpl;

import com.example.tingle.chat.dto.ChatMessageDto;
import com.example.tingle.chat.repository.ChatMessageRepository;
import com.example.tingle.chat.repository.ChatRoomRepository;
import com.example.tingle.chat.service.ChatMessageService;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<ChatMessageDto> findMessagesByIds(Long starId, Long userId) {
        return null;
    }

    @Override
    public List<ChatMessageDto> findMessagesByStarId(Long starId) {
        return null;
    }

    @Override
    public void saveMessages(ChatMessageDto chatMessageDto) {

    }

    @Override
    public void deleteMessages(Long starId) {

    }
}
