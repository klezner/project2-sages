package com.sages.project2.adapters.rest;

import com.sages.project2.adapters.exception.QuestNotFoundException;
import com.sages.project2.adapters.exception.UserNotFoundException;
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
        String description = "Invalid value[s] for the parameter[s]";
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(description));
    }

    @ExceptionHandler(QuestNotFoundException.class)
    public ResponseEntity<String> onQuestNotFound(QuestNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> onUserNotFound(UserNotFoundException exception){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }


//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ExceptionDto> onException(Exception exception) {
//        String description = "Internal Server Error";
//        return ResponseEntity
//                .status(HttpStatus.INTERNAL_SERVER_ERROR)
//                .body(new ExceptionDto(description));
//    }

}
