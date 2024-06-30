package com.espacogeek.geek.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.espacogeek.geek.models.UserModel;
import com.espacogeek.geek.services.UserService;

import com.espacogeek.geek.models.UserModel;
import com.espacogeek.geek.services.UserService;

import com.espacogeek.geek.types.NewUser;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    // Use GET to get api-key jwt

    @PostMapping("/")
    public UserModel addUser(@RequestBody NewUser newUser) {
        var passwordCrypted = BCrypt.withDefaults().hash(12, newUser.password().toCharArray()); // * @AbigailGeovana criptografa a senha
        UserModel user = new UserModel(null, newUser.username(), newUser.email(), passwordCrypted, null);

        return userService.save(user);
    }
}
