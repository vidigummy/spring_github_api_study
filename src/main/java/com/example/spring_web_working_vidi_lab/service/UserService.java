package com.example.spring_web_working_vidi_lab.service;


import com.example.spring_web_working_vidi_lab.DTO.GitRepo;
import com.example.spring_web_working_vidi_lab.Entity.GithubRepo;
import com.example.spring_web_working_vidi_lab.Entity.GithubUser;
import com.example.spring_web_working_vidi_lab.repository.GithubRepoRepository;
import com.example.spring_web_working_vidi_lab.repository.GithubUserRespository;
import com.example.spring_web_working_vidi_lab.utils.GithubAPI;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class UserService {

//    GitHub github;
    @Autowired
    GithubUserRespository githubUserRespository;

    @Autowired
    GithubRepoRepository githubRepoRepository;


    public boolean getRepoList(String userId) throws Exception {
        GithubUser nowUser = getUserByGitId(userId);
        String githubToken = nowUser.getGithubToken();
        String userName = nowUser.getUserName();
        GithubAPI githubAPI = new GithubAPI(githubToken, userName);
        List<GitRepo> repoList =  githubAPI.getRepository();
        saveRepoFromList(repoList, nowUser);
        return true;
    }

    public List<GithubRepo> getAllRepoList(String userId) throws Exception{
        GithubUser nowUser = getUserByGitId(userId);
        List<GithubRepo> result = nowUser.getRepos();
//        List<GithubRepo> result = githubUserRespository.
//        List<GithubRepo> result = githubRepoRepository.findGithubReposByUserId(nowUser.getId());
        return result;
    }

    @Transactional
    void saveRepoFromList(List<GitRepo> repoList, GithubUser user){
        for(GitRepo gr : repoList){
            try{
                GithubRepo repo = githubRepoRepository.findGithubRepoByRepoGitId(gr.getId());
                if(repo == null){
                    log.info("New Repo!");
                    GithubRepo newRepo = new GithubRepo(gr.getId(),gr.getName(),gr.getUrl().toString(), user);
                    githubRepoRepository.save(newRepo);
                }else{
                    log.info("Repo Not Null");
                }
            }catch (Exception e){
                log.info("New Repo!");
                GithubRepo newRepo = new GithubRepo(gr.getId(),gr.getName(),gr.getUrl().toString(), user);
                githubRepoRepository.save(newRepo);
            }
        }
    }

    private GithubUser getUserByGitId(String githubId){
        GithubUser nowUser = githubUserRespository.findGithubUserByGithubId(Long.parseLong(githubId));
        return nowUser;
    }
}
