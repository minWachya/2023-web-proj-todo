package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

// 필터 설정
@EnableWebSecurity // 스프링 시큐리티 활성. CSRF 공격을 보로하는 기능 활성화
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    // HttpSecurity: 시큐리티 설정을 위한 객체, 세부적인 보안 기능을 설정할 수 있는 API 제공해주는 클래스
    protected void configure(HttpSecurity http) throws Exception {
        // http 시큐리티 빌더
        http.cors()         // WebNvcConfig에서 이미 설정했으므로 기본 cors 설정
                .and()
                .csrf().disable()       // csrf는 현재 사용하지 않으므로 비활성화
                .httpBasic().disable()  // token을 사용하므로 basic 인증 비활성화
                // 세션 기반이 아니라 토큰을 받아서 검증함을 선언
                // 세션 관리 안 함: 일정 기간동안 클라이언트와 세션 유지 안하겠다.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // 중요 부분!! 이 부분 없으면 403 에러남
                // authorizeHttpRequests: auth라는 url이 있는 요청은 모두 허용해줘라.
                // 나머지 요청은 인증을 해야한다.
                .authorizeRequests().antMatchers("/", "/auth/**").permitAll()
                .anyRequest().authenticated();

        http.addFilterAfter(
                // CorsFilter 처리 후에 jwtAuthenticationFilter 처리해라
                // CorsFilter: pre-flight request(클라이언트가 실제 요청 전 작은 요청 먼저 보내는 작은 요청) 처리 필터
                jwtAuthenticationFilter,
                CorsFilter.class
        );
    }

}
