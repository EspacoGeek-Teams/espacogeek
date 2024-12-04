package com.espacogeek.geek.services;

import java.util.Optional;

import com.espacogeek.geek.models.TypeReferenceModel;

/**
 * Interface for the TypeReferenceService, which provides methods for retrieving TypeReferenceModel objects.
 */
public interface TypeReferenceService {
    /**
     * Retrieves a TypeReferenceModel object by its ID.
     * 
     * @param id The ID of the TypeReferenceModel object to retrieve.
     * @return An Optional containing the TypeReferenceModel object if found, or an empty Optional if not found.
     */
    Optional<TypeReferenceModel> findById(Integer id);
}
