package com.example.demo.Services;

import com.example.demo.Clients.GitClient;
import com.example.demo.Models.Branch;
import com.example.demo.Models.GitRepo;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;

@Data
@Service
@RequiredArgsConstructor
public class GitService
{
    private final GitClient gitClient;

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

}
