package com.example.spring_web_working_vidi_lab.controller;

import com.example.spring_web_working_vidi_lab.service.CustomOAuth2UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class DefaultController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(Model model){
        String registrationId = "github";
        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(registrationId, principalName);
        String id = client.getPrincipalName();
        OAuth2AccessToken accessToken = client.getAccessToken();
        System.out.println(id);
        System.out.println(accessToken);
        return "성공";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String hello(Model model){
        String registrationId = "github";
        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(registrationId, principalName);
        String id = client.getPrincipalName();
        OAuth2AccessToken accessToken = client.getAccessToken();
        return id;
    }
}
