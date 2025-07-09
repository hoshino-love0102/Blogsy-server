package com.example.blogsyserver.ai.controller;

import com.example.blogsyserver.ai.dto.FeedRecommendResponse;
import com.example.blogsyserver.ai.service.FeedRecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feed")
@RequiredArgsConstructor
public class FeedRecommendController {

    private final FeedRecommendService feedRecommendService;

    @GetMapping("/recommend")
    public ResponseEntity<FeedRecommendResponse> recommendFeed(@RequestParam Long userId) {
        FeedRecommendResponse response = feedRecommendService.recommendFeed(userId);
        return ResponseEntity.ok(response);
    }
}
