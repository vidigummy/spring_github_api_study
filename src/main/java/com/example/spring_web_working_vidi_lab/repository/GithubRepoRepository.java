package com.example.spring_web_working_vidi_lab.repository;

import com.example.spring_web_working_vidi_lab.Entity.GithubRepo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GithubRepoRepository extends JpaRepository<GithubRepo, Long> {
    public GithubRepo findGithubRepoByRepoGitId(Long id);
    public List<GithubRepo> findGithubReposByUserId(Long id);

//    public List<GithubRepo> findByUserId(Long id);
}
