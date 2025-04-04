package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ExceptionMessage
{
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
}
