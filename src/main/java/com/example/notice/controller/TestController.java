package com.example.notice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping(value = "/live")
    public ResponseEntity<?> live() {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
