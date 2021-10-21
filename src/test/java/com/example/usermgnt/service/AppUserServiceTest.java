package com.example.usermgnt.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.usermgnt.dto.AppUserDto;
import com.example.usermgnt.exception.ApiRequestException;
import com.example.usermgnt.exception.RoleNotFoundException;
import com.example.usermgnt.model.AppUser;
import com.example.usermgnt.model.Role;
import com.example.usermgnt.repository.AppUserRepository;
import com.example.usermgnt.repository.RoleRepository;
import com.example.usermgnt.requests.LoginRequest;
import com.example.usermgnt.requests.RegisterUserRequest;
import com.example.usermgnt.util.validator.EmailValidator;
import com.example.usermgnt.util.validator.PasswordValidator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {AppUserService.class, BCryptPasswordEncoder.class})
@ExtendWith(SpringExtension.class)
class AppUserServiceTest {
    @MockBean
    private AppUserRepository appUserRepository;

    @Autowired
    private AppUserService appUserService;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private EmailValidator emailValidator;

    @MockBean
    private PasswordValidator passwordValidator;

    @MockBean
    private RoleRepository roleRepository;

    @Test
    void testLoadUserByUsername() {
        Role role = new Role();
        role.setIsDeleted(true);
        role.setId(123L);
        role.setName("Name");

        AppUser appUser = new AppUser();
        appUser.setLastName("Doe");
        appUser.setUserEmail("jane.doe@example.org");
        appUser.setPassword("iloveyou");
        appUser.setAppUserRole(role);
        appUser.setTimeCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setIsDeleted(true);
        appUser.setId(123L);
        appUser.setTimeUpdated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setEnabled(true);
        appUser.setLocked(true);
        appUser.setFirstName("Jane");
        Optional<AppUser> ofResult = Optional.<AppUser>of(appUser);
        when(this.appUserRepository.findByUserEmail((String) any())).thenReturn(ofResult);
        assertSame(appUser, this.appUserService.loadUserByUsername("jane.doe@example.org"));
        verify(this.appUserRepository).findByUserEmail((String) any());
    }

    @Test
    void testLoadUserByUsername2() {
        when(this.appUserRepository.findByUserEmail((String) any())).thenReturn(Optional.<AppUser>empty());
        assertThrows(UsernameNotFoundException.class, () -> this.appUserService.loadUserByUsername("jane.doe@example.org"));
        verify(this.appUserRepository).findByUserEmail((String) any());
    }

    @Test
    void testLoadUserByUsername3() {
        Role role = new Role();
        role.setIsDeleted(true);
        role.setId(123L);
        role.setName("Name");

        AppUser appUser = new AppUser();
        appUser.setLastName("Doe");
        appUser.setUserEmail("jane.doe@example.org");
        appUser.setPassword("iloveyou");
        appUser.setAppUserRole(role);
        appUser.setTimeCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setIsDeleted(true);
        appUser.setId(123L);
        appUser.setTimeUpdated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setEnabled(true);
        appUser.setLocked(true);
        appUser.setFirstName("Jane");
        Optional<AppUser> ofResult = Optional.<AppUser>of(appUser);
        when(this.appUserRepository.findByUserEmail((String) any())).thenReturn(ofResult);
        assertSame(appUser, this.appUserService.loadUserByUsername("jane.doe@example.org"));
        verify(this.appUserRepository).findByUserEmail((String) any());
    }

    @Test
    void testLoadUserByUsername4() {
        when(this.appUserRepository.findByUserEmail((String) any())).thenReturn(Optional.<AppUser>empty());
        assertThrows(UsernameNotFoundException.class, () -> this.appUserService.loadUserByUsername("jane.doe@example.org"));
        verify(this.appUserRepository).findByUserEmail((String) any());
    }

    @Test
    void testSignUpUser() {
        Role role = new Role();
        role.setIsDeleted(true);
        role.setId(123L);
        role.setName("Name");
        Optional<Role> ofResult = Optional.<Role>of(role);
        when(this.roleRepository.findByName((String) any())).thenReturn(ofResult);
        when(this.emailValidator.test((String) any())).thenReturn(true);
        when(this.appUserRepository.existsByUserEmail((String) any())).thenReturn(true);
        assertThrows(ApiRequestException.class, () -> this.appUserService
                .signUpUser(new RegisterUserRequest("Jane", "Doe", "jane.doe@example.org", "iloveyou", "iloveyou")));
        verify(this.roleRepository).findByName((String) any());
        verify(this.emailValidator).test((String) any());
        verify(this.appUserRepository).existsByUserEmail((String) any());
    }

