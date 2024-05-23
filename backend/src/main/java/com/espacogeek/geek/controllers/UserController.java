package com.espacogeek.geek.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.espacogeek.geek.modals.UserModal;
import com.espacogeek.geek.repositories.UserRepository;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @QueryMapping
    public Optional<UserModal> userByUsername(@Argument String username) {
        return userRepository.findByUsernameContains(username);
    }
}
