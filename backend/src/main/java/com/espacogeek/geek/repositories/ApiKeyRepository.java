package com.espacogeek.geek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.espacogeek.geek.models.ApiKeyModel;

@Repository
public interface ApiKeyRepository extends JpaRepository<ApiKeyModel, Integer> {
}
