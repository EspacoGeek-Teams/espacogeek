package com.espacogeek.geek.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.espacogeek.geek.modals.UserModal;
import com.espacogeek.geek.services.User.UserService;
import com.espacogeek.geek.types.UserInput;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @QueryMapping
    public Optional<UserModal> findUser(@Argument(name = "filter") UserInput userInput) {
        return userService.findByUsernameContains(userInput.getUsername());
    }
}
