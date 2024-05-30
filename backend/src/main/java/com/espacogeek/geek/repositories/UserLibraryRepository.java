package com.espacogeek.geek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.espacogeek.geek.models.UserLibraryModel;

public interface UserLibraryRepository extends JpaRepository<UserLibraryModel, Integer> {
    
}
