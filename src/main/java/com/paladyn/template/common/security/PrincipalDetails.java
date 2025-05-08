package com.paladyn.template.common.security;

import com.paladyn.template.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Spring Security에서 인증된 사용자 정보를 담는 클래스
 * getAuthorities() 메서드를 통해 사용자의 권한(ROLE_USER, ROLE_ADMIN 등)을 Spring Security에 제공
 */
public class PrincipalDetails implements OAuth2User {

    @Getter
    private User user;
    private Map<String, Object> attributes;

    public PrincipalDetails(User user,Map<String,Object> attributes) {
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public String getName() {
        return user.getEmail();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(user.getAuthority()));
    }
}
