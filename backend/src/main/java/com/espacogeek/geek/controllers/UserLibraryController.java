package com.espacogeek.geek.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.espacogeek.geek.repositories.UserLibraryRepository;

public class UserLibraryController {
    
    @Autowired
    private UserLibraryRepository userLibraryRepository;
}