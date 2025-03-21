package com.example.newsfeed2.domain.comment.dto;

import lombok.Getter;

@Getter
public class CommentResponse {

    private Long postId;
    private Long commentId;
    private String name;
    private String message;

    public CommentResponse(Long postId, Long commentId, String name, String message) {
        this.postId = postId;
        this.commentId = commentId;
        this.name = name;
        this.message = message;
    }
}
