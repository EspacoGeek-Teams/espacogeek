package com.espacogeek.geek.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.espacogeek.geek.models.UserModel;
import com.espacogeek.geek.services.UserService;
import com.espacogeek.geek.types.NewUser;
import com.espacogeek.geek.types.UserInput;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @QueryMapping
    public List<UserModel> findUser(Integer id, String username, String email) {
        return userService.findByIdOrUsernameContainsOrEmail(id, username, email);
    }
    
    @MutationMapping(name = "create")
    public String createUser(@Argument(name = "credentials") NewUser newUser) {
        var passwordCrypt = BCrypt.withDefaults().hash(12, newUser.password().toCharArray()); 
        var user = new UserModel(null, newUser.username(), newUser.email(), passwordCrypt, null);

        userService.save(user);

        return HttpStatus.CREATED.toString();
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

    @MutationMapping(name = "delete")
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
