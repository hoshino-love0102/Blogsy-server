package com.example.blogsyserver.auth.service;

import com.example.blogsyserver.auth.dto.LoginRequest;
import com.example.blogsyserver.auth.dto.SignupRequest;
import com.example.blogsyserver.user.entity.User;
import com.example.blogsyserver.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // 회원가입
    public void signup(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 이메일입니다.");
        }

        if (userRepository.findByNickname(request.getNickname()).isPresent()) {
            throw new IllegalArgumentException("이미 사용중인 닉네임입니다.");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .birth(request.getBirth() != null ? request.getBirth() : null)
                .phone(request.getPhone())
                .profileImageUrl(request.getProfileImageUrl())
                .build();
        userRepository.save(user);
    }

    //로그인
    public String login(LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmailOrUsername());

        if (optionalUser.isEmpty()) {
            optionalUser = userRepository.findByUsername(request.getEmailOrUsername());
        }

        User user = optionalUser
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일 혹은 아이디입니다."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return "로그인 성공!";
    }
}
