package com.ukblog.controller;

import com.ukblog.payload.CommentDto;
import com.ukblog.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class CommentController {
    private CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }
    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> saveComment(
            @PathVariable("postId") long postId,
            @RequestBody CommentDto commentDto
            ){
        CommentDto dto = commentService.saveComment(postId, commentDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    @GetMapping("/posts/{postId}/comments")
    public List<CommentDto> getAllCommentByPostId(@PathVariable("postId") long postId){
        List<CommentDto> dto = commentService.getCommentByPostId(postId);
        return dto;
    }
    @GetMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> getCommentByCommentId(@PathVariable("postId") long postId,
                                                            @PathVariable("commentId") long commentId){
       CommentDto dto =  commentService.getCommentByCommentId(postId, commentId);
       return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    @PutMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<CommentDto> updateComment(
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId,
            @RequestBody CommentDto commentDto
    ){
        CommentDto dto = commentService.updateComment(postId, commentId, commentDto);

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @DeleteMapping("/posts/{postId}/comments/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId,
                                                @PathVariable("commentId") long commentId){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment is Deleted.", HttpStatus.OK);
    }
}
