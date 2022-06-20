package com.sages.project2.adapters.rest.dtos;

import lombok.Data;

@Data
public class SolutionDto {

    private Long userId;
    private String username;
    private Long questId;
    private String solution;
    private boolean result;

}
