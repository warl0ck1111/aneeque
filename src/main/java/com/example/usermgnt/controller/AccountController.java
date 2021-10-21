package com.example.usermgnt.controller;

import com.example.usermgnt.dto.AppUserDto;
import com.example.usermgnt.requests.LoginRequest;
import com.example.usermgnt.requests.RegisterUserRequest;
import com.example.usermgnt.response.ApiResponse;
import com.example.usermgnt.service.AppUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * @author Okala III
 */

@Slf4j
@RestController
@RequestMapping(path = "/api/v1/auth/")
public class AccountController {


    public static final String USER_SIGNUP = "user/signup";
    public static final String USER_LOGIN = "user/login";
    public static final String GET_ALL_USERS = "get-all";

    @Autowired
    private AppUserService appUserService;


    @PostMapping(path = USER_SIGNUP)
    public ResponseEntity<ApiResponse> registerUser(@RequestBody RegisterUserRequest request)  {
        System.out.println(request);
        AppUserDto appUser = appUserService.signUpUser(request);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.CREATED, "user registered successfully", appUser), HttpStatus.CREATED);
    }


    @PostMapping(path = USER_LOGIN)
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        AppUserDto appUser = appUserService.loginUser(request);
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "login successful", appUser), HttpStatus.OK);
    }

    @GetMapping(path = GET_ALL_USERS)
    public ResponseEntity<ApiResponse> getAllUsers(@RequestParam int page, @RequestParam int size ){
        return new ResponseEntity<>(new ApiResponse(HttpStatus.OK, "see object for details ", appUserService.getAllAppUsers(page,size)), HttpStatus.OK);
    }

}
