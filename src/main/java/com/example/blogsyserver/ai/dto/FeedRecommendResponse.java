package com.example.blogsyserver.ai.dto;

import com.example.blogsyserver.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FeedRecommendResponse {
    private List<PostDto> posts;

    @Getter
    @AllArgsConstructor
    public static class PostDto {
        private Long id;
        private String title;
        private String content;
    }

    public static FeedRecommendResponse from(List<Post> postList) {
        List<PostDto> dtoList = postList.stream()
                .map(p -> new PostDto(
                        p.getId(),
                        p.getTitle(),
                        p.getContent()
                ))
                .toList();
        return new FeedRecommendResponse(dtoList);
    }
}
