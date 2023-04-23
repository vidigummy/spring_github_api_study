package com.example.spring_web_working_vidi_lab.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;

@Getter
@Setter
@AllArgsConstructor
public class GitRepo {
    Long id;
    String name;
    URL url;
}
