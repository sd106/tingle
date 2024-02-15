package com.example.tingle.fanMeeting.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class FanMeetingType {

    @Id
    private Long id;

    private String image;

    private String name;

    private String description;

}
