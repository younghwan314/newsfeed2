package com.example.newsfeed2.domain.follow.dto;

import lombok.Getter;

@Getter
public class FollowResponse {

    private String FollowerName;
    private String FollowingName;

    public FollowResponse(String followerName, String followingName) {
        FollowerName = followerName;
        FollowingName = followingName;
    }
}
