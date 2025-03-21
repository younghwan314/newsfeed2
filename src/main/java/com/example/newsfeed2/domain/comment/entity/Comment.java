package com.example.newsfeed2.domain.comment.entity;

import com.example.newsfeed2.common.base.BaseEntity;
import com.example.newsfeed2.domain.post.entity.Post;
import com.example.newsfeed2.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor
public class Comment extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    private String message;

    private int likeCount;

    public Comment(User user, Post post, String message) {
        this.user = user;
        this.post = post;
        this.message = message;
    }

    public void update(String message) {
        this.message = message;
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }

    public void decreaseLikeCount() {
        if (this.likeCount > 0) {
            this.likeCount--;
        }
    }
}
