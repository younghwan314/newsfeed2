package com.example.newsfeed2.domain.comment.service;

import com.example.newsfeed2.domain.auth.dto.AuthUser;
import com.example.newsfeed2.domain.comment.dto.CommentRequest;
import com.example.newsfeed2.domain.comment.dto.CommentResponse;
import com.example.newsfeed2.domain.comment.entity.Comment;
import com.example.newsfeed2.domain.comment.repository.CommentRepository;
import com.example.newsfeed2.domain.post.entity.Post;
import com.example.newsfeed2.domain.post.service.PostService;
import com.example.newsfeed2.domain.user.entity.User;
import com.example.newsfeed2.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostService postService;
    private final UserService userService;

    @Transactional
    public CommentResponse postComment(AuthUser authUser, Long postId, CommentRequest commentRequest) {
        User user = userService.getUser(authUser);
        Post post = postService.postInfo(postId);

        Comment comment = new Comment(
                user,
                post,
                commentRequest.getMessage()
        );
        commentRepository.save(comment);
        return new CommentResponse(
                post.getId(),
                comment.getId(),
                user.getName(),
                commentRequest.getMessage()
        );
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getPostComments(AuthUser authUser, Long postId) {
        User user = userService.getUser(authUser);
        Post post = postService.postInfo(postId);
        List<Comment> comments = commentRepository.findByPost_Id(postId);
        return comments.stream().map(postComment -> new CommentResponse(
                post.getId(),
                postComment.getId(),
                user.getName(),
                postComment.getMessage()
        )).collect(Collectors.toList());
    }

    @Transactional
    public CommentResponse updateComment(AuthUser authUser,Long postId, Long commentId,CommentRequest commentRequest) {
        User user = userService.getUser(authUser);
        Post post = postService.postInfo(postId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("작성된 댓글이 없습니다")
        );
        if (comment.getUser().getId() != user.getId()) {
            throw new IllegalArgumentException("작성자만 댓글을 수정할 수 있습니다");
        }
        comment.update(commentRequest.getMessage());
        return new CommentResponse(
                post.getId(),
                comment.getId(),
                user.getName(),
                commentRequest.getMessage()
        );
    }

    @Transactional
    public void deleteComment(AuthUser authUser,Long postId, Long commentId) {
        User user = userService.getUser(authUser);
        Post post = postService.postInfo(postId);
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("작성한 댓글이 없습니다")
        );

        Boolean poster = post.getUser().getId().equals(user.getId());
        Boolean commenter = comment.getUser().getId().equals(user.getId());

        if (!poster && !commenter) {
            throw new IllegalArgumentException("게시물 작성자 또는 댓글 작성자만 삭제를 할 수 있습니다");
        }
        commentRepository.delete(comment);
    }

    @Transactional(readOnly = true)
    public Comment commentInfo(Long commentId) {
        return commentRepository.findById(commentId).orElseThrow(
                ()-> new IllegalArgumentException("작성한 댓글이 없습니다")
        );
    }
}
