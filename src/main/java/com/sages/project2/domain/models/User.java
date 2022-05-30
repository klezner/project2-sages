package com.sages.project2.domain.models;

import lombok.Data;

@Data
public class User {

    private Long userId;
    private String login;
    private String role;
}
