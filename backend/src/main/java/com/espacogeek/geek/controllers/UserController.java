package com.espacogeek.geek.controllers;

import java.beans.Beans;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import com.espacogeek.geek.dto.UserInput;
import com.espacogeek.geek.exception.GenericExeption;
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
    public UserModal registerUser(@Argument UserInput userInput) {
        var passwordCrypted = BCrypt.withDefaults().hash(12, userInput.password().toCharArray());
        UserModal user = new UserModal(null, userInput.username(), userInput.email(), passwordCrypted);
        return userService.save(user);
    }
}
