package com.espacogeek.geek.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.espacogeek.geek.repositories.UserRepository;

public class UserController {
    @Autowired
    UserRepository userRepository;
}
