package com.ukblog.service;

import com.ukblog.payload.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto saveComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentByPostId(long postId);
    CommentDto getCommentByCommentId(long postId, long commentId);
    CommentDto updateComment(long postId, long commentId,CommentDto commentDto);
    void deleteComment(long postId, long commentId);
}
