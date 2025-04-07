package com.example.demo.Clients;
import com.example.demo.Config.ClientConfig;
import com.example.demo.Models.Branch;
import com.example.demo.Models.GitRepo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "GitHub-Client", url = "https://api.github.com", configuration = ClientConfig.class)
public interface GitClient
{

    @GetMapping("/users/{username}/repos")
    List<GitRepo> getRepos(@PathVariable("username") String username);

    @GetMapping("/repos/{username}/{repo}/branches")
    List<Branch> getBranches(@PathVariable("username") String username, @PathVariable("repo") String repo);
}




