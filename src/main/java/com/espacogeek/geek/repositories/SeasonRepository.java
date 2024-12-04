package com.espacogeek.geek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.espacogeek.geek.models.SeasonModel;

@Repository
public interface SeasonRepository extends JpaRepository<SeasonModel, Integer> {

}
