package com.ukblog.controller;

import com.ukblog.payload.PostDto;
import com.ukblog.payload.PostResponse;
import com.ukblog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {
    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }
    //http://localhost:8080/api/posts
    @PostMapping
    public ResponseEntity<PostDto> savePost(@RequestBody PostDto postDto){
        PostDto dto = postService.savePost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
    //http://localhost:8080/api/posts?pageNo=1&pageSize=3&sortBy=id&sortDir=asc
    @GetMapping
    public PostResponse getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "5", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
       PostResponse  postResponse = postService.getAllPost(pageNo, pageSize, sortBy, sortDir);
        return postResponse;
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getPostByPostId(@PathVariable("postId") long postId){
        PostDto dto = postService.getPostByPostId(postId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(
            @RequestBody PostDto postDto, @PathVariable("postId") long postId){
        PostDto dto = postService.updatePost(postDto, postId);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable("postId") long postId){
        postService.deletePost(postId);
        return new ResponseEntity<>("Post Is Deleted", HttpStatus.OK);
    }
}
