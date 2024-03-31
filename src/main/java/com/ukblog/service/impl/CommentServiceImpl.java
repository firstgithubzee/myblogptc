package com.ukblog.service.impl;

import com.ukblog.entity.Comment;
import com.ukblog.entity.Post;
import com.ukblog.exception.BlogApiException;
import com.ukblog.exception.ResourceNotFoundException;
import com.ukblog.payload.CommentDto;
import com.ukblog.payload.PostDto;
import com.ukblog.repository.CommentRepository;
import com.ukblog.repository.PostRepository;
import com.ukblog.service.CommentService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    private PostRepository postRepository;
    private CommentRepository commentRepository;

    public CommentServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public CommentDto saveComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post Not Found with this id: " + postId)
        );

         comment.setPost(post);
         Comment savedComment = commentRepository.save(comment);
         CommentDto dto = mapToDto(savedComment);

        return dto;
    }

    @Override
    public List<CommentDto> getCommentByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post Not Found with this id: " + postId)
        );
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDto> dto = comments.stream().map(comment-> mapToDto(comment)).collect(Collectors.toList());

        return dto;
    }

    @Override
    public CommentDto getCommentByCommentId(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post Not Found with this id: " + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment not found with this id: " + commentId)
        );
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException("Comment does not belong to this post");
        }
         CommentDto dto = mapToDto(comment);
        return dto;
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post Not Found with this id: " + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment not found with this id: " + commentId)
        );
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException("Comment does not belong to this post");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
       Comment updatedComment =  commentRepository.save(comment);
        CommentDto dto = mapToDto(updatedComment);
        return dto;
    }

    @Override
    public void deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post Not Found with this id: " + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new ResourceNotFoundException("Comment not found with this id: " + commentId)
        );
        if(!comment.getPost().getId().equals(post.getId())){
            throw new BlogApiException("Comment does not belong to this post");
        }
        commentRepository.deleteById(commentId);
    }

    Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
    CommentDto mapToDto(Comment comment){
        CommentDto dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        return dto;
    }
}
