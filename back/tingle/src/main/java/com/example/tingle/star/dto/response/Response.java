package com.example.tingle.star.dto.response;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Response {

    private String code;

    private String message;

    private Object data;


}
