package com.example.tingle.fanMeeting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class FanMeetingType {

    @Id
    private Long id;

    private String name;
}
