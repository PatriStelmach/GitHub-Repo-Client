package com.example.demo.Config;

import com.example.demo.Models.GitToken;
import com.example.demo.Models.Installation;
import com.example.demo.Services.GitService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Scanner;

public class GitAuth
{

    public static Long getInstallList(String jwt) throws Exception
    {
        HttpClient client = HttpClient.newHttpClient();

        String url = "https://api.github.com/app/installations";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + jwt)
                .header("Accept", "application/vnd.github+json")
                .header("X-Github-Api-Version", "2022-11-28")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        //checking if app id is correct by getting status from the GitHub API
        while (response.statusCode() != 200)
        {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Wrong GitHub application ID.\n" +
                    "Check your application ID and try again.");
            {
                if(!scanner.hasNextLong())
                {
                    System.out.println("App ID must be a number.\n" +
                            "Try again.");
                    scanner.nextLine();
                }
                else
                {
                    return GitService.estAppId(Long.parseLong(scanner.nextLine()));
                }
            }


        }
        ObjectMapper mapper = new ObjectMapper();
        List<Installation> installations=  mapper.readValue(response.body(), mapper.getTypeFactory().constructCollectionType(List.class, Installation.class));

        String install = installations.toString();

        //parsing installations from string (JSON de facto) to long
        StringBuilder builder = new StringBuilder();
        for(char inst: install.toCharArray())
        {
            if(Character.isDigit(inst))
            {
                builder.append(inst);
            }

        }
        String token = builder.toString();
        return Long.parseLong(token);

    }

    public static String getInstallToken(String jwt) throws Exception
    {
        HttpClient client = HttpClient.newHttpClient();

        String url = String.format("https://api.github.com/app/installations/%d/access_tokens", getInstallList(jwt) );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + jwt)
                .header("Accept", "application/vnd.github.v3+json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        ObjectMapper mapper = new ObjectMapper();
        GitToken tokenResponse = mapper.readValue(response.body(), GitToken.class);

        return tokenResponse.getToken();

    }
}
