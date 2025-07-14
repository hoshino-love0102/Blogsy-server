package com.example.blogsyserver.auth.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//JWT 인증 필터 모든 요청마다 한 번만 실행되도록 OncePerRequestFilter 상속
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    //JwtUtil 주입
    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //요청 헤더에서 Authorization 값 가져오기
        String bearerToken = request.getHeader("Authorization");

        //Authorization 헤더가 존재하고 "Bearer "로 시작하는지 확인
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            // "Bearer " 이후의 실제 토큰만 잘라서 추출
            String token = bearerToken.substring(7);

            //토큰 유효성 검사
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 username 추출
                String username = jwtUtil.getUsernameFromToken(token);

                //username을 담아 Authentication 객체 생성
                Authentication auth =
                        new JwtAuthenticationToken(username, null, null);

                // SecurityContext에 인증 객체 저장 → 이후 SecurityContextHolder에서 인증 정보 활용 가능
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}
