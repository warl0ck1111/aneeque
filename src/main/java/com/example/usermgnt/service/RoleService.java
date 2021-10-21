package com.example.usermgnt.service;

import com.example.usermgnt.exception.ApiRequestException;
import com.example.usermgnt.exception.RoleNotFoundException;
import com.example.usermgnt.model.Role;
import com.example.usermgnt.repository.RoleRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Okala III
 */
@Slf4j
@Getter
@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional
    public Role saveRole(String roleName){
        boolean isRoleExist = roleRepository.existsByName(roleName);
        if(isRoleExist){
            log.error("Role already exists");
            throw new ApiRequestException("Role already exists", HttpStatus.BAD_REQUEST);
        }
        Role role = new Role(roleName);
        return roleRepository.save(role);

    }

    public List<Role> findAll(){
        List<Role> roleList = new ArrayList<>();
        List<Role> roles = roleRepository.findAll();
        for(Role role : roles){
            String roleName = role.getName().replace("_", " ");
            roleList.add(new Role(role.getId(),roleName));
        }
        return roleList;
    }

    public Role findByName(String roleName){
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> {
            log.error("role does not exist");
            return new RoleNotFoundException("role does not exist");
        });
        return role;
    }

}
