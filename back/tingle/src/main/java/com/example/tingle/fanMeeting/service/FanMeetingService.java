package com.example.tingle.fanMeeting.service;

import com.example.tingle.fanMeeting.dto.request.CreateFanMeetingRequest;
import com.example.tingle.fanMeeting.dto.response.GetFanMeetingInfoResponse;
import com.example.tingle.fanMeeting.entity.FanMeeting;
import com.example.tingle.fanMeeting.entity.FanMeetingReservation;
import com.example.tingle.fanMeeting.entity.FanMeetingType;
import com.example.tingle.fanMeeting.model.FanMeetingRoom;
import com.example.tingle.fanMeeting.repository.FanMeetingRepository;
import com.example.tingle.fanMeeting.repository.FanMeetingReservationRepository;
import com.example.tingle.fanMeeting.repository.FanMeetingTypeRepository;
import com.example.tingle.fanMeeting.utils.MeetingRoomMap;
import com.example.tingle.star.entity.StarEntity;
import com.example.tingle.star.repository.StarRepository;
import com.example.tingle.store.service.S3UploadService;
import com.example.tingle.user.entity.UserEntity;
import com.example.tingle.user.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FanMeetingService {
    private final FanMeetingTypeRepository fanMeetingTypeRepository;
    private final FanMeetingRepository fanMeetingRepository;
    private final FanMeetingReservationRepository fanMeetingReservationRepository;
    private final StarRepository starRepository;
    private final UserRepository userRepository;
    private final S3UploadService s3UploadService;
    private final ObjectMapper objectMapper;
    private final Map<Long, FanMeetingRoom> meetingRooms = MeetingRoomMap.getInstance().getMeetingRooms();

    public List<FanMeetingType> getFanMeetingTypes() {
        return fanMeetingTypeRepository.findAll();
    }

    public FanMeeting createFanMeeting(String requestJson, MultipartFile file1, MultipartFile file2) {

        CreateFanMeetingRequest request = null;
        System.out.println("!!!");
        try {
            request = objectMapper.readValue(requestJson, CreateFanMeetingRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        StarEntity star = starRepository.findByUsername(request.getStarName());
//        LocalDateTime ticketingStartAt = DateTimeParser.parse(request.getTicketingStartAt());
//        System.out.println("@@@@1111");
//        LocalDateTime ticketingEndAt = DateTimeParser.parse(request.getTicketingEndAt());
//        System.out.println("@@@@1111");
//        LocalDateTime fanMeetingStartAt = DateTimeParser.parse(request.getFanMeetingStartAt());
//        System.out.println("@@@@1111");
        String imgURL1 = null;
        String imgURL2 = null;
        try {
            imgURL1 = s3UploadService.saveFile(file1);
            imgURL2 = s3UploadService.saveFile(file2); // S3에 파일 업로드\
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        FanMeeting fanMeeting = FanMeeting.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .price(request.getPrice())
                .capacity(request.getCapacity())
                .fanMeetingStartAt(request.getFanMeetingStartAt())
                .ticketingStartAt(request.getTicketingStartAt())
                .ticketingEndAt(request.getTicketingEndAt())
                .imgURL1(imgURL1)
                .imgURL2(imgURL2)
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
                .capacity(fanMeeting.getCapacity())
                .price(fanMeeting.getPrice())
                .imgURL1(fanMeeting.getImgURL1())
                .imgURL1(fanMeeting.getImgURL2())
                .build();

    }

    public Optional<FanMeetingReservation> getFanMeetingReservation(Long starId, Long fanId, Long fanMeetingId) {
        StarEntity star = starRepository.findById(starId).orElseThrow(() -> new IllegalArgumentException("해당하는 star가 없습니다."));
        UserEntity fan = userRepository.findById(fanId).orElseThrow(() -> new IllegalArgumentException("해당하는 fan이 없습니다."));
        FanMeeting fanMeeting = fanMeetingRepository.findById(fanMeetingId).orElseThrow(() -> new IllegalArgumentException("해당하는 팬미팅이 없습니다."));
        return fanMeetingReservationRepository.findByUserAndStarAndFanMeeting(fan, star, fanMeeting);
    }

    public void finishFanMeeting(Long starId) {
        FanMeeting fanMeeting = recentFanMeeting(starId);
        Long waitingRoomId = starId * 10 + 1;
        Long meetingRoomId = starId * 10 + 2;
        for(Long k : meetingRooms.keySet()){
            System.out.println(k);
        }
        System.out.println("waitingRoomId : " + waitingRoomId);
        meetingRooms.remove(waitingRoomId);
        meetingRooms.remove(meetingRoomId);
        System.out.println(fanMeeting.getId());
        System.out.println(fanMeeting.getIsFinished());
        fanMeeting.finish();
        System.out.println(fanMeeting.getIsFinished());
        System.out.println("삭제 성공~");
    }

    /**
     * 티켓 만들기 서비스 코드
     */
    public void createTicket(Long starId, Long typeId, Long fanMettingId, String username) {

        Optional<StarEntity> optStar = starRepository.findById(starId);
        StarEntity star = optStar.get();
        System.out.println("star = " + star);

        Optional<FanMeetingType> optMeeting = fanMeetingTypeRepository.findById(typeId);
        FanMeetingType fanMeetingType = optMeeting.get();
        System.out.println("fanMeetingType = " + fanMeetingType);

        Optional<FanMeeting> optFanMeeting = fanMeetingRepository.findById(fanMettingId);
        FanMeeting fanMeeting = optFanMeeting.get();
        System.out.println("fanMeeting = " + fanMeeting);

        UserEntity user = userRepository.findByUsername(username);
        System.out.println("user = " + user);

        FanMeetingReservation fanMeetingReservation = FanMeetingReservation.builder()
                .fanMeetingType(fanMeetingType)
                .fanMeeting(fanMeeting)
                .user(user)
                .star(star)
                .build();

        fanMeetingReservationRepository.save(fanMeetingReservation);
        System.out.println("티켓 저장 완료!");

    }

    /**
     * 특정 팬미팅의 티켓 수를 반환하는 서비스
     */
    public List<FanMeetingReservation> getMeetingTicketNumber(Long meetingId) {
        Optional<FanMeeting> fanMeeting = fanMeetingRepository.findById(meetingId);
        if (fanMeeting.isPresent()) {
            List<FanMeetingReservation> fanMeetingReservations = fanMeetingReservationRepository.findByFanMeeting(fanMeeting.get());
            return fanMeetingReservations;
        }
        return null;
    }

}