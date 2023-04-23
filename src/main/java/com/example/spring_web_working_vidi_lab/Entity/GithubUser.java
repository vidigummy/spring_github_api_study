package com.example.spring_web_working_vidi_lab.Entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GithubUser {
    public GithubUser(String userName, Long githubId, String token){
        this.userName = userName;
        this.githubId = githubId;
        this.githubToken = token;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @Column(name = "github_id", unique = true)
    private Long githubId;

    @Column(name = "github_token")
    private String githubToken;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<GithubRepo> repos = new ArrayList<GithubRepo>();

    @Override
    public String toString() {
        return super.toString();
    }
}
