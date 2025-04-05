package com.example.demo.Config;

import com.example.demo.Models.GitToken;
import com.example.demo.Models.Installation;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

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

        ObjectMapper mapper = new ObjectMapper();
        List<Installation> installations=  mapper.readValue(response.body(), mapper.getTypeFactory().constructCollectionType(List.class, Installation.class));

        String install = installations.toString();

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
