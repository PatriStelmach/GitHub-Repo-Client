package com.example.demo.Controllers;

import com.example.demo.Models.GitRepo;
import com.example.demo.Services.GitService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@AllArgsConstructor
@RequestMapping("/github")
public class GitController
{
    private final GitService gitService;


    @GetMapping("/getRepos/{username}")
    public ResponseEntity <List<GitRepo>> fromClient(@PathVariable("username") String username)
    {

        return ResponseEntity.ok().body(gitService.getAllRepos(username));
    }

}
