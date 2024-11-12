package com.espacogeek.geek.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.espacogeek.geek.models.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer> {
    
    List<UserModel> findByIdOrUsernameContainsOrEmail(Integer id, String username, String email);

    Optional<UserModel> findUserByEmail(String username);
}
