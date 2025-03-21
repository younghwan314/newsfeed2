package com.example.newsfeed2.domain.follow.entity;

import com.example.newsfeed2.domain.follow.enums.FollowStatus;
import com.example.newsfeed2.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Follow {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long followId;

    // 나를 팔로우 하는 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "follower_id", nullable = false) // 팔로우 신청한 사람
    private User follower;

    // 내가 팔로우 하는 사람
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "following_id", nullable = false) // 필로우 신청 당한 사람
    private User following;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FollowStatus followStatus;

    public Follow(User follower, User following, FollowStatus followStatus) {
        this.follower = follower;
        this.following = following;
        this.followStatus = followStatus;
    }
}
