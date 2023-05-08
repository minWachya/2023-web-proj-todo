package com.example.demo.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Controller 동작 시 동작하는 Filter
// 인증 완료되면 생성하는 객체
@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            // 사용자 인증 끝나면 userId 만들어내는데 이걸 controller에서 사용함
            // 요청에서 토큰 가져오기
            String token = parseBearerToken(request);
            log.info("filter is runing...");

            // 토큰 검사하기
            if(token != null && !token.equalsIgnoreCase("null")) {
                // userId 가져오기
                String userId = tokenProvider.validateAndGetUserId(token);
                log.info("Authentication usre ID: " + userId);

                // 인증 완료되면 UsernamePasswordAuthenticationToken 객체 생성
                // 생성자: UsernamePasswordAuthenticationToken(사용자 id, 비밀번호, 사용자 권한(ROLE_ADMIN, ROLE_USER))
                // SecurityContextHolder에 SecurityContext 등록되면 인증된 사용자라고 인증됨
                AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userId, // @AuthenticationPrincipal 붙은 인자 값으로 전달됨
                        null,
                        AuthorityUtils.NO_AUTHORITIES // 사용자 권한 없음으로 설정
                );
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // 겹겹이 보안: SecurityContextHolder ( SecurityContext ( Authentication ) )
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authentication);
                SecurityContextHolder.setContext(securityContext);
            }
        } catch (Exception e) {
            logger.error("Could not set user authentication in security context", e);
        }

        //다음 체인 실행
        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        // Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴
        String bearerToken = request.getHeader("Authorization");

        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
