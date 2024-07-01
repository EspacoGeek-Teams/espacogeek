package com.espacogeek.geek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.espacogeek.geek.models.AlternativeTitleModel;

@Repository
public interface AlternativeTitlesRepository extends JpaRepository <AlternativeTitleModel, Integer> {
    
}