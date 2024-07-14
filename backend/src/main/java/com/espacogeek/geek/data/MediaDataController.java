package com.espacogeek.geek.data;

import java.util.List;

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

    public MediaModel updateAllInformation(MediaModel media, MediaModel result);

    public MediaModel updateArtworks(MediaModel media, MediaModel result);

    public List<AlternativeTitleModel> updateAlternativeTitles(MediaModel media, MediaModel result);

    public List<ExternalReferenceModel> updateExternalReferences(MediaModel media, MediaModel result);
}