    @Test
    void testSignUpUser2() {
        when(this.roleRepository.findByName((String) any())).thenReturn(Optional.<Role>empty());
        when(this.emailValidator.test((String) any())).thenReturn(true);
        when(this.appUserRepository.existsByUserEmail((String) any())).thenReturn(true);
        assertThrows(RoleNotFoundException.class, () -> this.appUserService
                .signUpUser(new RegisterUserRequest("Jane", "Doe", "jane.doe@example.org", "iloveyou", "iloveyou")));
        verify(this.roleRepository).findByName((String) any());
        verify(this.emailValidator).test((String) any());
    }

    @Test
    void testSignUpUser3() {
        Role role = new Role();
        role.setIsDeleted(true);
        role.setId(123L);
        role.setName("Name");
        Optional<Role> ofResult = Optional.<Role>of(role);
        when(this.roleRepository.findByName((String) any())).thenReturn(ofResult);
        when(this.emailValidator.test((String) any())).thenReturn(false);
        when(this.appUserRepository.existsByUserEmail((String) any())).thenReturn(true);
        assertThrows(IllegalArgumentException.class, () -> this.appUserService
                .signUpUser(new RegisterUserRequest("Jane", "Doe", "jane.doe@example.org", "iloveyou", "iloveyou")));
        verify(this.emailValidator).test((String) any());
    }

    @Test
    void testSignUpUser4() {
        Role role = new Role();
        role.setIsDeleted(true);
        role.setId(123L);
        role.setName("Name");
        Optional<Role> ofResult = Optional.<Role>of(role);
        when(this.roleRepository.findByName((String) any())).thenReturn(ofResult);
        when(this.emailValidator.test((String) any())).thenReturn(true);

        Role role1 = new Role();
        role1.setIsDeleted(true);
        role1.setId(123L);
        role1.setName("Name");

        AppUser appUser = new AppUser();
        appUser.setLastName("Doe");
        appUser.setUserEmail("jane.doe@example.org");
        appUser.setPassword("iloveyou");
        appUser.setAppUserRole(role1);
        appUser.setTimeCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setIsDeleted(true);
        appUser.setId(123L);
        appUser.setTimeUpdated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setEnabled(true);
        appUser.setLocked(true);
        appUser.setFirstName("Jane");
        when(this.appUserRepository.save((AppUser) any())).thenReturn(appUser);
        when(this.appUserRepository.existsByUserEmail((String) any())).thenReturn(false);
        AppUserDto actualSignUpUserResult = this.appUserService
                .signUpUser(new RegisterUserRequest("Jane", "Doe", "jane.doe@example.org", "iloveyou", "iloveyou"));
        assertEquals(role, actualSignUpUserResult.getAppUserRole());
        assertEquals("Doe", actualSignUpUserResult.getLastName());
        assertEquals("iloveyou", actualSignUpUserResult.getPassword());
        assertEquals("Jane", actualSignUpUserResult.getFirstName());
        assertEquals("jane.doe@example.org", actualSignUpUserResult.getUserEmail());
        verify(this.roleRepository).findByName((String) any());
        verify(this.emailValidator).test((String) any());
        verify(this.appUserRepository).existsByUserEmail((String) any());
        verify(this.appUserRepository).save((AppUser) any());
    }

    @Test
    void testSignUpUser5() {
        Role role = new Role();
        role.setIsDeleted(true);
        role.setId(123L);
        role.setName("Name");
        Optional<Role> ofResult = Optional.<Role>of(role);
        when(this.roleRepository.findByName((String) any())).thenReturn(ofResult);
        when(this.emailValidator.test((String) any())).thenReturn(true);

        Role role1 = new Role();
        role1.setIsDeleted(true);
        role1.setId(123L);
        role1.setName("Name");

        AppUser appUser = new AppUser();
        appUser.setLastName("Doe");
        appUser.setUserEmail("jane.doe@example.org");
        appUser.setPassword("iloveyou");
        appUser.setAppUserRole(role1);
        appUser.setTimeCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setIsDeleted(true);
        appUser.setId(123L);
        appUser.setTimeUpdated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setEnabled(true);
        appUser.setLocked(true);
        appUser.setFirstName("Jane");
        when(this.appUserRepository.save((AppUser) any())).thenReturn(appUser);
        when(this.appUserRepository.existsByUserEmail((String) any())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> this.appUserService
                .signUpUser(new RegisterUserRequest("", "Doe", "jane.doe@example.org", "iloveyou", "iloveyou")));
    }

