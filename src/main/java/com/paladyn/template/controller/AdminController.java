package com.paladyn.template.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 관리자 접근 가능 엔드포인트를 관리하는 Controller
 */
@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @GetMapping
    public String adminPage() {
        return "admin";
    }
}
