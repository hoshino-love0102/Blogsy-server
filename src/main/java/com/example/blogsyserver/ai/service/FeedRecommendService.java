package com.example.blogsyserver.ai.service;

import org.springframework.stereotype.Service;

@Service
public class FeedRecommendService {

    public String recommendFeed(Long userId) {
        return "User " + userId + " 맞춤 피드 추천 결과";
    }
}