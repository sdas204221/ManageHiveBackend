package com.sdas204221.ManageHive.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ManageHive")
public class TestController {
    @GetMapping
    public ResponseEntity<String> test(){
        return new ResponseEntity<>("This is a test end point", HttpStatus.OK);
    }
}
