package com.sages.project2.adapters.rest.dtos;

import lombok.Data;

@Data
public class UserDto {

    private Long userId;
    private String login;
    private String role;
}
