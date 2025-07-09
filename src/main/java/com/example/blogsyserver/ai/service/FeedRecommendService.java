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

    private final PostRepository postRepository; // 게시글 조회용 레포지토리

    public FeedRecommendResponse recommendFeed(Long userId) {
        List<Post> posts = postRepository.findAll(); // 모든 게시글 조회
        float[] userVector = getUserEmbedding(userId); // 사용자 임베딩 가져오기

        List<Post> recommended = posts.stream()
                .sorted(Comparator.comparingDouble(
                        p -> -cosineSimilarity(userVector, toVector(p.getEmbedding()))
                ))
                .limit(10) // 유사도 기준 상위 10개 선택
                .toList();

        return FeedRecommendResponse.from(recommended); // DTO로 변환 후 반환
    }

    private float[] getUserEmbedding(Long userId) {
        return new float[]{0.1f, 0.3f, 0.5f}; // 더미 사용자 임베딩
    }

    private float[] toVector(String embeddingJson) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(embeddingJson, float[].class); // JSON → 벡터 변환
        } catch (Exception e) {
            e.printStackTrace();
            return new float[0];
        }
    }

    private double cosineSimilarity(float[] vecA, float[] vecB) {
        double dot = 0.0, normA = 0.0, normB = 0.0;
        for (int i = 0; i < vecA.length; i++) {
            dot += vecA[i] * vecB[i]; // 내적 계산
            normA += vecA[i] * vecA[i];
            normB += vecB[i] * vecB[i];
        }
        return dot / (Math.sqrt(normA) * Math.sqrt(normB)); // 코사인 유사도 계산
    }
}
