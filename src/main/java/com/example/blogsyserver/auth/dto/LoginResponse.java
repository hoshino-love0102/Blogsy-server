package com.example.blogsyserver.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private String username;
    private String email;
    private String nickname;
    private LocalDate birth;
    private String phone;
    private String profileImageUrl;
}