package com.sages.project2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldREST {

    private static final String text= "hello World";

    @GetMapping("/test")
    public String testOne() {
        return "test one";
    }

    @RequestMapping("/hello")
    public ResponseEntity<String> see(){
        return ResponseEntity.ok()
                .body(get());
    }

    public String get(){
        return text;
    }
}