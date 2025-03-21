package com.example.newsfeed2.domain.like.controller;

import com.example.newsfeed2.domain.auth.annotation.Auth;
import com.example.newsfeed2.domain.auth.dto.AuthUser;
import com.example.newsfeed2.domain.like.dto.CommentLikeResponse;
import com.example.newsfeed2.domain.like.dto.PostLikeResponse;
import com.example.newsfeed2.domain.like.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/posts/{postId}/likes")
    public PostLikeResponse likePost(@Auth AuthUser authUser, @PathVariable Long postId){
        return likeService.likePost(authUser, postId);
    }

    @PostMapping("/posts/{postId}/comments/{commentId}/likes")
    public CommentLikeResponse likeComment(@Auth AuthUser authUser, @PathVariable Long postId, @PathVariable Long commentId){
        return likeService.likeComment(authUser, postId, commentId);
    }

}
