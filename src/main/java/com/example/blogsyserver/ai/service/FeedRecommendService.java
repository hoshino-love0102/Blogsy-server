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

    // 게시글을 불러오기 위한 Repository
    private final PostRepository postRepository;

    // OpenAI Embedding 생성 서비스
    private final OpenAiEmbeddingService openAiEmbeddingService;

    // 사용자의 임베딩과 게시글 임베딩 간 유사도로 상위 10개 피드 추천
    public FeedRecommendResponse recommendFeed(Long userId) {
        // 모든 게시글 가져오기
        List<Post> posts = postRepository.findAll();

        // 사용자 임베딩 벡터 생성
        float[] userVector = getUserEmbedding(userId);

        // 게시글들을 코사인 유사도로 정렬 후 상위 10개 선택
        List<Post> recommended = posts.stream()
                .sorted(Comparator.comparingDouble(
                        // 유사도 높은 순으로 정렬하기 위해 - 부호 사용
                        p -> -cosineSimilarity(userVector, toVector(p.getEmbedding()))
                ))
                .limit(10)
                .toList();

        // 추천 결과를 DTO로 변환해 반환
        return FeedRecommendResponse.from(recommended);
    }

    // 사용자 임베딩 벡터 생성 (임시로 "게임 추천" 텍스트 사용)
    private float[] getUserEmbedding(Long userId) {
        // TODO: 실제로는 userId 기반으로 프로필 텍스트 생성해야 함
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
        double dot = 0.0;
        double normA = 0.0;
        double normB = 0.0;
        // 벡터 내 각 요소 곱의 합(dot), 벡터 길이(norm) 계산
        for (int i = 0; i < vecA.length; i++) {
            dot += vecA[i] * vecB[i];
            normA += vecA[i] * vecA[i];
            normB += vecB[i] * vecB[i];
        }
        // 코사인 유사도 계산
        return dot / (Math.sqrt(normA) * Math.sqrt(normB));
    }
}
