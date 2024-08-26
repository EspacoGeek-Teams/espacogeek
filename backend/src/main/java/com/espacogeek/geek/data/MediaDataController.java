package com.espacogeek.geek.data;

import java.util.List;

import com.espacogeek.geek.models.AlternativeTitleModel;
import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.GenreModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.models.SeasonModel;

public interface MediaDataController {
    // External references
    public static final Integer TMDB_ID = 1;
    public static final Integer TVDB_ID = 2;
    public static final Integer IMDB_ID = 3;
    public static final Integer IGDB_ID = 4;

    // Media Type references
    public static final Integer SERIE_ID = 1;

    default public MediaModel updateAllInformation(MediaModel media, MediaModel result) {
        throw new UnsupportedOperationException();
    }

    default public MediaModel updateArtworks(MediaModel media, MediaModel result) {
        throw new UnsupportedOperationException();
    }

    default public List<AlternativeTitleModel> updateAlternativeTitles(MediaModel media, MediaModel result) {
        throw new UnsupportedOperationException();
    }

    default public List<ExternalReferenceModel> updateExternalReferences(MediaModel media, MediaModel result) {
        throw new UnsupportedOperationException();
    }

    default public List<GenreModel> updateGenres(MediaModel media, MediaModel result) {
        throw new UnsupportedOperationException();
    }

    default public List<SeasonModel> updateSeason(MediaModel media, MediaModel result) {
        throw new UnsupportedOperationException();
    }
}
