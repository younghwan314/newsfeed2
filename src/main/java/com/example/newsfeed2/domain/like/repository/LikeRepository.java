package com.example.newsfeed2.domain.like.repository;

import com.example.newsfeed2.domain.comment.entity.Comment;
import com.example.newsfeed2.domain.like.entity.Like;
import com.example.newsfeed2.domain.post.entity.Post;
import com.example.newsfeed2.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByUserAndPost(User user, Post post);
    Optional<Like> findByUserAndComment(User user, Comment comment);
}
