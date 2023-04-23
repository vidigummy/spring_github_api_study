package com.example.spring_web_working_vidi_lab.config.auth;

import com.example.spring_web_working_vidi_lab.service.CustomOAuth2UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Configuration
public class OAuthConfig {

    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        // OAuth2UserService 구현체를 생성하고 반환하는 코드
//        return new OAuth2UserService();
        return new CustomOAuth2UserService();
    }
}