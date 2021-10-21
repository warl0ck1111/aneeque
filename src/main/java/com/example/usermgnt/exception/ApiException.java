package com.example.usermgnt.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ApiException {
    private final String message;
    @JsonIgnore
    private final Throwable throwable;
    private final HttpStatus httpStatus;
    private final LocalDateTime timeStamp;
    private final int statusCode;
    @JsonProperty("status")
    private String success;


    public ApiException(String message, Throwable throwable, HttpStatus httpStatus, LocalDateTime timeStamp ) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
        this.statusCode = httpStatus.value();
        this.success = String.valueOf(!httpStatus.isError());
    }
}
