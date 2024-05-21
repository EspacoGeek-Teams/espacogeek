package com.espacogeek.geek.modals;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class UserModal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    @Size(max = 20, message = "{validation.name.size.too_long}")
    @Column(name = "username", nullable = false)
    private String username;
    
    @Size(max = 45, message = "{validation.name.size.too_long}")
    @Column(name = "email", nullable = false)
    private String email;

    @Size(max = 45, message = "{validation.name.size.too_long}")
    @Column(name = "password", nullable = false)
    private String password;
}
