package com.example.usermgnt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Okala III
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {
    @SequenceGenerator(name = "app_user_role_sequence", sequenceName = "app_user_role_sequence", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "app_user_role_sequence")
    @JsonIgnore
    private Long id;

    @Column(unique = true)
    private String name;

    @JsonIgnore
    @Basic(optional = true)
    @Column(name = "IS_DELETED")
    private Boolean isDeleted = Boolean.FALSE;

    public Role(String name) {
        this.name = name;
    }

    public Role(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
