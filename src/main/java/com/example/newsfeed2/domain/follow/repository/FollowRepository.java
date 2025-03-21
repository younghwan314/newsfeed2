package com.example.newsfeed2.domain.follow.repository;

import com.example.newsfeed2.domain.follow.entity.Follow;
import com.example.newsfeed2.domain.follow.enums.FollowStatus;
import com.example.newsfeed2.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);
    List<Follow> findAllByFollowerAndFollowStatus(User follower, FollowStatus status);
    List<Follow> findAllByFollowingAndFollowStatus(User following, FollowStatus status);
}
