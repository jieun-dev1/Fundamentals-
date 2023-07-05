package com.example.demo.hello.controller;

import com.example.demo.hello.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    @GetMapping("/hello")
    public ApiResponse hello() {
        return ApiResponse.hello();
    }
}
