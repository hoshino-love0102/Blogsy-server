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

    // 사용자의 임베딩과 게시글 임베딩 간 유사도로 상위 10개 피드 추천
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

    // 사용자 임베딩 벡터 생성 (임시로 "게임 추천" 사용)
    private float[] getUserEmbedding(Long userId) {
        String userProfileText = "게임 추천";
        return openAiEmbeddingService.generateEmbedding(userProfileText);
    }

    // JSON 문자열로 저장된 임베딩을 float 배열로 변환
    private float[] toVector(String embeddingJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(embeddingJson, float[].class);
        } catch (Exception e) {
            e.printStackTrace();
            return new float[0];
        }
    }

    // 두 벡터 간 코사인 유사도 계산
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
