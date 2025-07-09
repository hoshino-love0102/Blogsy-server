package com.example.blogsyserver.ai.service;

import com.example.blogsyserver.ai.dto.FeedRecommendResponse;
import com.example.blogsyserver.post.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedRecommendService {

    public FeedRecommendResponse recommendFeed(Long userId) {
        List<Post> dummyPosts = List.of(
                new Post(1L, "게임 추천", "게임 관련 내용", "[0.1, 0.3, 0.5]"),
                new Post(2L, "여행 정보", "여행 관련 내용", "[0.4, 0.2, 0.1]")
        );

        return FeedRecommendResponse.from(dummyPosts);
    }
}