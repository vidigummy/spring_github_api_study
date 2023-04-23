package com.example.spring_web_working_vidi_lab.service;

import com.example.spring_web_working_vidi_lab.Entity.GithubUser;
import com.example.spring_web_working_vidi_lab.repository.GithubUserRespository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.*;

@Service
@Slf4j
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    GithubUserRespository githubUserRespository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // TODO: 구현할 로직 작성
        OAuth2AccessToken accessToken = userRequest.getAccessToken();
//        System.out.println(accessToken.getTokenValue());
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        Map<String,Object> parameters = userRequest.getAdditionalParameters();
        DefaultOAuth2UserService defaultUserService = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = defaultUserService.loadUser(userRequest);

        String userInfoEndpointUri = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUri();
        if (!StringUtils.hasText(userInfoEndpointUri)) {
            throw new OAuth2AuthenticationException(
                    new OAuth2Error("missing_user_info_uri", "The user info endpoint URI is missing in the client registration", null));
        }

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + userRequest.getAccessToken().getTokenValue());
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> responseEntity = restTemplate.exchange(userInfoEndpointUri, HttpMethod.GET, entity, Map.class);
        Map<String, Object> userAttributes = responseEntity.getBody();



        String name = (String) userAttributes.get("name");
        String login = (String) userAttributes.get("login");
        String avatarUrl = (String) userAttributes.get("avatar_url");
        String htmlUrl = (String) userAttributes.get("html_url");
        List<String> roles = Arrays.asList("ROLE_USER");
        Collection<? extends GrantedAuthority> authorities = Collections.emptyList();
        OAuth2User principal = new DefaultOAuth2User(authorities, userAttributes, "id");
        loginUser(accessToken, principal);
//        System.out.println(principal.getAttributes());
        return principal;
    }

    @Transactional
    void loginUser(OAuth2AccessToken token, OAuth2User user){
        String userName = user.getAttribute("login");
        Long githubId  = Integer.toUnsignedLong(user.getAttribute("id"));

        try{
            // 이미 존재 시에.
            GithubUser loginUser = githubUserRespository.findGithubUserByGithubId(githubId);
            loginUser.setGithubToken(token.getTokenValue());
            githubUserRespository.save(loginUser);
            log.info("user Updated!");
        }catch (Exception e){
            try{
                GithubUser newUser = new GithubUser(userName, githubId,token.getTokenValue());
                githubUserRespository.save(newUser);
                log.info("new User login!");
            }catch (Exception e2){
                log.error("Error occurred!",e2.getStackTrace());
            }
        }
    }

}