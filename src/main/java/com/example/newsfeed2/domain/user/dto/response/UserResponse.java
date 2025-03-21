package com.example.newsfeed2.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserResponse {

    private String name;
    private String email;


    public UserResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