    @Test
    void testSignUpUser6() {
        Role role = new Role();
        role.setIsDeleted(true);
        role.setId(123L);
        role.setName("Name");
        Optional<Role> ofResult = Optional.<Role>of(role);
        when(this.roleRepository.findByName((String) any())).thenReturn(ofResult);
        when(this.emailValidator.test((String) any())).thenReturn(true);

        Role role1 = new Role();
        role1.setIsDeleted(true);
        role1.setId(123L);
        role1.setName("Name");

        AppUser appUser = new AppUser();
        appUser.setLastName("Doe");
        appUser.setUserEmail("jane.doe@example.org");
        appUser.setPassword("iloveyou");
        appUser.setAppUserRole(role1);
        appUser.setTimeCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setIsDeleted(true);
        appUser.setId(123L);
        appUser.setTimeUpdated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setEnabled(true);
        appUser.setLocked(true);
        appUser.setFirstName("Jane");
        when(this.appUserRepository.save((AppUser) any())).thenReturn(appUser);
        when(this.appUserRepository.existsByUserEmail((String) any())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> this.appUserService
                .signUpUser(new RegisterUserRequest("Jane", "", "jane.doe@example.org", "iloveyou", "iloveyou")));
    }

    @Test
    void testSignUpUser7() {
        Role role = new Role();
        role.setIsDeleted(true);
        role.setId(123L);
        role.setName("Name");
        Optional<Role> ofResult = Optional.<Role>of(role);
        when(this.roleRepository.findByName((String) any())).thenReturn(ofResult);
        when(this.emailValidator.test((String) any())).thenReturn(true);

        Role role1 = new Role();
        role1.setIsDeleted(true);
        role1.setId(123L);
        role1.setName("Name");

        AppUser appUser = new AppUser();
        appUser.setLastName("Doe");
        appUser.setUserEmail("jane.doe@example.org");
        appUser.setPassword("iloveyou");
        appUser.setAppUserRole(role1);
        appUser.setTimeCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setIsDeleted(true);
        appUser.setId(123L);
        appUser.setTimeUpdated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setEnabled(true);
        appUser.setLocked(true);
        appUser.setFirstName("Jane");
        when(this.appUserRepository.save((AppUser) any())).thenReturn(appUser);
        when(this.appUserRepository.existsByUserEmail((String) any())).thenReturn(false);
        assertThrows(IllegalArgumentException.class,
                () -> this.appUserService.signUpUser(new RegisterUserRequest("Jane", "Doe", "", "iloveyou", "iloveyou")));
    }

    @Test
    void testSignUpUser8() {
        Role role = new Role();
        role.setIsDeleted(true);
        role.setId(123L);
        role.setName("Name");
        Optional<Role> ofResult = Optional.<Role>of(role);
        when(this.roleRepository.findByName((String) any())).thenReturn(ofResult);
        when(this.emailValidator.test((String) any())).thenReturn(true);

        Role role1 = new Role();
        role1.setIsDeleted(true);
        role1.setId(123L);
        role1.setName("Name");

        AppUser appUser = new AppUser();
        appUser.setLastName("Doe");
        appUser.setUserEmail("jane.doe@example.org");
        appUser.setPassword("iloveyou");
        appUser.setAppUserRole(role1);
        appUser.setTimeCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setIsDeleted(true);
        appUser.setId(123L);
        appUser.setTimeUpdated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setEnabled(true);
        appUser.setLocked(true);
        appUser.setFirstName("Jane");
        when(this.appUserRepository.save((AppUser) any())).thenReturn(appUser);
        when(this.appUserRepository.existsByUserEmail((String) any())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> this.appUserService
                .signUpUser(new RegisterUserRequest("Jane", "Doe", "jane.doe@example.org", "Password", "iloveyou")));
    }

