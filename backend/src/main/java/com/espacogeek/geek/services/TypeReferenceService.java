package com.espacogeek.geek.services;

import java.util.Optional;

import com.espacogeek.geek.models.TypeReferenceModel;

public interface TypeReferenceService {
    Optional<TypeReferenceModel> findById (Integer id);
}
