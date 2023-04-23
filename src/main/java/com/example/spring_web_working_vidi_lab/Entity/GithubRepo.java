package com.example.spring_web_working_vidi_lab.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GithubRepo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, nullable = false, name = "repo_git_id")
    private Long repoGitId;

    @Column(nullable = false, name = "repo_name")
    private String repoName;

    @Column(nullable = false, name = "repo_url")
    private String url;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private GithubUser user;

    public GithubRepo(Long id, String name,String url, GithubUser user){
        this.repoGitId = id;
        this.repoName = name;
        this.url = url;
        this.user = user;
    }
}
