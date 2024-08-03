package com.espacogeek.geek.controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import com.espacogeek.geek.exception.GenericException;
import com.espacogeek.geek.models.UserModel;
import com.espacogeek.geek.services.UserService;
import com.espacogeek.geek.types.NewUser;
import com.espacogeek.geek.types.UserInput;
import com.espacogeek.geek.utils.GetIdInUserDetailsAuthority;
import com.espacogeek.geek.utils.PasswordValidator;

import at.favre.lib.crypto.bcrypt.BCrypt;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @QueryMapping
    public List<UserModel> findUser(@Argument Integer id, @Argument String username, @Argument String email) {
        return userService.findByIdOrUsernameContainsOrEmail(id, username, email);
    }
    
    @MutationMapping(name = "createUser")
    public String createUser(@Argument(name = "credentials") NewUser newUser) {

        if (!new PasswordValidator(newUser.password()).check()) {
            throw new GenericException(HttpStatus.BAD_GATEWAY.toString());
        }

        var passwordCrypt = BCrypt.withDefaults().hash(12, newUser.password().toCharArray()); 
        var user = new UserModel(null, newUser.username(), newUser.email().toLowerCase(), passwordCrypt, null);

        userService.save(user);

        return HttpStatus.CREATED.toString();
    }

    @MutationMapping(name = "editPassword")
    @PreAuthorize("hasRole('user')")
    public String editPasswordUserLogged(Authentication authentication, @Argument String actualPassword, @Argument String newPassword) {

        if (!new PasswordValidator(newPassword).check()) {
            throw new GenericException(HttpStatus.BAD_GATEWAY.toString());
        }

        var userId = new GetIdInUserDetailsAuthority(authentication).getUserID();

        var userLogged = userService.findById(Integer.valueOf(userId)).get();
        var resultPassword = BCrypt.verifyer().verify(actualPassword.toCharArray(), userLogged.getPassword()).verified;

        if (resultPassword) {
            userLogged.setPassword(BCrypt.withDefaults().hash(12, newPassword.toCharArray()));
            userService.save(userLogged);
            return HttpStatus.OK.toString();
        }
        
        return HttpStatus.UNAUTHORIZED.toString();
    }

    @MutationMapping(name = "deleteUser")
    @PreAuthorize("hasRole('user')")
    public String deleteUserLogged(Authentication authentication, @Argument String password) {

        var userId = new GetIdInUserDetailsAuthority(authentication).getUserID();

        var userLogged = userService.findById(userId).get(); 
        var resultPassword = BCrypt.verifyer().verify(password.toCharArray(), userLogged.getPassword()).verified;
        
        if (resultPassword) {
            userService.deleteById(Integer.valueOf(userId));
            return HttpStatus.OK.toString();
        }
        return HttpStatus.UNAUTHORIZED.toString();
    }

    // TODO EDIT USER USERNAME
    @MutationMapping(name = "editUsername")
    @PreAuthorize("hasRole('user')")
    public String editUsernameUserLogged(Authentication authentication, @Argument String password, @Argument String NewUsername) {
        
        var userId = new GetIdInUserDetailsAuthority(authentication).getUserID();

        var userLogged = userService.findById(userId).get(); 
        var resultPassword = BCrypt.verifyer().verify(password.toCharArray(), userLogged.getPassword()).verified;
        
        if (resultPassword) {
            userLogged.setUsername(NewUsername);
            userService.save(userLogged);
            return HttpStatus.OK.toString();
        }

        return HttpStatus.UNAUTHORIZED.toString();
    }

    // TODO EDIT USER EMAIL
    @MutationMapping(name = "editEmail")
    @PreAuthorize("hasRole('user')")
    public String editEmailUserLogged(Authentication authentication, @Argument String password, @Argument String NewEmail) {

        var userId = new GetIdInUserDetailsAuthority(authentication).getUserID();

        var userLogged = userService.findById(userId).get(); 
        var resultPassword = BCrypt.verifyer().verify(password.toCharArray(), userLogged.getPassword()).verified;
        
        if (resultPassword) {
            userLogged.setEmail(NewEmail);
            userService.save(userLogged);
            return HttpStatus.OK.toString();
        }

        return HttpStatus.UNAUTHORIZED.toString();
    }
}
