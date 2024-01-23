package com.example.tingle.fanMeeting.service;

import com.example.tingle.fanMeeting.entity.FanMeetingType;
import com.example.tingle.fanMeeting.repository.FanMeetingTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FanMeetingService {
    private final FanMeetingTypeRepository fanMeetingTypeRepository;
    public List<FanMeetingType> getFanMeetingTypes() {
        return fanMeetingTypeRepository.findAll();
    }

    public FanMeetingType getFanMeetingType(Long id) {
        return fanMeetingTypeRepository.findById(id).orElseThrow();
    }


}
