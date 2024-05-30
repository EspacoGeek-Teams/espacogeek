package com.espacogeek.geek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.espacogeek.geek.models.ExternalReferenceModel;

public interface ExternalReferenceRepository extends JpaRepository<ExternalReferenceModel, Integer> {

}
