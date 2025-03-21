package com.example.newsfeed2.domain.follow.service;

import com.example.newsfeed2.domain.auth.dto.AuthUser;
import com.example.newsfeed2.domain.follow.dto.FollowerResponse;
import com.example.newsfeed2.domain.follow.dto.FollowingResponse;
import com.example.newsfeed2.domain.follow.entity.Follow;
import com.example.newsfeed2.domain.follow.enums.FollowStatus;
import com.example.newsfeed2.domain.follow.repository.FollowRepository;
import com.example.newsfeed2.domain.user.entity.User;
import com.example.newsfeed2.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final UserService userService;

    @Transactional
    public void connectFollow(String followerName, String followingName) {
        User followerUser = userService.userInfo(followerName);
        User followingUser = userService.userInfo(followingName);

        Follow follower = new Follow(followingUser, followerUser, FollowStatus.FOLLOWING);
        Follow following = new Follow(followerUser,followingUser, FollowStatus.FOLLOWER);

        followRepository.save(follower);
        followRepository.save(following);
    }

    @Transactional
    public void disconnectFollow(String followerNickname, String followingNickname) {
        User followerUser = userService.userInfo(followerNickname);
        User followingUser = userService.userInfo(followingNickname);
        Follow follower = followRepository.findByFollowerAndFollowing(followerUser,followingUser).orElseThrow(
                ()-> new IllegalArgumentException("팔로우 관계가 아닙니다")
        );
        Follow following = followRepository.findByFollowerAndFollowing(followingUser,followerUser).orElseThrow(
                ()-> new IllegalArgumentException("팔로우 관계가 아닙니다")
        );
        followRepository.delete(follower);
        followRepository.delete(following);
    }


    @Transactional(readOnly = true)
    public List<FollowerResponse> followerList(AuthUser authUser) {
        User user = userService.getUser(authUser);

        List<Follow> followList = followRepository.findAllByFollowerAndFollowStatus(user, FollowStatus.FOLLOWER);
        return  followList.stream()
                .map(follow -> new FollowerResponse(follow.getFollower().getName()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<FollowingResponse> followingList(AuthUser authUser) {
        User user = userService.getUser(authUser);

        List<Follow> followList = followRepository.findAllByFollowingAndFollowStatus(user, FollowStatus.FOLLOWING);
        return followList.stream()
                .map(follow -> new FollowingResponse(follow.getFollowing().getName()))
                .collect(Collectors.toList());
    }
}
