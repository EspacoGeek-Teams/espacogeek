package com.espacogeek.geek.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.espacogeek.geek.models.UserModel;
import com.espacogeek.geek.services.UserService;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @QueryMapping
    public List<UserModel> findUser(@Argument Integer id, @Argument String username, @Argument String email) {
        return userService.findByIdOrUsernameContainsOrEmail(id, username, email);
    }

    @QueryMapping(name = "login")
    @PreAuthorize("hasRole('user')")
    public String doLoginUser() {
        return HttpStatus.ACCEPTED.toString();
    }
}
