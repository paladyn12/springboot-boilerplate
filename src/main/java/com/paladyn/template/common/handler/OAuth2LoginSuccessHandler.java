package com.paladyn.template.common.handler;

import com.paladyn.template.common.security.JwtTokenProvider;
import com.paladyn.template.common.security.PrincipalDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * OAuth2 Login 성공 Handler

  [JWT 토큰을 활용한 사용자 인증 흐름]

  1.  JWT 토큰 발급
 * (1) 로그인 성공 시 authentication.getPrincipal()로 유저 정보를 꺼냄
 * (2) 해당 유저 정보를 통해 JWT 토큰 생성 (createToken())
 * (3) 이 토큰은 응답 해더 (Authorization) 혹은 Body에 전달

  2. 클라이언트 저장
 * (1) 클라이언트는 받은 JWT를 로컬 저장소(cookie, localStorage 등)에 저장
 * (2) 이후 요청 시 Authorization: Bearer <JWT> 형식으로 요청 헤더에 JWT 토큰을 포함

  3. 서버에서 JWT 인증 처리
 * (1) JWT 토큰이 Authorization 헤더에 포함되어 전달됨
 * (2) SecurityConfig에서 설정한 JwtAuthenticationFilter가 클라이언트의 요청 헤더의 JWT 토큰 확인하여 파싱 + 인증 + 권한 세팅
 * (3) 여기서 사용자 권한이 ADMIN인지, USER 인지 등을 파악하여 적절한 권한 부여가 이루어짐
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();

        String token = jwtTokenProvider.createToken(principalDetails.getUser());

        // 응답 헤더에 JWT 토큰 추가
        response.addHeader("Authorization", "Bearer " + token);

        // JWT 토큰을 response에 담아서 전송
        response.setContentType("application/json");
        response.sendRedirect("/");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
        System.out.println("TOKEN : "+token);
    }
}