package com.sages.project2.adapters.rest.dtos;

import lombok.Data;

@Data
public class SolutionDto {

    private Long userId;
    private String username;
    private String questName;
    private String solution;
    private boolean result;

}
