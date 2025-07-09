package com.example.blogsyserver.ai.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmbeddingRequest {
    private String model;
    private String input;
}
