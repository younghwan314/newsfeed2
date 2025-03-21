package com.example.newsfeed2.domain.follow.dto;

import lombok.Getter;

@Getter
public class FollowerResponse {

    private String followerName;

    public FollowerResponse(String followerName) {
        this.followerName = followerName;
    }
}
