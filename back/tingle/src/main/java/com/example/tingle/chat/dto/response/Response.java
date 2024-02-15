package com.example.tingle.chat.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response {
    private String code;
    private String message;
    private Object data;
}
