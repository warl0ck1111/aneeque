package com.example.usermgnt.config;

import com.example.usermgnt.dto.AppUserDto;
import com.example.usermgnt.exception.RoleNotFoundException;
import com.example.usermgnt.model.AppUser;
import com.example.usermgnt.model.AppUserRole;
import com.example.usermgnt.model.Role;
import com.example.usermgnt.repository.RoleRepository;
import com.example.usermgnt.requests.RegisterUserRequest;
import com.example.usermgnt.service.AppUserService;
import com.example.usermgnt.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Okala III
 */

@Slf4j
@Component
public class ApplicationStartupConfig implements ApplicationListener<ContextRefreshedEvent> {


    @Autowired
    private RoleService roleService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private RoleRepository roleRepository;


    private boolean databaseAlreadyConfigured = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        List<Role> roleList = getRoles();
        for (Role r : roleList) {
            System.out.println(r.getName());
        }
        if (roleList.size() != 0) databaseAlreadyConfigured = true;

        if (!databaseAlreadyConfigured) {

            //create new role records
            log.info("database not configured");
            List<Role> roles = new ArrayList<>();
            for (AppUserRole role : AppUserRole.values()) {
                roles.add(new Role(role.name()));
            }
            roleService.getRoleRepository().saveAll(roles);

            Role userRole = roleRepository.findByName(AppUserRole.USER.name()).orElseThrow(() -> {
                log.error(" role does not exist");
                return new RoleNotFoundException(" role does not exist");
            });

            AppUserDto appUser = appUserService.signUpUser(
                    new RegisterUserRequest("Bashir", "Okala", "bashirokala@hotmail.com", "password", "password"));

        } else
            log.info("database is configured");

    }


    public List<Role> getRoles() {
        return roleService.findAll();

    }

}
