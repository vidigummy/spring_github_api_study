package com.example.spring_web_working_vidi_lab.repository;


import com.example.spring_web_working_vidi_lab.Entity.GithubRepo;
import com.example.spring_web_working_vidi_lab.Entity.GithubUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GithubUserRespository extends JpaRepository<GithubUser, Long> {
    GithubUser findGithubUserByGithubId(Long githubId);
    GithubUser findGithubUserByUserName(String userName);
}
