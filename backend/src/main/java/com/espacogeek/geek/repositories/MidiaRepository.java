package com.espacogeek.geek.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.espacogeek.geek.models.MidiaModel;

@Repository
public interface MidiaRepository extends JpaRepository<MidiaModel, Integer> {
    List<Optional<MidiaModel>> findMidiaByIdOrName(Integer id, String name);
}
