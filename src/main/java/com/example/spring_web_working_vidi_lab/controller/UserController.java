package com.example.spring_web_working_vidi_lab.controller;

import com.example.spring_web_working_vidi_lab.Entity.GithubRepo;
import com.example.spring_web_working_vidi_lab.repository.GithubUserRespository;
import com.example.spring_web_working_vidi_lab.service.UserService;
import com.example.spring_web_working_vidi_lab.utils.JsonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
public class UserController {
    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @Autowired
    private UserService userService;



    @Transactional
    @RequestMapping(value = "/user/repo/list/update", method = RequestMethod.GET)
    public String getUserRepo(Model model) throws Exception {
        String id = loadUser();
        boolean result = userService.getRepoList(id);
        return Boolean.toString(result);
    }

    @RequestMapping(value = "/user/repo/select/all")
    public String getAllRepo(Model model) throws Exception{
        String id = loadUser();
        List<GithubRepo> result = userService.getAllRepoList(id);
        for(GithubRepo g : result){
            log.info(g.getRepoName());
        }
        String returnJson = convertGithubRepoListToJson(result);
        return returnJson;
    }

//    @RequestMapping(value = "/user/repo/get", method = RequestMethod.GET)
//    public String getOneRepo(Model model, @RequestParam(value="name") String name) throws Exception{
//
//
//    }


    private String loadUser(){
        String registrationId = "github";
        String principalName = SecurityContextHolder.getContext().getAuthentication().getName();
        OAuth2AuthorizedClient client = authorizedClientService.loadAuthorizedClient(registrationId, principalName);
        return client.getPrincipalName();
    }



    public String convertGithubRepoListToJson(List<GithubRepo> list){
        ObjectMapper mapper = new ObjectMapper();
        String json = "";
        try{
            json = mapper.writeValueAsString(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return json;
    }
}


