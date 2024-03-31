package com.ukblog.service.impl;

import com.ukblog.entity.Post;
import com.ukblog.exception.ResourceNotFoundException;
import com.ukblog.payload.PostDto;
import com.ukblog.payload.PostResponse;
import com.ukblog.repository.PostRepository;
import com.ukblog.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto savePost(PostDto postDto) {
       Post post = mapToEntity(postDto);

        Post savedPost = postRepository.save(post);
        PostDto dto = mapToDto(savedPost);

        return dto;
    }

    @Override
    public PostResponse getAllPost(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())?
                Sort.by(sortBy).ascending(): Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo,pageSize,sort);
       Page<Post> content = postRepository.findAll(pageable);
       List<Post> posts = content.getContent();
        List<PostDto> dto = posts.stream().map(post->mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setContent(dto);
        postResponse.setPageNo(content.getNumber());
        postResponse.setPageSize(content.getSize());
        postResponse.setTotalPages(content.getTotalPages());
        postResponse.setTotalElement(content.getTotalElements());
        postResponse.setLast(content.isLast());
        return postResponse;
    }

    @Override
    public PostDto getPostByPostId(long postId) {
         Post post = postRepository.findById(postId).
                 orElseThrow(()-> new ResourceNotFoundException("Post is not found with id: " + postId));
         PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post not found with id: " + postId)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post updatedPost = postRepository.save(post);
        PostDto dto = mapToDto(updatedPost);
        return dto;
    }

    @Override
    public void deletePost(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new ResourceNotFoundException("Post not found with id: " + postId)
        );
        postRepository.delete(post);
    }

    PostDto mapToDto(Post post){
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }
    Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }
}
