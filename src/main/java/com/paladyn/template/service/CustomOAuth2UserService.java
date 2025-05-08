package com.paladyn.template.service;

import com.paladyn.template.common.security.PrincipalDetails;
import com.paladyn.template.domain.User;
import com.paladyn.template.domain.UserRole;
import com.paladyn.template.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * oauth2-client 라이브러리를 사용해 사용자 정보를 불러오는 Service
 * 현재 paladyn12@naver.com 이메일의 유저만 ADMIN으로 설정
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        log.info("oAuth2User: {}", oauth2User);

        Map<String, Object> attributes = oauth2User.getAttributes();

        // 카카오 고유 ID
        String oauthId = String.valueOf(attributes.get("id"));
        String oauthProvider = userRequest.getClientRegistration().getRegistrationId();

        // 닉네임과 이메일 파싱
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

        String email = (String) kakaoAccount.get("email");
        String nickname = (String) profile.get("nickname");

        // DB에서 사용자 조회
        User user = userRepository.findByEmail(email);

        if (user == null) {
            // 이메일이 특정 관리자 이메일이면 ADMIN 권한 부여
            UserRole userRole = "paladyn12@naver.com".equals(email) ? UserRole.ADMIN : UserRole.USER;

            user = User.builder()
                    .email(email)
                    .nickname(nickname)
                    .oAuthProvider(oauthProvider)
                    .authority("ROLE_" + userRole.name())
                    .userRole(userRole)
                    .build();

            userRepository.save(user);
            log.info("새 사용자 저장: {}", user);
        } else {
            log.info("기존 사용자 로그인: {}", user);
        }

        // Spring Security 권한 부여
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + user.getUserRole().name()));

        return new PrincipalDetails(user, attributes);
    }
}
