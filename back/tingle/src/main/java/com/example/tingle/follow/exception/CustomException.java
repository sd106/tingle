package com.example.tingle.follow.exception;

import com.example.tingle.follow.dto.response.Response;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomException {

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response illegalArgumentExceptionAdvice(IllegalArgumentException e) {
        //IllegalArgumentException 으로 오류를 보내주면 모두 이 메소드를 탄다
        return new Response("fail", e.getMessage().toString(), null);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Response entityNotFoundExceptionAdvice(EntityNotFoundException e) {
        return new Response("fail", e.getMessage().toString(), null);
    }

}
