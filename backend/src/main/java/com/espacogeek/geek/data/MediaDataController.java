package com.espacogeek.geek.data;

import java.util.List;

import com.espacogeek.geek.exception.GenericException;
import com.espacogeek.geek.models.AlternativeTitleModel;
import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.MediaModel;

public interface MediaDataController {
    // External references
    public static final Integer TMDB_ID = 1;
    public static final Integer TVDB_ID = 2;
    public static final Integer IMDB_ID = 3;

    // Media Type references 
    public static final Integer SERIE_ID = 1;

    /**
     * This method get the media and set cover and banner. It also persist to Database.
     * 
     * @param media media that doesn't have cover or banner
     * @throws GenericException when ExternalReference couldn't be found
     * @return Media object with cover and banner
     */
    public MediaModel handleArtworks(MediaModel media);

    public MediaModel getAllInformation(MediaModel media);

    public List<AlternativeTitleModel> handleAlternativeTitles(MediaModel media, MediaModel results);

    public List<ExternalReferenceModel> handleExternalReferences(MediaModel media, MediaModel results);
}
