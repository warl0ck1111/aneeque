package com.example.usermgnt.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author Okala III
 */

@Data
public class LoginRequest {
    @NotNull
    private String email;
    @NotNull
    private String password;
}
