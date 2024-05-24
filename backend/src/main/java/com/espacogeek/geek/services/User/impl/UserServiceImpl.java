package com.espacogeek.geek.services.User.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.espacogeek.geek.exception.GenericExeption;
import com.espacogeek.geek.modals.UserModal;
import com.espacogeek.geek.repositories.UserRepository;
import com.espacogeek.geek.services.User.UserService;

import jakarta.validation.ConstraintViolationException;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    /**
     * @see UserService#findByEmail(String)
     */
    @Override
    public Optional<UserModal> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email).orElseThrow(() -> new GenericExeption(HttpStatus.NOT_FOUND+": Email not found")));
    }

    /**
     * @see UserService#findById(Integer)
     */
    @Override
    public Optional<UserModal> findById(Integer id) {
        return Optional.ofNullable(userRepository.findById(id).orElseThrow(() -> new GenericExeption(HttpStatus.NOT_FOUND+": Id not found")));
    }

    /**
     * @see UserService#findByUsernameContains(String)
     */
    @Override
    public Optional<UserModal> findByUsernameContains(String username) throws GenericExeption {
        return Optional.ofNullable(userRepository.findByUsernameContains(username).orElseThrow(() -> new GenericExeption(HttpStatus.NOT_FOUND+": Username not found")));
    }

    /**
     * @see UserService#save(UserModal)
     */
    @Override
    public UserModal save(UserModal user) {
        try {
            return userRepository.save(user);
        } catch (ConstraintViolationException e) {
            throw new GenericExeption(HttpStatus.BAD_REQUEST+": Data too long.");
        }
    }
}
