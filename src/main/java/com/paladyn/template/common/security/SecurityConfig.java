package com.paladyn.template.common.security;

import com.paladyn.template.common.exception.CustomAuthExceptionHandler;
import com.paladyn.template.common.handler.OAuth2LoginSuccessHandler;
import com.paladyn.template.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring 보안 설정
 * 로그인은 OAuth2 로그인으로 진행하며 성공 여부에 따라 Handler 호출
 * 사용자 정보를 받아오는 Service로 DefaultOAuth2UserService를 상속받은 CustomOAuth2UserService 클래스 사용
 * /admin 으로 시작하는 url은 관리자만 접근 가능
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final OAuth2LoginSuccessHandler oAuth2LoginSuccessHandler;
    private final CustomAuthExceptionHandler customAuthExceptionHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                .requestMatchers("/admin/**").hasRole("ADMIN") // /admin/ 경로는 ADMIN 권한만 접근 가능
                .anyRequest().permitAll());
        http.oauth2Login(config -> config
                    .successHandler(oAuth2LoginSuccessHandler)
                    .failureHandler(customAuthExceptionHandler)
                    .userInfoEndpoint(endpointConfig -> endpointConfig
                            .userService(customOAuth2UserService)))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
