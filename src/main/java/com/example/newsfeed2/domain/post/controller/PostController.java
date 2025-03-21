package com.example.newsfeed2.domain.post.controller;

import com.example.newsfeed2.domain.auth.annotation.Auth;
import com.example.newsfeed2.domain.auth.dto.AuthUser;
import com.example.newsfeed2.domain.post.dto.request.PostRequest;
import com.example.newsfeed2.domain.post.dto.response.PostResponse;
import com.example.newsfeed2.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping("/posts")
    public PostResponse createPost(@Auth AuthUser authUser, @RequestBody PostRequest postRequest) {
        return postService.createPost(authUser, postRequest);
    }

    @PutMapping("/posts/{postId}")
    public PostResponse updatePost(@Auth AuthUser authUser, @PathVariable Long postId, @RequestBody PostRequest postRequest) {
        return postService.updatePost(authUser, postId, postRequest);
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@Auth AuthUser authUser, @PathVariable Long postId) {
        postService.deletePost(authUser, postId);
    }

    @GetMapping("/posts/all/create")
    public Page<PostResponse> getAllPostsByCreate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<PostResponse> posts = postService.getAllPosts(pageable);
        return posts;
    }

    @GetMapping("/posts/all/update")
    public Page<PostResponse> getAllPostsByUpdate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        Page<PostResponse> posts = postService.getAllPosts(pageable);
        return posts;
    }

    @GetMapping("/posts/following")
    public Page<PostResponse> getAllPostsByFollowing(
            @Auth AuthUser authUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        Page<PostResponse> posts = postService.findAllFollowsPosts(authUser, pageable);
        return posts;
    }

    @GetMapping("/posts/date")
    public Page<PostResponse> getAllPostsByDate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("updatedAt").descending());
        Page<PostResponse> posts = postService.findAllByDate(startDate, endDate, pageable);

        return posts;
    }
}
