package com.example.spring_web_working_vidi_lab.utils;

import com.example.spring_web_working_vidi_lab.DTO.GitRepo;
import lombok.extern.slf4j.Slf4j;
import org.kohsuke.github.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GithubAPI {
    GitHub gitHub;
    String token = null;
    String userName = null;
    public List<GitRepo> getRepository() throws Exception{
        List<GitRepo> list = new ArrayList<>();
        try{
            connectToGithub(this.token);
        }catch (Exception e){
            throw new IllegalAccessException("Failed To Connect github API SVR");
        }

        GHRepositorySearchBuilder builder = gitHub.searchRepositories()
                .user(userName)
                .sort(GHRepositorySearchBuilder.Sort.UPDATED);


//        PagedIterable<GHRepository> repos = builder.list().withPageSize(10);
        PagedIterable<GHRepository> repos = gitHub.getUser(this.userName).listRepositories().withPageSize(10);
//        log.info(Integer.toString(cnt));
        for(GHRepository g : repos){
            GitRepo nowRepo = new GitRepo(g.getId(), g.getName(), g.getUrl());
            System.out.println(g.toString());
            list.add(nowRepo);
        }
        return list;
    }

    public GithubAPI(String token, String userName){
        this.token = token;
        this.userName = userName;
    }

    private void connectToGithub(String token) throws IOException {
        gitHub = new GitHubBuilder().withOAuthToken(token).build();
        gitHub.checkApiUrlValidity();
    }
}
