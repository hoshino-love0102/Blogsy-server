package com.example.blogsyserver.auth.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SignupRequest {

    private String username;
    private String email;
    private String password;
    private String nickname;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;

    private String phone;
    private String profileImageUrl;
}
