package com.example.newsfeed2.domain.comment.controller;

import com.example.newsfeed2.domain.auth.annotation.Auth;
import com.example.newsfeed2.domain.auth.dto.AuthUser;
import com.example.newsfeed2.domain.comment.dto.CommentRequest;
import com.example.newsfeed2.domain.comment.dto.CommentResponse;
import com.example.newsfeed2.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public CommentResponse postComment(@Auth AuthUser authUser, @PathVariable Long postId, @RequestBody CommentRequest commentRequest) {
        return commentService.postComment(authUser ,postId, commentRequest);
    }

    @GetMapping("/posts/{postId}/comments")
    public List<CommentResponse> getCommentsByPost(@Auth AuthUser authUser, @PathVariable Long postId) {
        return commentService.getPostComments(authUser, postId);
    }

    @PatchMapping("/posts/{postId}/comments/{commentId}")
    public CommentResponse updateComment(@Auth AuthUser authUser, @PathVariable Long postId, @PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {
        return commentService.updateComment(authUser,postId, commentId, commentRequest);
    }

    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public void deleteComment(@Auth AuthUser authUser, @PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(authUser, postId, commentId);
    }
}
