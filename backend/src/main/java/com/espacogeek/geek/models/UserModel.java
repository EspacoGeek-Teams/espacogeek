package com.espacogeek.geek.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.util.Assert;
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
    @Email()
    private String email;

    @Size(max = 70, message = "Password too long")
    @Column(name = "password", nullable = false)
    private byte[] password;

    @OneToMany(mappedBy = "user")
    @Transient
    private List<UserLibraryModel> userLibrary;

    @Transient
    private List<GrantedAuthority> authorities = new ArrayList<>();

    public UserModel(Integer id, @Size(max = 20, message = "username too long") String username,
            @Size(max = 50, message = "email too long") @Email String email,
            @Size(max = 70, message = "Password too long") byte[] password, List<UserLibraryModel> userLibrary) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.userLibrary = userLibrary;
    }

    @Override
    public String getPassword() {
        return new String(this.password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public UserBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "authorities cannot be null");
        this.authorities = new ArrayList<>(authorities);
        return User.builder().authorities(authorities);
    }

    public UserBuilder authorities(String... authorities) {
        Assert.notNull(authorities, "authorities cannot be null");
        return authorities(AuthorityUtils.createAuthorityList(authorities));
    }

    public UserBuilder roles(String... roles) {
        List<GrantedAuthority> authorities = new ArrayList<>(roles.length);
        for (String role : roles) {
            Assert.isTrue(!role.startsWith("ROLE_"),
                    () -> role + " cannot start with ROLE_ (it is automatically added)");
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        return authorities(authorities);
    }
}
