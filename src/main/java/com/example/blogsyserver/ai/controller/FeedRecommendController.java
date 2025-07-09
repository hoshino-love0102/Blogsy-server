package com.example.blogsyserver.ai.controller;

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
    public ResponseEntity<String> recommendFeed(@RequestParam Long userId) {
        String result = feedRecommendService.recommendFeed(userId);
        return ResponseEntity.ok(result);
    }
}
