package com.espacogeek.geek.services;

import java.util.Optional;

import com.espacogeek.geek.models.MediaCategoryModel;

/**
 * Interface for the MediaCategoryService, which provides methods for managing MediaCategoryModel objects.
 */
public interface MediaCategoryService {
    /**
     * Retrieves a MediaCategoryModel object by its ID.
     * 
     * @param id The ID of the MediaCategoryModel object to retrieve.
     * @return An Optional containing the MediaCategoryModel object if found, or an empty Optional if not found.
     */
    Optional<MediaCategoryModel> findById(Integer id);
}
