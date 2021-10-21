package com.example.usermgnt.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ApiRequestException extends RuntimeException{

    private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    public ApiRequestException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
