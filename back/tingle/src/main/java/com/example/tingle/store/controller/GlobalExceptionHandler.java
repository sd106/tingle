package com.example.tingle.store.controller;



import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice // 모든 컨트롤러에서 발생하는 예외를 처리하기 위한 어노테이션
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) // 처리할 예외 클래스를 지정
    @ResponseBody // 에러 응답을 JSON 형식으로 생성
    public Map<String, String> handleException(Exception e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "서버 오류 발생");
        errorResponse.put("message", e.getMessage()); // 예외 메시지를 추가로 포함할 수 있음

        return errorResponse;
    }
}