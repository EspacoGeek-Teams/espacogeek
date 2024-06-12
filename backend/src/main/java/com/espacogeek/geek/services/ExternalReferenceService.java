package com.espacogeek.geek.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;

import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.MediaModel;

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
}
