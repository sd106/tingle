package com.example.tingle.fanMeeting.controller;

import com.example.tingle.fanMeeting.dto.FanMeetingReservationMessageDto;
import com.example.tingle.fanMeeting.dto.request.FanMeetingReservationRequest;
import com.example.tingle.fanMeeting.dto.response.GetFanMeetingInfoResponse;
import com.example.tingle.fanMeeting.entity.FanMeeting;
import com.example.tingle.fanMeeting.entity.FanMeetingReservation;
import com.example.tingle.fanMeeting.entity.FanMeetingType;
import com.example.tingle.fanMeeting.service.FanMeetingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/fanMeeting")
@RequiredArgsConstructor
public class FanMeetingController {
    private final FanMeetingService fanMeetingService;

    @GetMapping("/types")
    public List<FanMeetingType> getFanMeetingTypes() {
        System.out.println("getFanMeetingTypes");
        return fanMeetingService.getFanMeetingTypes();

    }

    @PostMapping("/")
    public FanMeeting createFanMeeting(@RequestParam String requestJson, @RequestParam(required = false) MultipartFile file1, @RequestParam(required = false) MultipartFile file2) {
        System.out.println("makeFanMeeting");
        return fanMeetingService.createFanMeeting(requestJson, file1, file2);
    }

    @GetMapping("/info/{starId}")
    public GetFanMeetingInfoResponse getFanMeetingInfo(@PathVariable Long starId) {
        System.out.println("getFanMeetingInfo");
        return fanMeetingService.getFanMeetingInfo(starId);
    }

    @GetMapping("/{starId}/reservation/{fanId}/{fanMeetingId}")
    public Boolean getFanMeetingReservation(@PathVariable Long starId, @PathVariable Long fanId, @PathVariable Long fanMeetingId) {
        System.out.println("getFanMeetingReservation");
        Optional<FanMeetingReservation> optFanMeetingReservation = fanMeetingService.getFanMeetingReservation(starId, fanId, fanMeetingId);

        Boolean haveTicket = false;
        if (optFanMeetingReservation.isPresent()) {
            haveTicket = true;
        }
        return haveTicket;
    }

    @DeleteMapping("/finish/{starId}")
    @Transactional
    public void finishFanMeeting(@PathVariable Long starId) {
        System.out.println("finishFanMeeting");
        fanMeetingService.finishFanMeeting(starId);
    }

    /**
     * 팬미팅 티켓을 구매하는 컨트롤러
     */
    @PostMapping("/{fanMeetingId}/reservation/new/{starId}/{typeId}")
    @Transactional
    public void perchaseMeeting(@PathVariable Long fanMeetingId,
                                @PathVariable Long starId,
                                @PathVariable Long typeId,
                                @RequestBody FanMeetingReservationRequest fanMeetingReservationRequest) {
        System.out.println("티켓 만들기 시작");
        System.out.println("fanMettingId = " + fanMeetingId);
        System.out.println("starId = " + starId);
        System.out.println("typeId = " + typeId);
        System.out.println("username = " + fanMeetingReservationRequest.getUsername());
        String username = fanMeetingReservationRequest.getUsername();
        fanMeetingService.createTicket(starId, typeId, fanMeetingId, username);


    }

    /**
     * 특정 팬미팅의 티켓 수를 얻는 컨트롤러
     */
    @GetMapping("/{fanMeetingId}/reservation")
    public Integer getMeetingTicketNumber(@PathVariable Long fanMeetingId) {
        System.out.println("이 팬미팅의 티켓이 얼마나 팔렸는지 불러올게요");
        List<FanMeetingReservation> fanMeetingReservations = fanMeetingService.getMeetingTicketNumber(fanMeetingId);

        return fanMeetingReservations.size();
    }

    @GetMapping("/fanMeetingReservation/{userId}/{starId}")
    public FanMeetingReservationMessageDto getRecentFanMeetingByUserAndStar(@PathVariable Long userId, @PathVariable Long starId) {
        return fanMeetingService.findRecentFanMeetingByUserAndStar(userId, starId);
    }
}