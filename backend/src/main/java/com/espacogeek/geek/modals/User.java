package com.espacogeek.geek.modals;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_user")
    private Integer id;

    @Size(max = 20, message = "{validation.name.size.too_long}")
    @Column(name = "username")
    private String username;
    
    @Size(max = 45, message = "{validation.name.size.too_long}")
    @Column(name = "email")
    private String email;

    @Size(max = 45, message = "{validation.name.size.too_long}")
    @Column(name = "password")
    private String password;

    // add users_library
}
