package com.example.tingle.follow.dto.event;

import org.springframework.context.ApplicationEvent;

public class FollowerAddedEvent extends ApplicationEvent {

    private Long starId;

    public FollowerAddedEvent(Object source, Long starId) {
        super(source);
        this.starId = starId;
    }

    public Long getStarId() {
        return starId;
    }




}
