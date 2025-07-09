package com.example.blogsyserver.ai.service;

import com.example.blogsyserver.ai.dto.EmbeddingRequest;
import com.example.blogsyserver.ai.dto.EmbeddingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenAiEmbeddingService {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.embedding.url}")
    private String embeddingApiUrl;

    private final RestTemplate restTemplate;

    /**
     * 주어진 텍스트를 OpenAI Embedding API로 변환하여 float 벡터로 반환한다.
     */
    public float[] generateEmbedding(String text) {
        EmbeddingRequest request = new EmbeddingRequest(model, text);

        EmbeddingResponse response = restTemplate.postForObject(
                embeddingApiUrl,
                request,
                EmbeddingResponse.class
        );

        if (response == null || response.getData().isEmpty()) {
            throw new RuntimeException("Embedding API 호출 실패 - 응답이 null 또는 빈 데이터");
        }

        List<Double> embeddingList = response.getData().get(0).getEmbedding();

        float[] embeddingArray = new float[embeddingList.size()];
        for (int i = 0; i < embeddingList.size(); i++) {
            embeddingArray[i] = embeddingList.get(i).floatValue();
        }

        return embeddingArray;
    }
}
