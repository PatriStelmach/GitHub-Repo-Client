package com.example.demo.Config;

import com.example.demo.Clients.ClientErrorDecoder;
import com.example.demo.Models.AppId;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig
{

    //AppId object since RequestInterceptor only accepts objects as parameters
    @Bean
    public RequestInterceptor requestInterceptor(AppId appId)
    {
        return requestTemplate ->
        {
            try
            {
                String token = GitAuth.getInstallToken(CreateJWT.generateJWTFromPKCS1File(appId.getId()));
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
