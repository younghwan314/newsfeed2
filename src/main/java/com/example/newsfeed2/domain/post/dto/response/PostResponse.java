package com.example.newsfeed2.domain.post.dto.response;

import lombok.Getter;

@Getter
public class PostResponse {

    private final Long postId;
    private final String name;
    private final String title;
    private final String content;
    private int likeCount;

    public PostResponse(Long postId, String name, String title, String content) {
        this.postId = postId;
        this.name = name;
        this.title = title;
        this.content = content;
    }

    public PostResponse(Long postId, String name, String title, String content, int likeCount) {
        this.postId = postId;
        this.name = name;
        this.title = title;
        this.content = content;
        this.likeCount = likeCount;
    }
}
