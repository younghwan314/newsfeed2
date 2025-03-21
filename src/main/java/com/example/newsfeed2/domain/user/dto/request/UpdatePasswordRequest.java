package com.example.newsfeed2.domain.user.dto.request;

import lombok.Getter;

@Getter
public class UpdatePasswordRequest {

    private String oldPassword;
    private String newPassword;
}
