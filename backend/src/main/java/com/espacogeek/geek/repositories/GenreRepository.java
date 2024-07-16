package com.espacogeek.geek.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.espacogeek.geek.models.GenreModel;

@Repository
public interface GenreRepository extends JpaRepository<GenreModel, Integer> {
    public List<GenreModel> findAllByNames(List<String> names);
}
