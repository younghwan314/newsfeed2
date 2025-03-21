package com.example.newsfeed2.domain.follow.dto;

import lombok.Getter;

@Getter
public class FollowingResponse {

    private String followingName;

    public FollowingResponse(String followingName) {
        this.followingName = followingName;
    }
}