    @Test
    void testSignUpUser9() {
        Role role = new Role();
        role.setIsDeleted(true);
        role.setId(123L);
        role.setName("Name");
        Optional<Role> ofResult = Optional.<Role>of(role);
        when(this.roleRepository.findByName((String) any())).thenReturn(ofResult);
        when(this.emailValidator.test((String) any())).thenReturn(true);

        Role role1 = new Role();
        role1.setIsDeleted(true);
        role1.setId(123L);
        role1.setName("Name");

        AppUser appUser = new AppUser();
        appUser.setLastName("Doe");
        appUser.setUserEmail("jane.doe@example.org");
        appUser.setPassword("iloveyou");
        appUser.setAppUserRole(role1);
        appUser.setTimeCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setIsDeleted(true);
        appUser.setId(123L);
        appUser.setTimeUpdated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setEnabled(true);
        appUser.setLocked(true);
        appUser.setFirstName("Jane");
        when(this.appUserRepository.save((AppUser) any())).thenReturn(appUser);
        when(this.appUserRepository.existsByUserEmail((String) any())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> this.appUserService
                .signUpUser(new RegisterUserRequest("Jane", "Doe", "jane.doe@example.org", "", "iloveyou")));
    }

    @Test
    void testSignUpUser10() {
        Role role = new Role();
        role.setIsDeleted(true);
        role.setId(123L);
        role.setName("Name");
        Optional<Role> ofResult = Optional.<Role>of(role);
        when(this.roleRepository.findByName((String) any())).thenReturn(ofResult);
        when(this.emailValidator.test((String) any())).thenReturn(true);

        Role role1 = new Role();
        role1.setIsDeleted(true);
        role1.setId(123L);
        role1.setName("Name");

        AppUser appUser = new AppUser();
        appUser.setLastName("Doe");
        appUser.setUserEmail("jane.doe@example.org");
        appUser.setPassword("iloveyou");
        appUser.setAppUserRole(role1);
        appUser.setTimeCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setIsDeleted(true);
        appUser.setId(123L);
        appUser.setTimeUpdated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setEnabled(true);
        appUser.setLocked(true);
        appUser.setFirstName("Jane");
        when(this.appUserRepository.save((AppUser) any())).thenReturn(appUser);
        when(this.appUserRepository.existsByUserEmail((String) any())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> this.appUserService
                .signUpUser(new RegisterUserRequest("Jane", "Doe", "jane.doe@example.org", "iloveyou", "")));
    }

    @Test
    void testSignUpUser11() {
        Role role = new Role();
        role.setIsDeleted(true);
        role.setId(123L);
        role.setName("Name");
        Optional<Role> ofResult = Optional.<Role>of(role);
        when(this.roleRepository.findByName((String) any())).thenReturn(ofResult);
        when(this.emailValidator.test((String) any())).thenReturn(true);

        Role role1 = new Role();
        role1.setIsDeleted(true);
        role1.setId(123L);
        role1.setName("Name");

        AppUser appUser = new AppUser();
        appUser.setLastName("Doe");
        appUser.setUserEmail("jane.doe@example.org");
        appUser.setPassword("iloveyou");
        appUser.setAppUserRole(role1);
        appUser.setTimeCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setIsDeleted(true);
        appUser.setId(123L);
        appUser.setTimeUpdated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setEnabled(true);
        appUser.setLocked(true);
        appUser.setFirstName("Jane");
        when(this.appUserRepository.save((AppUser) any())).thenReturn(appUser);
        when(this.appUserRepository.existsByUserEmail((String) any())).thenReturn(false);
        assertThrows(IllegalArgumentException.class, () -> this.appUserService.signUpUser(new RegisterUserRequest()));
    }

    @Test
    void testLoginUser() throws AuthenticationException {
        when(this.authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));

        Role role = new Role();
        role.setIsDeleted(true);
        role.setId(123L);
        role.setName("Name");

