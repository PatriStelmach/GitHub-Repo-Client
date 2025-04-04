package com.example.demo.Config;

import com.example.demo.Clients.ClientErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class ClientConfig
{
    @Bean
    public RequestInterceptor requestInterceptor()
    {
        return requestTemplate ->
        {
            try
            {
                String token = GitAuth.getInstallToken(CreateJWT.generateJWT());
                requestTemplate.header("Authorization", "token " + token);
            }
            catch (Exception e)
            {
                throw new RuntimeException("Unable to obtain access token", e);
            }
        };
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ClientErrorDecoder();
    }
}
