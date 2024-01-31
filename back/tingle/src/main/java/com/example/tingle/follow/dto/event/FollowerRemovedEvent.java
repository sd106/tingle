package com.example.tingle.follow.dto.event;

import org.springframework.context.ApplicationEvent;

public class FollowerRemovedEvent extends ApplicationEvent {
    private final Long starId;

    public FollowerRemovedEvent(Object source, Long starId) {
        super(source);
        this.starId = starId;
    }

    public Long getStarId() {
        return starId;
    }
}
