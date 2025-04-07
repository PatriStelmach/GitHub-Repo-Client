package com.example.demo.Services;

import com.example.demo.Clients.GitClient;
import com.example.demo.Config.CreateJWT;
import com.example.demo.Config.GitAuth;
import com.example.demo.Config.PKC1Decode;
import com.example.demo.Models.Branch;
import com.example.demo.Models.GitRepo;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class GitService
{
    private final GitClient gitClient;

    @Getter
    private static String path = System.getenv("GITHUB_PRIVATE_KEY_PATH");

    @Getter
    private static Long appId = Long.parseLong(System.getenv("GITHUB_AP_ID"));



    public List<GitRepo> getAllRepos(String username)
    {
        List<GitRepo> repos = gitClient.getRepos(username);
        repos.removeIf(GitRepo::isFork);

        for (GitRepo repo : repos)
        {
            repo.setBranches(getBranches(username, repo.getName()));
        }

        return repos;
    }

    public List<Branch> getBranches(String username, String repoName)
    {
        return gitClient.getBranches(username, repoName);
    }

    public static Long estAppId(Long newId) throws Exception
    {
        appId = newId;
        CreateJWT.generateJWTFromPKCS1File();
        GitAuth.getInstallToken(CreateJWT.generateJWTFromPKCS1File());
        return null;
    }

    public static String definePath(String newPath) throws Exception
    {
        path = newPath;
        return PKC1Decode.decode();
    }




}
