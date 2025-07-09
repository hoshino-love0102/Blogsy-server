package com.example.blogsyserver.post;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 2000)
    private String embedding;

    public Post(String title, String content, String embedding) {
        this.title = title;
        this.content = content;
        this.embedding = embedding;
    }

    public Post(Long id, String title, String content, String embedding) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.embedding = embedding;
    }
}
