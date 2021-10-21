package com.example.usermgnt.dto;

import com.example.usermgnt.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

/**
 * @author Okala III
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto {

    private String firstName;

    private String lastName;

    private String userEmail;

    private Role appUserRole;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private String confirmPassword;


}