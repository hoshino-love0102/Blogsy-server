package com.example.blogsyserver.auth.service;

import com.example.blogsyserver.auth.dto.LoginRequest;
import com.example.blogsyserver.auth.dto.LoginResponse;
import com.example.blogsyserver.auth.dto.SignupRequest;
import com.example.blogsyserver.auth.jwt.JwtUtil;
import com.example.blogsyserver.exception.DuplicateEmailException;
import com.example.blogsyserver.exception.DuplicateNicknameException;
import com.example.blogsyserver.exception.LoginFailException;
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
    private final JwtUtil jwtUtil;

    // 회원가입
    public void signup(SignupRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmailException();
        }

        if (userRepository.findByNickname(request.getNickname()).isPresent()) {
            throw new DuplicateNicknameException();
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(encodedPassword)
                .nickname(request.getNickname())
                .birth(request.getBirth())
                .phone(request.getPhone())
                .profileImageUrl(request.getProfileImageUrl())
                .build();

        userRepository.save(user);
    }

    // 로그인
    public LoginResponse login(LoginRequest request) {
        Optional<User> optionalUser = userRepository.findByEmail(request.getEmailOrUsername());

        if (optionalUser.isEmpty()) {
            optionalUser = userRepository.findByUsername(request.getEmailOrUsername());
        }

        User user = optionalUser
                .orElseThrow(LoginFailException::new);

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new LoginFailException();
        }

        String token = jwtUtil.generateToken(user.getUsername());

        return new LoginResponse(
                token,
                user.getUsername(),
                user.getEmail(),
                user.getNickname(),
                user.getBirth(),
                user.getPhone(),
                user.getProfileImageUrl()
        );
    }
}
