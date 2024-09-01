package com.espacogeek.geek.services;

import java.util.List;
import java.util.Optional;

import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.models.TypeReferenceModel;

/**
 * Interface for the ExternalReferenceService, which provides methods for retrieving ExternalReferenceModel objects.
 */
public interface ExternalReferenceService {

    /**
     * Retrieves an ExternalReferenceModel object by its ID.
     *
     * @param id The ID of the ExternalReferenceModel object to retrieve.
     * @return An Optional containing the ExternalReferenceModel object if found, or an empty Optional if not found.
     */
    Optional<ExternalReferenceModel> findById(Integer id);

    /**
     * Retrieves a list of ExternalReferenceModel objects that match the provided MediaModel.
     *
     * @param media The MediaModel to use for searching.
     * @return A list of ExternalReferenceModel objects that match the provided MediaModel.
     */
    List<ExternalReferenceModel> findAll(MediaModel media);

    /**
     * Save external reference provided.
     *
     * @param externalReference The ExternalReference to save.
     * @return ExternalReferenceModel object saved.
     */
    ExternalReferenceModel save(ExternalReferenceModel externalReference);

    /**
     * Save all external reference provided.
     *
     * @param externalReferences The ExternalReferences to save.
     * @return List of ExternalReferenceModel object saved.
     */
    List<ExternalReferenceModel> saveAll(List<ExternalReferenceModel> externalReferences);

    /**
     * Find by reference and Type Reference
     *
     * @param reference value of ExternalReference.
     * @param typeReference the Type Reference to find.
     * @return ExternalReferenceModel list of <code>ExternalReferenceModel</code> found.
     */
    List<ExternalReferenceModel> findByReferenceAndType(String reference, TypeReferenceModel typeReference);
}
