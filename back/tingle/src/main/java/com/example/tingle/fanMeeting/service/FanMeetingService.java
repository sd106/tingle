package com.example.tingle.fanMeeting.service;

import com.example.tingle.fanMeeting.dto.request.CreateFanMeetingRequest;
import com.example.tingle.fanMeeting.entity.FanMeeting;
import com.example.tingle.fanMeeting.entity.FanMeetingType;
import com.example.tingle.fanMeeting.repository.FanMeetingRepository;
import com.example.tingle.fanMeeting.repository.FanMeetingTypeRepository;
import com.example.tingle.fanMeeting.utils.DateTimeParser;
import com.example.tingle.user.entity.StarEntity;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.StarRepository;
import com.example.tingle.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FanMeetingService {
    private final FanMeetingTypeRepository fanMeetingTypeRepository;
    private final FanMeetingRepository fanMeetingRepository;
    private final StarRepository starRepository;
    private final UserRepository userRepository;

    public List<FanMeetingType> getFanMeetingTypes() {
        return fanMeetingTypeRepository.findAll();
    }

    public FanMeeting createFanMeeting(CreateFanMeetingRequest request) {
        UserEntity fan = userRepository.findByUsername(request.getFanName());
        StarEntity star = starRepository.findByUsername(request.getStarName());


        LocalDateTime ticketingStartAt = DateTimeParser.parse(request.getTicketingStartAt());
        LocalDateTime ticketingEndAt = DateTimeParser.parse(request.getTicketingEndAt());
        LocalDateTime fanMeetingStartAt = DateTimeParser.parse(request.getFanMeetingStartAt());

        System.out.println(request.getAvailableFanMeetingTypes());
        System.out.println(request.getPrice());
        System.out.println(ticketingEndAt);
        FanMeeting fanMeeting = FanMeeting.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .capacity(request.getCapacity())
                .fanMeetingStartAt(fanMeetingStartAt)
                .ticketingStartAt(ticketingEndAt)
                .ticketingEndAt(ticketingStartAt)
                .availableFanMeetingTypes(request.getAvailableFanMeetingTypes())
                .fan(fan)
                .star(star)
                .build();

        return fanMeetingRepository.save(fanMeeting);

    }
}
