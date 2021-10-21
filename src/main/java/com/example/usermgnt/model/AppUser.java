package com.example.usermgnt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

/**
 * @author Okala III
 */
@Slf4j
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
public class AppUser implements UserDetails {
    @SequenceGenerator(name="user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @JsonProperty("userId")
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true, nullable = false)
    private String userEmail;

    @JsonIgnore
    private String password;


    @JoinColumn(name = "user_role_fk", referencedColumnName = "id")
    @OneToOne
    private Role appUserRole;
    private Boolean locked = false;
    private Boolean enabled = false;

    private Boolean isDeleted = Boolean.FALSE;



    @CreationTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd h:m:s")
    private LocalDateTime timeCreated;

    @UpdateTimestamp
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd h:m:s")
    private LocalDateTime timeUpdated;

    /**
     *
     * @param firstName
     * @param lastName
     * @param userEmail
     * @param password
     * @param appUserRole
     */
    public AppUser(String firstName, String lastName, String userEmail, String password, Role appUserRole) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userEmail = userEmail;
        this.password = password;
        this.appUserRole = appUserRole;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        log.info("getAuthorities called ");
        log.info("getAuthorities is: "+getAppUserRole().getName());

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(getAppUserRole().getName());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userEmail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
