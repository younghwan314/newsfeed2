package com.example.newsfeed2.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class LoginResponse {

    private final String bearerjwt;

    public LoginResponse(String bearerjwt) {
        this.bearerjwt = bearerjwt;
    }
}
