package com.example.newsfeed2.domain.follow.controller;

import com.example.newsfeed2.domain.auth.annotation.Auth;
import com.example.newsfeed2.domain.auth.dto.AuthUser;
import com.example.newsfeed2.domain.follow.dto.FollowerResponse;
import com.example.newsfeed2.domain.follow.dto.FollowingResponse;
import com.example.newsfeed2.domain.follow.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow")
    public void follow(@RequestParam String followerNickName, @RequestParam String followingNickName) {
        followService.connectFollow(followingNickName, followerNickName);
    }

    @GetMapping("/follower/list")
    public List<FollowerResponse> followList(@Auth AuthUser authUser) {
        List<FollowerResponse> followers = followService.followerList(authUser);
        return followers;
    }

    @GetMapping("/following/list")
    public List<FollowingResponse> followingList(@Auth AuthUser authUser) {
        List<FollowingResponse> followings = followService.followingList(authUser);
        return followings;
    }

    @DeleteMapping("/follow")
    public void unfollow(@RequestParam String followerNickName, @RequestParam String followingNickName) {
        followService.disconnectFollow(followingNickName,followerNickName);
    }
}
