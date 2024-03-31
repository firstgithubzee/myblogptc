package com.ukblog.repository;

import com.ukblog.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
