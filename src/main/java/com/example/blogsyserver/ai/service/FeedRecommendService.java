package com.example.blogsyserver.ai.service;

import com.example.blogsyserver.ai.dto.FeedRecommendResponse;
import com.example.blogsyserver.post.Post;
import com.example.blogsyserver.post.PostRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedRecommendService {

    private final PostRepository postRepository;
    private final OpenAiEmbeddingService openAiEmbeddingService;

    public FeedRecommendResponse recommendFeed(Long userId) {
        List<Post> posts = postRepository.findAll();

        float[] userVector = getUserEmbedding(userId);

        List<Post> recommended = posts.stream()
                .sorted(Comparator.comparingDouble(
                        p -> -cosineSimilarity(userVector, toVector(p.getEmbedding()))
                ))
                .limit(10)
                .toList();

        return FeedRecommendResponse.from(recommended);
    }

    private float[] getUserEmbedding(Long userId) {
        String userProfileText = "게임 추천";
        return openAiEmbeddingService.generateEmbedding(userProfileText);
    }

    private float[] toVector(String embeddingJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(embeddingJson, float[].class);
        } catch (Exception e) {
            e.printStackTrace();
            return new float[0];
        }
    }

    private double cosineSimilarity(float[] vecA, float[] vecB) {
        if (vecA.length == 0 || vecB.length == 0) return 0.0;

        double dot = 0.0, normA = 0.0, normB = 0.0;
        for (int i = 0; i < vecA.length; i++) {
            dot += vecA[i] * vecB[i];
            normA += vecA[i] * vecA[i];
            normB += vecB[i] * vecB[i];
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
