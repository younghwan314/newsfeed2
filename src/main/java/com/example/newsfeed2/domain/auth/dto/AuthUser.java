package com.example.newsfeed2.domain.auth.dto;

import lombok.Getter;

@Getter
public class AuthUser {

    private final long id;
    private final String name;
    private final String email;

    public AuthUser(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
}
