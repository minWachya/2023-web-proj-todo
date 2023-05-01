package com.example.demo.security;

import com.example.demo.model.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {
    private static final String SECRET_KEY = "dds";

    // JWT 토큰 생성
    public String create(UserEntity userEntity) {
        // 기한 1일
        Date expiryDate = Date.from(
                Instant.now()
                        .plus(1, ChronoUnit.DAYS)
        );

        /*
        // header
        { "alg": "HS512" } // 알고리즘
        // payload
        {
            "sub": "123df33dddg5".
            "iss": "demo app",
            "iat": 123345,
            "exp": 1592734950
         }.
         // secret key 사용해 서명한 부분
         Naskd359Msdjadk
        */

        return Jwts.builder()
                // header
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                // payload
                .setSubject(userEntity.getId())
                .setIssuer("demo app")
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .compact();
    }

    // 토큰 안증
    public String validateAndGetUserId(String token) {
        // parseClaimsJws 메서드가 base64로 디코딩 및 파싱
        // 헤더와 페이로드를 setSigningKey로 넘어온 시크릴 키를 이용해 서명한 루 token의 서명과 비교
        // 위조되지 않았다면 페이로드(Claims) 리턴
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)      // 시크릿 키로 해석
                .parseClaimsJws(token)
                .getBody();                     // 페이로드의 바디 부분 얻기
        return claims.getSubject();             // 바디에 넣은 subject(id) 리턴
    }
}
