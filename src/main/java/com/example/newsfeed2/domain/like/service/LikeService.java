package com.example.newsfeed2.domain.like.service;

import com.example.newsfeed2.domain.auth.dto.AuthUser;
import com.example.newsfeed2.domain.comment.entity.Comment;
import com.example.newsfeed2.domain.comment.service.CommentService;
import com.example.newsfeed2.domain.like.dto.CommentLikeResponse;
import com.example.newsfeed2.domain.like.dto.PostLikeResponse;
import com.example.newsfeed2.domain.like.entity.Like;
import com.example.newsfeed2.domain.like.repository.LikeRepository;
import com.example.newsfeed2.domain.post.entity.Post;
import com.example.newsfeed2.domain.post.service.PostService;
import com.example.newsfeed2.domain.user.entity.User;
import com.example.newsfeed2.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;
    private final CommentService commentService;

    @Transactional
    public PostLikeResponse likePost(AuthUser authUser, Long postId) {
        User user = userService.getUser(authUser);
        Post post = postService.postInfo(postId);

        if (user.getId().equals(post.getUser().getId())) {
            throw new IllegalArgumentException("자신의 게시물에 좋아요를 누를 수 없습니다");
        }

        boolean isLiked;
        Optional<Like> existingLike = likeRepository.findByUserAndPost(user, post);
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            post.decreaseLikeCount();
            isLiked = false;
        } else {
            likeRepository.save(new Like(user, post));
            post.increaseLikeCount();
            isLiked = true;
        }

        int likeCount = post.getLikeCount();
        return new PostLikeResponse(postId, isLiked, likeCount);

    }

    @Transactional
    public CommentLikeResponse likeComment(AuthUser authUser, Long postId , Long commentId) {
        User user = userService.getUser(authUser);
        Post post = postService.postInfo(postId);
        Comment comment = commentService.commentInfo(commentId);

        if(user.getId().equals(comment.getUser().getId())) {
            throw new IllegalArgumentException("자신의 댓글에 좋아요를 누를 수 없습니다");
        }

        boolean isLiked;
        Optional<Like> existingLike = likeRepository.findByUserAndComment(user, comment);
        if (existingLike.isPresent()) {
            likeRepository.delete(existingLike.get());
            comment.decreaseLikeCount();
            isLiked = false;
        } else {
            likeRepository.save(new Like(user,post,comment));
            comment.increaseLikeCount();
            isLiked = true;
        }

        int likeCount = comment.getLikeCount();
        return new CommentLikeResponse(postId, commentId, isLiked, likeCount);
    }
}
