package com.espacogeek.geek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.espacogeek.geek.models.GenreModel;

public interface GenreRepository extends JpaRepository<GenreModel, Integer> {

}
