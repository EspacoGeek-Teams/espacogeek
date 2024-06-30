package com.espacogeek.geek.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

<<<<<<< HEAD
<<<<<<< HEAD
import com.espacogeek.geek.models.UserModel;
import com.espacogeek.geek.services.UserService;
=======
=======
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
import com.espacogeek.geek.modals.UserModal;
import com.espacogeek.geek.services.User.UserService;
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
import com.espacogeek.geek.types.NewUser;

import at.favre.lib.crypto.bcrypt.BCrypt;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    // Use GET to get api-key jwt

    @PostMapping("/")
<<<<<<< HEAD
<<<<<<< HEAD
    public UserModel addUser(@RequestBody NewUser newUser) {
        var passwordCrypted = BCrypt.withDefaults().hash(12, newUser.password().toCharArray()); // * @AbigailGeovana criptografa a senha
        UserModel user = new UserModel(null, newUser.username(), newUser.email(), passwordCrypted, null);
=======
    public UserModal addUser(@RequestBody NewUser newUser) {
        var passwordCrypted = BCrypt.withDefaults().hash(12, newUser.password().toCharArray());
        UserModal user = new UserModal(null, newUser.username(), newUser.email(), passwordCrypted);
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
=======
    public UserModal addUser(@RequestBody NewUser newUser) {
        var passwordCrypted = BCrypt.withDefaults().hash(12, newUser.password().toCharArray());
        UserModal user = new UserModal(null, newUser.username(), newUser.email(), passwordCrypted);
>>>>>>> parent of a503368 (refractor and feat: fixed name modals to models and implementing midia)
        return userService.save(user);
    }
}
