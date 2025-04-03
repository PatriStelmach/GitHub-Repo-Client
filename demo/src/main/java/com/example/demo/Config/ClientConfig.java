package com.example.demo.Config;

import feign.RequestInterceptor;
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
                throw new RuntimeException("Nie można uzyskać tokena dostępu", e);
            }
        };
    }

}
