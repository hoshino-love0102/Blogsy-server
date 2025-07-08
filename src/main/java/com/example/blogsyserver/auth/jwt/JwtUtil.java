package com.example.blogsyserver.auth.jwt;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    //jwt 토큰 생성
    public String generateToken(String username) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    //jwt 토큰에서 username빼오기
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    //토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException ex) {
            System.out.println("Token expired");
        } catch (UnsupportedJwtException ex) {
            System.out.println("Unsupported JWT");
        } catch (MalformedJwtException ex) {
            System.out.println("Malformed JWT");
        } catch (SignatureException ex) {
            System.out.println("Invalid signature");
        } catch (IllegalArgumentException ex) {
            System.out.println("Illegal argument token");
        }
        return false;
    }
}
