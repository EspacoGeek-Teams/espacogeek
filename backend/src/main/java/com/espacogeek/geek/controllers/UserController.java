package com.espacogeek.geek.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.espacogeek.geek.modals.UserModal;
import com.espacogeek.geek.services.User.UserService;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @QueryMapping
    public Optional<UserModal> findUserByUsername(@Argument String username) {
        return userService.findByUsernameContains(username);
    }

    @QueryMapping
    public Optional<UserModal> findUserById(@Argument Integer id) {
        return userService.findById(id);
    }

    @QueryMapping
    public Optional<UserModal> findUserByEmail(@Argument String email) {
        return userService.findByEmail(email);
    }

    @MutationMapping
    public UserModal registerUser(@Argument String username, @Argument String email, @Argument String password) {
        var passwordCrypted = BCrypt.withDefaults().hash(12, password.toCharArray()); // Encrypt password
        UserModal user = new UserModal(null, username, email, passwordCrypted);
        return userService.save(user);
    }
}
