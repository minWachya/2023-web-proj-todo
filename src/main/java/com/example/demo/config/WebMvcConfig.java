package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration // 스프링 빈으로 등록, 여러 설정 관련 메서드를 여기서 제공하겠다.(얘는 설정 관련 메서드와 빈 생성 기능을 갖고있구나)
public class WebMvcConfig implements WebMvcConfigurer {
    private final long MAX_AGE_SECS = 3600;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 모든 경로에 대해
        registry.addMapping("/**")
                // Origin이 front 주소일 경우, 아래 메서드를 허용한다.
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECS); // 실제 요청 보내기 전에 보내는 작은 요청(Options요청)을 클라이언트 쪽에서 보관하는 시간
        // 옵션 요청 매번 보내지 않고 몇초동안은 보관하고 있게..
    }
}
