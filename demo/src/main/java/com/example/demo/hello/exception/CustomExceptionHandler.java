package com.example.demo.hello.exception;

import com.example.demo.experiment.controller.TriggerOOMController;
import com.example.demo.hello.response.ApiResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(TriggerOOMController.CustomException.class)
    public ApiResponse handleCustomException(TriggerOOMController.CustomException exception) {
        return ApiResponse.fail(exception.getMessage());
    }
}
