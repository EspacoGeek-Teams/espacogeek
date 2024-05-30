package com.espacogeek.geek.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import com.espacogeek.geek.exception.GenericExeption;
import com.espacogeek.geek.modals.UserModal;
import com.espacogeek.geek.services.User.UserService;
import com.espacogeek.geek.types.UserInput;

import at.favre.lib.crypto.bcrypt.BCrypt;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @QueryMapping
    public Optional<UserModal> findUser(@Argument(name = "filter") UserInput userInput) {
        return userService.findByIdOrUsernameContainsOrEmail(userInput.getId(), userInput.getUsername(), userInput.getEmail());
    }

    @MutationMapping(name = "editPassword")
    public String editPasswordUser(@Argument String actualPassword, @Argument String newPassword, @ContextValue String userId) {
        var userLoged = userService.findById(Integer.valueOf(userId)).get();
        var resultPassword = BCrypt.verifyer().verify(actualPassword.toCharArray(), userLoged.getPassword()).verified;
        if (resultPassword) {
            userLoged.setPassword(BCrypt.withDefaults().hash(12, newPassword.toCharArray()));
            userService.save(userLoged);
            return HttpStatus.OK.toString();
        }
        return HttpStatus.UNAUTHORIZED.toString();
    }

    @MutationMapping()
    public String deleteUser(@Argument String password, @ContextValue String userId) {
        var userLoged = userService.findById(Integer.valueOf(userId)).get();
        var resultPassword = BCrypt.verifyer().verify(password.toCharArray(), userLoged.getPassword()).verified;
        if (resultPassword) {
            userService.deleteById(Integer.valueOf(userId));
            return HttpStatus.OK.toString();
        }
        return HttpStatus.UNAUTHORIZED.toString();
    }
}
