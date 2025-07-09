package com.example.blogsyserver.ai.service;

import com.example.blogsyserver.ai.dto.FeedRecommendResponse;
import com.example.blogsyserver.post.Post;
import com.example.blogsyserver.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedRecommendService {

    private final PostRepository postRepository;

    public FeedRecommendResponse recommendFeed(Long userId) {
        List<Post> posts = postRepository.findAll();
        return FeedRecommendResponse.from(posts);
    }
}
