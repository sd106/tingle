package com.example.tingle.fanMeeting.service;

import com.example.tingle.fanMeeting.dto.request.CreateFanMeetingRequest;
import com.example.tingle.fanMeeting.dto.response.GetFanMeetingInfoResponse;
import com.example.tingle.fanMeeting.entity.FanMeeting;
import com.example.tingle.fanMeeting.entity.FanMeetingReservation;
import com.example.tingle.fanMeeting.entity.FanMeetingType;
import com.example.tingle.fanMeeting.repository.FanMeetingRepository;
import com.example.tingle.fanMeeting.repository.FanMeetingReservationRepository;
import com.example.tingle.fanMeeting.repository.FanMeetingTypeRepository;
import com.example.tingle.fanMeeting.utils.DateTimeParser;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.user.entity.UserEntity;
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
    private final FanMeetingReservationRepository fanMeetingReservationRepository;
    private final StarRepository starRepository;
    private final UserRepository userRepository;

    public List<FanMeetingType> getFanMeetingTypes() {
        return fanMeetingTypeRepository.findAll();
    }

    public FanMeeting createFanMeeting(CreateFanMeetingRequest request) {
        StarEntity star = starRepository.findByUsername(request.getStarName());

        LocalDateTime ticketingStartAt = DateTimeParser.parse(request.getTicketingStartAt());
        LocalDateTime ticketingEndAt = DateTimeParser.parse(request.getTicketingEndAt());
        LocalDateTime fanMeetingStartAt = DateTimeParser.parse(request.getFanMeetingStartAt());

        FanMeeting fanMeeting = FanMeeting.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .capacity(request.getCapacity())
                .fanMeetingStartAt(fanMeetingStartAt)
                .ticketingStartAt(ticketingEndAt)
                .ticketingEndAt(ticketingStartAt)
//                .imgURL(request.getImgURL())
                .availableFanMeetingTypes(request.getAvailableFanMeetingTypes())
                .star(star)
                .isFinished(false)
                .build();

        return fanMeetingRepository.save(fanMeeting);

    }

    // 가장 최근의 fanMeeting을 가져온다.
    public FanMeeting recentFanMeeting(Long starId) {
        StarEntity star = starRepository.findById(starId).orElseThrow(() -> new IllegalArgumentException("해당하는 star가 없습니다."));

        return fanMeetingRepository.findRecentFanMeetingByStar(star);
    }

    //     star의 이름으로 fanMeeting의 정보를 가져온다.
    public GetFanMeetingInfoResponse getFanMeetingInfo(Long starId) {

        FanMeeting fanMeeting = recentFanMeeting(starId);

        if (fanMeeting == null || fanMeeting.getIsFinished()) {
            return GetFanMeetingInfoResponse.builder()
                    .status("closed")
                    .build();
        }
        String status;
        LocalDateTime now = LocalDateTime.now();

        // fanMeeting의 status 결정
        // closed, open, ticketing
        if (now.isBefore(fanMeeting.getTicketingStartAt())) {
            status = "closed";
        } else if (now.isBefore(fanMeeting.getTicketingEndAt())) {
            status = "ticketing";
        } else {
            status = "open";
        }

        return GetFanMeetingInfoResponse.builder()
                .id(fanMeeting.getId())
                .title(fanMeeting.getTitle())
                .description(fanMeeting.getDescription())
                .status(status)
                .ticketingStartAt(fanMeeting.getTicketingStartAt().toString())
                .ticketingEndAt(fanMeeting.getTicketingEndAt().toString())
                .fanMeetingStartAt(fanMeeting.getFanMeetingStartAt().toString())
                .price(fanMeeting.getPrice())
                .imgURL(fanMeeting.getImgURL())
                .build();

    }

    public FanMeetingReservation getFanMeetingReservation(Long fanId) {
        UserEntity fan = userRepository.findById(fanId).orElseThrow(() -> new IllegalArgumentException("해당하는 fan이 없습니다."));
        return fanMeetingReservationRepository.findByUser(fan);
    }

    public void finishFanMeeting(Long starId) {
        FanMeeting fanMeeting = recentFanMeeting(starId);

        fanMeeting.finish();
    }
}