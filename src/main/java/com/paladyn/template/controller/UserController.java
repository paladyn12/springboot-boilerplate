package com.paladyn.template.controller;

import com.paladyn.template.common.security.PrincipalDetails;
import com.paladyn.template.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 로그인 유저 정보를 확인할 수 있는 Controller
 */
@Controller
@RequestMapping("/users")
@Slf4j
public class UserController {

    @GetMapping("/info")
    public void userInfo(Authentication authentication) {
        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        User user = principal.getUser();
        log.info("user={}", user.getEmail());
    }
}
