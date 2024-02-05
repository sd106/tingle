package com.example.tingle.auth.dto.Response;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BasicSingupLoadResponse {
    private String email;
    private String name;

}
