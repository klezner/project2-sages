package com.sages.project2;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldREST {

    private static final String TEXT = "hello World";

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/test")
    public String testOne() {
        return "test one";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/hello")
    public ResponseEntity<String> see() {
        return ResponseEntity.ok()
                .body(get());
    }

    public String get() {
        return TEXT;
    }
}