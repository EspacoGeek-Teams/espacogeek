package com.espacogeek.geek.modals;

import java.io.Serializable;
import java.util.Optional;

import org.springframework.http.HttpStatus;

import com.espacogeek.geek.exception.GenericExeption;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserModal implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    @Size(max = 20, message = "username too long")
    @Column(name = "username", nullable = false)
    private String username;
    
    @Size(max = 50, message = "email too long")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Size(max = 70, message = "Password too long")
    @Column(name = "password", nullable = false)
    private byte[] password;
}
