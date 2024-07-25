package com.espacogeek.geek.services;

import java.util.List;

import com.espacogeek.geek.models.MediaCategoryModel;
import com.espacogeek.geek.models.MediaModel;

/**
 * Interface for the MediaService, which provides methods for managing MediaModel objects.
 */
public interface MediaService {
    /**
     * Finds MediaModel objects by their ID or name.
     * 
     * @param id   The ID of the MediaModel object to find.
     * @param name The name of the MediaModel object to find.
     * @return A list of MediaModel objects that match the provided ID or name.
     */
    List<MediaModel> findSerieByIdOrName(Integer id, String name);

    /**
     * Finds MediaModel objects by their ID or name.
     * <p>
     * This query use <code>JOIN FETCH</code> for all proprieties of media, so <b>avoid at the most use this query</b>. For the most case use {@link #findSerieByIdOrName(Integer, String)}.
     * 
     * @param id   The ID of the MediaModel object to find.
     * @param name The name of the MediaModel object to find.
     * @return A list of MediaModel objects that match the provided ID or name.
     */
    List<MediaModel> findSerieJoinFetchedByIdOrName(Integer id, String name);

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
}
