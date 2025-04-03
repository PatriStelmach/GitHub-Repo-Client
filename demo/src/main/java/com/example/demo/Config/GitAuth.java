package com.example.demo.Config;

import com.example.demo.Models.GitToken;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class GitAuth
{
    public static String getInstallToken(String jwt) throws Exception
    {
        HttpClient client = HttpClient.newHttpClient();

        String url = String.format("https://api.github.com/app/installations/%d/access_tokens", 63803159 );

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
