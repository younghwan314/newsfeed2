package com.example.newsfeed2.domain.post.dto.request;

import lombok.Getter;

@Getter
public class PostRequest {
    private Long postId;
    private String name;
    private String title;
    private String content;
}