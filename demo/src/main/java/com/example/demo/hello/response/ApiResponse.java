package com.example.demo.hello.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
public class ApiResponse {
    private HttpStatus httpStatus;
    private LocalDateTime localDateTime;
    private String message;

    public ApiResponse(HttpStatus httpStatus, LocalDateTime localDateTime, String message) {
        this.httpStatus = httpStatus;
        this.localDateTime = localDateTime;
        this.message = message;
    }

    public static ApiResponse hello() {
        return new ApiResponse(HttpStatus.OK, LocalDateTime.now(), "hello world");
    }

    public static ApiResponse fail(String message) {
        return new ApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, LocalDateTime.now(), message);
    }
}
