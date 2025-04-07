package com.example.demo.Errors;

import com.example.demo.Models.ExceptionMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.io.InputStream;

public class ClientErrorDecoder implements ErrorDecoder
{
    private final ErrorDecoder errorDecoder = new Default();

    //custom error when retrieving 404 user not found from GitHub API
    @Override
    public Exception decode(String methodKey, Response response)
    {
        ExceptionMessage message = null;
        try (InputStream bodyIs = response.body().asInputStream())
        {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, ExceptionMessage.class);
        }
        catch (IOException e)
        {
            return new Exception(e.getMessage());
        }
        if(response.status() == 404)
        {
            throw new UserNotFoundException(message.message());
        }
            else
            {
                return errorDecoder.decode(methodKey, response);
            }
        }



    }