        AppUser appUser = new AppUser();
        appUser.setLastName("Doe");
        appUser.setUserEmail("jane.doe@example.org");
        appUser.setPassword("iloveyou");
        appUser.setAppUserRole(role);
        appUser.setTimeCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setIsDeleted(true);
        appUser.setId(123L);
        appUser.setTimeUpdated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setEnabled(true);
        appUser.setLocked(true);
        appUser.setFirstName("Jane");
        Optional<AppUser> ofResult = Optional.<AppUser>of(appUser);
        when(this.appUserRepository.findByUserEmail((String) any())).thenReturn(ofResult);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("jane.doe@example.org");
        loginRequest.setPassword("iloveyou");
        AppUserDto actualLoginUserResult = this.appUserService.loginUser(loginRequest);
        assertEquals(
                "AppUserDto(firstName=Jane, lastName=Doe, userEmail=jane.doe@example.org, appUserRole=Role(id=123,"
                        + " name=Name, isDeleted=true), password=iloveyou, confirmPassword=null)",
                actualLoginUserResult.toString());
        assertEquals("Doe", actualLoginUserResult.getLastName());
        assertEquals("iloveyou", actualLoginUserResult.getPassword());
        assertEquals("Jane", actualLoginUserResult.getFirstName());
        assertEquals("jane.doe@example.org", actualLoginUserResult.getUserEmail());
        verify(this.authenticationManager).authenticate((Authentication) any());
        verify(this.appUserRepository, atLeast(1)).findByUserEmail((String) any());
    }

    @Test
    void testLoginUser2() throws AuthenticationException {
        when(this.authenticationManager.authenticate((Authentication) any()))
                .thenReturn(new TestingAuthenticationToken("Principal", "Credentials"));
        when(this.appUserRepository.findByUserEmail((String) any())).thenReturn(Optional.<AppUser>empty());

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("jane.doe@example.org");
        loginRequest.setPassword("iloveyou");
        assertThrows(UsernameNotFoundException.class, () -> this.appUserService.loginUser(loginRequest));
        verify(this.authenticationManager).authenticate((Authentication) any());
        verify(this.appUserRepository).findByUserEmail((String) any());
    }

    @Test
    void testGetAllAppUsers() {
        when(this.appUserRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<AppUser>(new ArrayList<AppUser>()));
        assertTrue(this.appUserService.getAllAppUsers(1, 3).isEmpty());
        verify(this.appUserRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetAllAppUsers2() {
        Role role = new Role();
        role.setIsDeleted(true);
        role.setId(123L);
        role.setName("Name");

        AppUser appUser = new AppUser();
        appUser.setLastName("Doe");
        appUser.setUserEmail("jane.doe@example.org");
        appUser.setPassword("iloveyou");
        appUser.setAppUserRole(role);
        appUser.setTimeCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setIsDeleted(true);
        appUser.setId(123L);
        appUser.setTimeUpdated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setEnabled(true);
        appUser.setLocked(true);
        appUser.setFirstName("Jane");

        ArrayList<AppUser> appUserList = new ArrayList<AppUser>();
        appUserList.add(appUser);
        PageImpl<AppUser> pageImpl = new PageImpl<AppUser>(appUserList);
        when(this.appUserRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        assertEquals(1, this.appUserService.getAllAppUsers(1, 3).size());
        verify(this.appUserRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetAllAppUsers3() {
        Role role = new Role();
        role.setIsDeleted(true);
        role.setId(123L);
        role.setName("Name");

        AppUser appUser = new AppUser();
        appUser.setLastName("Doe");
        appUser.setUserEmail("jane.doe@example.org");
        appUser.setPassword("iloveyou");
        appUser.setAppUserRole(role);
        appUser.setTimeCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setIsDeleted(true);
        appUser.setId(123L);
        appUser.setTimeUpdated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser.setEnabled(true);
        appUser.setLocked(true);
        appUser.setFirstName("Jane");

        Role role1 = new Role();
        role1.setIsDeleted(true);
        role1.setId(123L);
        role1.setName("Name");

        AppUser appUser1 = new AppUser();
        appUser1.setLastName("Doe");
        appUser1.setUserEmail("jane.doe@example.org");
        appUser1.setPassword("iloveyou");
        appUser1.setAppUserRole(role1);
        appUser1.setTimeCreated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser1.setIsDeleted(true);
        appUser1.setId(123L);
        appUser1.setTimeUpdated(LocalDateTime.of(1, 1, 1, 1, 1));
        appUser1.setEnabled(true);
        appUser1.setLocked(true);
        appUser1.setFirstName("Jane");

        ArrayList<AppUser> appUserList = new ArrayList<AppUser>();
        appUserList.add(appUser1);
        appUserList.add(appUser);
        PageImpl<AppUser> pageImpl = new PageImpl<AppUser>(appUserList);
        when(this.appUserRepository.findAll((org.springframework.data.domain.Pageable) any())).thenReturn(pageImpl);
        assertEquals(2, this.appUserService.getAllAppUsers(1, 3).size());
        verify(this.appUserRepository).findAll((org.springframework.data.domain.Pageable) any());
    }

    @Test
    void testGetAllAppUsers4() {
        when(this.appUserRepository.findAll((org.springframework.data.domain.Pageable) any()))
                .thenReturn(new PageImpl<AppUser>(new ArrayList<AppUser>()));
        assertTrue(this.appUserService.getAllAppUsers(0, 3).isEmpty());
        verify(this.appUserRepository).findAll((org.springframework.data.domain.Pageable) any());
    }
}

