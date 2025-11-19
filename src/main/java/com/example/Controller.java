package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

    @GetMapping("/")
    public String hello() {
        return "Hello, World!";
    }

    @GetMapping("/health")
    public String heath() {
        return "{\"isAvailable\":true}";
    }
}

