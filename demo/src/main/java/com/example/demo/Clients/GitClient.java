package com.example.demo.Clients;
import com.example.demo.Config.ClientConfig;
import com.example.demo.Config.CreateJWT;
import com.example.demo.Config.GitAuth;
import com.example.demo.Models.Branch;
import com.example.demo.Models.GitRepo;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.util.List;

@FeignClient(name = "My-Git-Client", url = "https://api.github.com", configuration = ClientConfig.class)
public interface GitClient
{

    @GetMapping("/users/{username}/repos")
    List<GitRepo> getRepos(@PathVariable("username") String username);

    @GetMapping("/repos/{username}/{repo}/branches")
    List<Branch> getBranches(@PathVariable("username") String username, @PathVariable("repo") String repo);
}




