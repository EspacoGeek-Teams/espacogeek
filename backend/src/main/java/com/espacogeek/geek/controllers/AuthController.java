package com.espacogeek.geek.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.espacogeek.geek.modals.UserModal;
import com.espacogeek.geek.services.User.UserService;
import com.espacogeek.geek.types.NewUser;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/")
    public UserModal addUser(@RequestBody NewUser newUser) {
        var passwordCrypted = BCrypt.withDefaults().hash(12, newUser.password().toCharArray());
        UserModal user = new UserModal(null, newUser.username(), newUser.email(), passwordCrypted);
        return userService.save(user);
    }
}
