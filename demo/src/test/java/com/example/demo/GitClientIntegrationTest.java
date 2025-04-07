package com.example.demo;


import com.example.demo.Clients.GitClient;
import com.example.demo.Config.CreateJWT;
import com.example.demo.Config.GitAuth;
import com.example.demo.Config.PKC1Decode;
import com.example.demo.Controllers.GitController;
import com.example.demo.Errors.UserNotFoundException;
import com.example.demo.Models.Branch;
import com.example.demo.Models.GitRepo;
import com.example.demo.Services.GitService;
import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GitClientIntegrationTest
{
    @Autowired
    private GitClient gitClient;
    @Autowired
    private GitService gitService;
    @Autowired
    private TestRestTemplate restTemplate;


    @Test
    public void contextLoads() {
    }

    //tests if endpoint works for default "octocat" user
    @Test
    public void testGetReposFromGitHub()
    {
        //given, when
        List<GitRepo> repos = gitService.getAllRepos("octocat");

        //then
        assertNotNull(repos, "Repository list can't be null");
        assertFalse(repos.isEmpty(), "Repository list can't be null");

        repos.forEach(repo -> System.out.println("Repo: " + repo.getName()));
    }

    //tests if any branch exists for given repo
    @Test
    public void testGetBranchesFromGitHub()
    {
        //given, when
        List<Branch> branches = gitClient.getBranches("octocat", "Hello-World");

        //then
        assertNotNull(branches, "List of branches can't be null");
        assertFalse(branches.isEmpty(), "List of branches can't be null");

        //debug info
        branches.forEach(branch -> System.out.println("Branch: " + branch.getName()));
    }

    //tests if user with wrong username throws not found
    @Test
    void shouldReturnNotFoundForInvalidUser()
    {

        //given
        String username = "blalellelelelelelelelleleleldoesntexist";

        //when
        ResponseEntity<String> response = restTemplate.getForEntity("/getRepos/" + username, String.class);

        // then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    //testing if env var for GitHub App Id exists and is correct, testing then jwt and accesstoken
    @Test
    public void testEstAppIdWithRealValues() throws Exception {
        // Given
        Long realAppId = Long.parseLong(System.getenv("GITHUB_AP_ID"));

        // When
        String realJwt = CreateJWT.generateJWTFromPKCS1File();
        String realToken = GitAuth.getInstallToken(realJwt);
        Long result = GitService.estAppId(realAppId);

        // Then
        assertEquals(realAppId, GitService.getAppId());
        assertNull(result);
    }

    //testing if env var private key exists and is correct
    @Test
    public void testDefinePathWithRealValues() throws Exception {
        // Given
        String realPath = System.getenv("GITHUB_PRIVATE_KEY_PATH");
        String realDecoded = PKC1Decode.decode();

        // When
        String result = GitService.definePath(realPath);

        // Then
        assertEquals(realPath, GitService.getPath());
        assertEquals(realDecoded, result);
    }

}

