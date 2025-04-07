package com.example.demo.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties(ignoreUnknown = true)
public record Branch(String name, @JsonProperty("commit") Commit lastCommitSha) {

}
