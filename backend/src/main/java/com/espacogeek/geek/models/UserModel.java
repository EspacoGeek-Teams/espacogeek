package com.espacogeek.geek.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class UserModel implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Integer id;

    @Size(max = 20, message = "username too long")
    @Column(name = "username", nullable = false)
    private String username;
    
    @Size(max = 50, message = "email too long")
    @Column(name = "email", nullable = false, unique = true)
    @Email(message = "Must provided a valid email")
    private String email;

    @Size(max = 70, message = "Password too long")
    @Column(name = "password", nullable = false)
    private byte[] password;

    @OneToMany(mappedBy = "user")
    @Transient
    private List<UserLibraryModel> userLibrary;

    @Override
    public String getPassword() {
        return new String(this.password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }
}
