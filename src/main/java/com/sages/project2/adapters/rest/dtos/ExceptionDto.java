package com.sages.project2.adapters.rest.dtos;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class ExceptionDto {

    LocalDateTime timestamp = LocalDateTime.now();
    String description;

}
