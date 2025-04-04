package com.example.demo.Clients;

import com.example.demo.Models.ExceptionMessage;
import com.example.demo.UserNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;

public class ClientErrorDecoder implements ErrorDecoder
{
    private final ErrorDecoder errorDecoder = new Default();
    @Override
    public Exception decode(String methodKey, Response response)
    {
        ExceptionMessage message = null;
        try (InputStream bodyIs = response.body().asInputStream())
        {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, ExceptionMessage.class);
        } catch (IOException e)
        {
            return new Exception(e.getMessage());
        }
        if (response.status() == 404)
        {
        throw new UserNotFoundException(message.getMessage());
        }
        else
        {
           return errorDecoder.decode(methodKey, response);
        }

    }


}
