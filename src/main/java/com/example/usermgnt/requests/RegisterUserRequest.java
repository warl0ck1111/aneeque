package com.example.usermgnt.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Okala III
 */


@Data
@NoArgsConstructor
@AllArgsConstructor

public class RegisterUserRequest {
    private String firstName;

    private String lastName;


    private String userEmail;

    private String password;

    private String confirmPassword;


}
