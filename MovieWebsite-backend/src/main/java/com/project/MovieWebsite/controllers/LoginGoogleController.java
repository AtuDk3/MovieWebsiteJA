package com.project.MovieWebsite.controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/users")
public class LoginGoogleController {

    @GetMapping("/signingoogle")
    public OAuth2User user(@AuthenticationPrincipal OAuth2User principal) {
        return principal;
    }
    
}
