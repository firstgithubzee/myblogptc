package com.ukblog.service;

import com.ukblog.payload.PostDto;
import com.ukblog.payload.PostResponse;

import java.util.List;

public interface PostService {
     PostDto savePost(PostDto postDto);
     PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir);
     PostDto getPostByPostId(long postId);
     PostDto updatePost(PostDto postDto, long postId);
     void deletePost(long postId);
}
