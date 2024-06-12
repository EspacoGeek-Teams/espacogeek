package com.espacogeek.geek.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.espacogeek.geek.models.ExternalReferenceModel;

@Repository
public interface ExternalReferenceRepository<T> extends JpaRepository<ExternalReferenceModel, Integer> {

}
