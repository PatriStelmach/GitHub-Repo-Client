package com.example.demo.Errors;

import feign.codec.ErrorDecoder;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Decoder
{
    @Bean
    public ErrorDecoder errorDecoder() {
        return new ClientErrorDecoder();
    }
}

