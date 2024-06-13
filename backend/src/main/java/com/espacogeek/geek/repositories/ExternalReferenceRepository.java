package com.espacogeek.geek.repositories;

import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.TypeReferenceModel;

@Repository
public interface ExternalReferenceRepository<T> extends JpaRepository<ExternalReferenceModel, Integer> {
    Optional<ExternalReferenceModel> findByReferenceAndTypeReferenceModel (String reference, TypeReferenceModel typeReferenceModel);
    
    // Optional<ExternalReferenceModel> findByReference (String reference);
}
