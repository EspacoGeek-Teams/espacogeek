package com.espacogeek.geek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.espacogeek.geek.modals.GenreModal;

public interface GenreRepository extends JpaRepository<GenreModal, Integer> {

}
