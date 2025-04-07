package com.example.demo.Config;

import com.example.demo.Errors.ClientErrorDecoder;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig
{

    @Bean
    public RequestInterceptor requestInterceptor()
    {

        return requestTemplate ->
        {

            try
            {
                String token = GitAuth.getInstallToken(CreateJWT.generateJWTFromPKCS1File());
                requestTemplate.header("Authorization", "token " + token);
            }

            catch (Exception e)
            {
                throw new RuntimeException("");
            }
        };


    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ClientErrorDecoder();
    }
}