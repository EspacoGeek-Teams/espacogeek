package com.espacogeek.geek.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.espacogeek.geek.modals.UserModal;

public interface UserRepository extends JpaRepository<UserModal, Integer>{
    List<UserModal> findByUsername(UserModal id);
}
