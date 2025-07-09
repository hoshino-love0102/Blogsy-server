package com.example.blogsyserver.ai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OpenAiEmbeddingService {

    @Value("${openai.model}")
    private String model;

    @Value("${openai.api.embedding.url}")
    private String embeddingApiUrl;

    private final RestTemplate restTemplate;
}