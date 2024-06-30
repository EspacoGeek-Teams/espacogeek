package com.espacogeek.geek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.espacogeek.geek.modals.UserLibraryModal;

public interface UserLibraryRepository extends JpaRepository<UserLibraryModal, Integer> {
    
}
