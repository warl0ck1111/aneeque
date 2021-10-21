package com.example.usermgnt.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Okala III
 */
@Data
public class ApiResponse implements Serializable {

    @JsonIgnore
    @JsonProperty("status")
    private HttpStatus httpStatus;

    @JsonProperty("statusCode")
    private int statusCode;
    
    @JsonProperty("status")
    private String success;
        
    @JsonProperty("data")
    private Object data;
    
    @JsonProperty("message")
    private String message = "";
    
    @JsonIgnore
    @JsonProperty("timestamp")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private Date timestamp = new Date();
    
    
    public ApiResponse(HttpStatus httpStatus, boolean error, String message, Object data) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.data = data;
        this.statusCode = httpStatus.value();
        this.success = String.valueOf(!httpStatus.isError());
    }

    public ApiResponse(HttpStatus httpStatus, String message, Object data) {
        this.httpStatus = httpStatus;
        this.success = String.valueOf(!httpStatus.isError());
        this.message = message;
        this.data = data;
        this.statusCode = httpStatus.value();
        this.success = String.valueOf(!httpStatus.isError());


    }
    
    public ApiResponse(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.success = String.valueOf(!httpStatus.isError());
        this.message = message;
        this.statusCode = httpStatus.value();
        this.success = String.valueOf(!httpStatus.isError());


    }
    
    public ApiResponse(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public ApiResponse(String message) {
        this.message = message;
    }
        
    public ApiResponse(Object data) {
        this.data = data;
    }
    
    
    /**
     * @param result
     * @return
     */
    public static String[] getErrors(BindingResult result) {
        String[] errors = new String[result.getErrorCount()];
        int i = 0;
        for (Object object : result.getAllErrors()) {
            FieldError fieldError = (FieldError) object;
            errors[i] = fieldError.getDefaultMessage();
            i++;
        }
        return errors;
    }
    
}
