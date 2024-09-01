package com.espacogeek.geek.services;

import java.util.List;
import java.util.Optional;

import com.espacogeek.geek.models.MediaModel;

/**
 * Interface for the MediaService, which provides methods for managing MediaModel objects.
 */
public interface MediaService {
    /**
     * Finds Series (MediaModel) objects by their ID or name.
     *
     * @param id   The ID of the Series (MediaModel) object to find.
     * @param name The name of the Series (MediaModel) object to find.
     * @return A list of Series (MediaModel) objects that match the provided ID or name.
     */
    List<MediaModel> findSerieByIdOrName(Integer id, String name);

    /**
     * Finds Game (MediaModel) objects by their ID or name.
     *
     * @param id   The ID of the Game (MediaModel) object to find.
     * @param name The name of the Game (MediaModel) object to find.
     * @return A list of Game (MediaModel) objects that match the provided ID or name.
     */
    List<MediaModel> findGameByIdOrName(Integer id, String name);

    /**
     * Saves a MediaModel object to the database.
     *
     * @param media The MediaModel object to save.
     * @return The saved MediaModel object.
     */
    MediaModel save(MediaModel media);

    /**
     * Saves a list of MediaModel objects to the database.
     *
     * @param medias The list of MediaModel objects to save.
     * @return The list of saved MediaModel objects.
     */
    List<MediaModel> saveAll(List<MediaModel> medias);

    /**
     * Find any media by ID (PK) provided.
     * @param idMedia the ID (PK) of the media.
     * @return return a Optional Media.
     */
    Optional<MediaModel> findById(Integer idMedia);
}
