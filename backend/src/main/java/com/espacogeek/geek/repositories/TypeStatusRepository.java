package com.espacogeek.geek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.espacogeek.geek.modals.TypeStatusModal;

public interface TypeStatusRepository extends JpaRepository<TypeStatusModal, Integer> {
    
}
