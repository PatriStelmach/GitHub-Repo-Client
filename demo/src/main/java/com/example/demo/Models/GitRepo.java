package com.example.demo.Models;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
@Data
public class GitRepo
{

    private final String name;

    private final Owner owner;

    private List<Branch> branches;
    private final boolean fork;
}
