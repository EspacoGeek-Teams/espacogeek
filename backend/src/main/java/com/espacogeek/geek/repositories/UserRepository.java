package com.espacogeek.geek.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.espacogeek.geek.modals.UserModal;

@Repository
public interface UserRepository extends JpaRepository<UserModal, Integer>{
    Optional<UserModal> findByIdOrUsernameContainsOrEmail(Integer id, String username, String email);
}
