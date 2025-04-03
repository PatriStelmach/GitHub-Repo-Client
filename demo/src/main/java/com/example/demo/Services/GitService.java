package com.example.demo.Services;

import com.example.demo.Clients.GitClient;
import com.example.demo.Models.Branch;
import com.example.demo.Models.GitRepo;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class GitService
{
    private final GitClient gitClient;


    public List<GitRepo> getAllRepos(String username)
    {
//        Map<Integer, String> errorMap = new HashMap<>();
//        errorMap.put(400, "INVALID_TEMPLATE");
//        errorMap.put(404, "REQUEST_REJECTED");
//        try {
//            List<GitRepo> repos = gitClient.getRepos(username);
//
//            repos.removeIf(GitRepo::isFork);
//
//            for (GitRepo repo : repos)
//            {
//                repo.setBranches(getBranches(username, repo.getName()));
//            }
//
//            return repos;
//        } catch (Exception e ) {
//            SomeOtherFunction(errorMap.get(e.getStatusCode()),errorMessage);
//        }

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
