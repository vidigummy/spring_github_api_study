package com.example.spring_web_working_vidi_lab.utils;

import com.example.spring_web_working_vidi_lab.Entity.GithubRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JsonUtils {

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
