package com.espacogeek.geek.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.espacogeek.geek.modals.UserModal;

public interface UserRepository extends JpaRepository<UserModal, Integer>{
    Optional<UserModal> findByUsernameContains(String id);
}
