package com.example.usermgnt.service;


import com.example.usermgnt.dto.AppUserDto;
import com.example.usermgnt.requests.LoginRequest;
import com.example.usermgnt.exception.ApiRequestException;
import com.example.usermgnt.exception.RoleNotFoundException;
import com.example.usermgnt.model.AppUser;
import com.example.usermgnt.model.AppUserRole;
import com.example.usermgnt.model.Role;
import com.example.usermgnt.repository.AppUserRepository;
import com.example.usermgnt.repository.RoleRepository;
import com.example.usermgnt.requests.RegisterUserRequest;
import com.example.usermgnt.util.validator.EmailValidator;
import com.example.usermgnt.util.validator.PasswordValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.example.usermgnt.util.Util.hasValue;

/**
 * @author Okala III
 */
@Slf4j
@Service
public class AppUserService implements UserDetailsService {

    private static final String USER_NOT_FOUND_MSG = "user with email %s not found";
    private static final int OTP_LENGTH = 6;
    private static final String OTP_MSG_TEMPLATE = "Your OTP is: %s";
    private static final int OTP_LIFETIME_IN_MINUTES = 15;


    @Autowired
    private EmailValidator emailValidator;
    @Autowired
    private PasswordValidator passwordValidator;


    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public UserDetails loadUserByUsername(String email) {
        return appUserRepository.findByUserEmail(email).orElseThrow(() -> {
            log.error(String.format(USER_NOT_FOUND_MSG, email));
            return new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email));
        });
    }



    @Transactional
    public AppUserDto signUpUser(RegisterUserRequest registerUserRequest)  {
        System.out.println(registerUserRequest);
        if (!hasValue(registerUserRequest.getFirstName())) {
            log.error("first name cannot be empty");
            throw new IllegalArgumentException("first name cannot be empty");
        }

        if (!hasValue(registerUserRequest.getLastName())) {
            log.error("last name cannot be empty");
            throw new IllegalArgumentException("last name cannot be empty");
        }


        if (!hasValue(registerUserRequest.getUserEmail())) {
            log.error("user phone no cannot be empty");
            throw new IllegalArgumentException("user email field cannot be empty");
        }

        if (!hasValue(registerUserRequest.getPassword())) {
            log.error("password field cannot be empty");
            throw new IllegalArgumentException("password field cannot be empty");
        }
        if (!hasValue(registerUserRequest.getConfirmPassword())) {
            log.error("Confirm password field cannot be empty");
            throw new IllegalArgumentException("confirm password field cannot be empty");
        }

        if (!registerUserRequest.getPassword().equals(registerUserRequest.getConfirmPassword())) {
            log.error("password mismatch");
            throw new IllegalArgumentException("passwords mismatch");
        }


        String email = registerUserRequest.getUserEmail();
        boolean isValidUserEmail = emailValidator.test(email);

        if (!isValidUserEmail) {
            log.error("email not valid");
            throw new IllegalArgumentException("email not valid");
        }


        Role role = roleRepository.findByName(AppUserRole.USER.name()).orElseThrow(() -> {
            log.error("role does not exist");
            return new RoleNotFoundException("role does not exist");
        });

        boolean userExists = appUserRepository.existsByUserEmail(registerUserRequest.getUserEmail());

        if (userExists) {
            log.error("Email already taken");
            throw new ApiRequestException("Email already taken", HttpStatus.BAD_REQUEST);
        }

        String encodedPassword = bCryptPasswordEncoder.encode((registerUserRequest.getPassword()));

        AppUser appUser = new AppUser();
        BeanUtils.copyProperties(registerUserRequest, appUser);
        appUser.setAppUserRole(role);
        appUser.setPassword(encodedPassword);
        appUser.setEnabled(true);

        AppUser newUser = appUserRepository.save(appUser);
        AppUserDto appUserDto = new AppUserDto();
        BeanUtils.copyProperties(newUser, appUserDto);


        return appUserDto;
    }



    @Transactional
    public AppUserDto loginUser(LoginRequest request) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        } catch (BadCredentialsException ex) {
            log.error("invalid username or password");
            throw new ApiRequestException("invalid username or password", HttpStatus.FORBIDDEN);
        } catch (DisabledException ex) {
            log.error("user is disabled");
            throw new ApiRequestException("user is disabled", HttpStatus.FORBIDDEN);
        } catch (LockedException ex) {
            log.error("user account is locked");
            throw new ApiRequestException("user account is locked", HttpStatus.LOCKED);
        }


        final UserDetails userDetails = loadUserByUsername(request.getEmail());
        AppUser appUser = appUserRepository.findByUserEmail(request.getEmail()).get();
        AppUserDto appUserDto = new AppUserDto();

        BeanUtils.copyProperties(appUser, appUserDto);

//        final String jwt = jwtTokenUtil.generateToken(userDetails);
        return appUserDto;

    }



    public List<AppUserDto> getAllAppUsers(int page, int size) {
        List<AppUserDto> response = new ArrayList<>();
        Page<AppUser> appUserList = appUserRepository.findAll(PageRequest.of(page, size));
        appUserList.forEach(appUser -> {
            AppUserDto appUserDto = new AppUserDto();
            BeanUtils.copyProperties(appUser, appUserDto);
            response.add(appUserDto);
        });
        return response;
    }


//
//    public void enableAppUser(String email) {
//        AppUser appUser = appUserRepository.findByUserEmail(email).orElseThrow(() -> {
//            log.error("email does not exist");
//            return new ApiRequestException("email does not exist", HttpStatus.NOT_FOUND);
//        });
//        appUser.setEnabled(true);
//        appUserRepository.save(appUser);
//
//    }
//
//    public void disableAppUser(String email) {
//        AppUser appUser = appUserRepository.findByUserEmail(email).orElseThrow(() -> {
//            log.error("email does not exist");
//            return new ApiRequestException("email does not exist", HttpStatus.NOT_FOUND);
//        });
//        appUser.setEnabled(false);
//        appUserRepository.save(appUser);
//
//    }


}
