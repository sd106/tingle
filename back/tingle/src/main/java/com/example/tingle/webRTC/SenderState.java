package com.example.tingle.webRTC;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Data
@Getter
@RequiredArgsConstructor
public class SenderState {
    private Long id;
    private String username;
    private String picture;

    public SenderState(String username) {
        this.username = username;
    }
}
