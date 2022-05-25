package com.sages.project2.adapter.rest.dto;

import lombok.Data;

@Data
public class UserDto {

    private Long userId;
    private String login;
    private String role;
}
