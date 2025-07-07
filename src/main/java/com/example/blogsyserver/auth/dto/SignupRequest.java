package com.example.blogsyserver.auth.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class SignupRequest {

    private String username;
    private String email;
    private String password;
    private String nickname;
    private LocalDate birth;
    private String phone;
}
