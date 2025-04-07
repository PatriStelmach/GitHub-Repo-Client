package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExceptionMessage(String timestamp, int status, String error, String message, String path) {
}
