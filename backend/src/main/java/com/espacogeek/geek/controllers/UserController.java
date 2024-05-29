package com.espacogeek.geek.controllers;

import java.beans.Beans;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;

import com.espacogeek.geek.exception.GenericExeption;
import com.espacogeek.geek.modals.UserModal;
import com.espacogeek.geek.services.User.UserService;
import com.espacogeek.geek.types.NewUser;
import com.espacogeek.geek.types.UserInput;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.annotation.security.PermitAll;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @QueryMapping
    public Optional<UserModal> findUser(@Argument(name = "filter") UserInput userInput) {
        return userService.findByUsernameContains(userInput.getUsername());
    }

    @MutationMapping
    public UserModal registerUser(@Argument(name = "newUser") NewUser newUser) {
        var passwordCrypted = BCrypt.withDefaults().hash(12, newUser.getPassword().toCharArray());
        UserModal user = new UserModal(null, newUser.getUsername(), newUser.getEmail(), passwordCrypted);
        return userService.save(user);
    }
}
