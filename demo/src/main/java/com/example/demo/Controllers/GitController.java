package com.example.demo.Controllers;
import com.example.demo.Clients.GitClient;

import com.example.demo.Config.ClientConfig;
import com.example.demo.Models.AppId;
import com.example.demo.Models.Branch;
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

    private final GitClient gitClient;
    private final GitService gitService;
    private final ClientConfig clientConfig;
    private final AppId appId;



    @GetMapping("/fromClient/{username}/{app_id}")
    public ResponseEntity <List<GitRepo>> fromClient(@PathVariable("username") String username, @PathVariable("app_id") Long app_id)
    {
        appId.setId(app_id);
        clientConfig.requestInterceptor(appId);
        return ResponseEntity.ok().body(gitService.getAllRepos(username));
    }


    @GetMapping("/branch/{username}/{repo}")
    public ResponseEntity <List<Branch>> branch(@PathVariable("username") String username, @PathVariable("repo") String repo)
    {
        return ResponseEntity.ok().body(gitClient.getBranches(username, repo));
    }

//        @GetMapping("/user/{username}")
//        public ResponseEntity<String> getUser(@PathVariable("username") String username)
//        {
//            clientConfig.requestInterceptor();
//            return ResponseEntity.ok().body(gitClient.getUser(username));
//        }


}
