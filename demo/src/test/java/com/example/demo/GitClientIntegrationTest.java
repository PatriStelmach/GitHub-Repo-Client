package com.example.demo;


import com.example.demo.Clients.GitClient;
import com.example.demo.Controllers.GitController;
import com.example.demo.Models.Branch;
import com.example.demo.Models.GitRepo;
import com.example.demo.Services.GitService;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "github.api.url=http://localhost:8081"
        }
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class GitClientIntegrationTest
{
    @Autowired
    private GitClient gitClient;

    public static MockWebServer mockWebServer;

    @BeforeAll
    static void setUp() throws IOException
    {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8081);

    }

    @AfterAll
    static void tearDown() throws IOException
    {
        mockWebServer.shutdown();
    }


    @Test
    public void contextLoads() {
    }



    //tests if endpoint works for default "octocat" user
    @Test
    public void testGetReposFromGitHub() throws Exception
    {
        // given
        String jsonBody = """
                    [
                       {
                                          "name": "git-consortium",
                                          "owner": {
                                              "login": "octocat"
                                          },
                                          "branches": [
                                              {
                                                  "name": "master",
                                                  "commit": {
                                                      "sha": "b33a9c7c02ad93f621fa38f0e9fc9e867e12fa0e"
                                                  }
                                              }
                                          ],
                                          "fork": false
                                      }
                    ]
                """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(jsonBody)
                .addHeader("Content-Type", "application/json"));

        // when
        List<GitRepo> repos = gitClient.getRepos("octocat");

        // then
        assertNotNull(repos, "Repository list can't be null");
        assertFalse(repos.isEmpty(), "Repository list can't be empty");
        assertEquals("git-consortium", repos.get(0).getName());
        assertEquals("octocat", repos.get(0).getOwner().login());
    }

    @Test
    public void testGetBranchesFromGitHub()
    {
        // given
        String jsonBody = """
        [
          {
            "name": "main",
            "commit": {
              "sha": "abc123",
              "url": "https://api.github.com/repos/octocat/Hello-World/commits/abc123"
            }
          }
        ]
    """;

        mockWebServer.enqueue(new MockResponse()
                .setBody(jsonBody)
                .addHeader("Content-Type", "application/json"));

        // when
        List<Branch> branches = gitClient.getBranches("octocat", "Hello-World");

        // then
        assertNotNull(branches, "List of branches can't be null");
        assertFalse(branches.isEmpty(), "List of branches can't be empty");
        assertEquals("main", branches.get(0).name());
    }






}

