package ru.nonamejack.audioshop.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-jwt")
public class TestController {

    @GetMapping("/secure")
    public String secure(@AuthenticationPrincipal Jwt jwt, Authentication auth) {
        System.out.println("Authorities: " + auth.getAuthorities());
        return jwt.getClaimAsString("email");
    }
}
