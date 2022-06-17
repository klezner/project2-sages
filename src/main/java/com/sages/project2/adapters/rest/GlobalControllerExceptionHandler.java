package com.sages.project2.adapters.rest;

import com.sages.project2.adapters.rest.dtos.ExceptionDto;
import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(ConversionFailedException.class)
    public ResponseEntity<ExceptionDto> onConversionFailedException(ConversionFailedException exception) {
        String description = "Invalid value for the difficulty parameter";
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(description));
    }

//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ExceptionDto> onException(Exception exception) {
//        String description = "Internal Server Error";
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(new ExceptionDto(description));
//    }

}
