package com.espacogeek.geek.data;

import java.util.List;

import com.espacogeek.geek.data.api.MediaApi;
import com.espacogeek.geek.models.AlternativeTitleModel;
import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.GenreModel;
import com.espacogeek.geek.models.MediaCategoryModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.models.SeasonModel;
import com.espacogeek.geek.models.TypeReferenceModel;
import com.espacogeek.geek.services.ExternalReferenceService;
import com.espacogeek.geek.services.MediaService;
import com.espacogeek.geek.services.TypeReferenceService;

public interface MediaDataController {
    // External references
    public static final Integer TMDB_ID = 1;
    public static final Integer TVDB_ID = 2;
    public static final Integer IMDB_ID = 3;
    public static final Integer IGDB_ID = 4;

    // Media Type references
    public static final Integer SERIE_ID = 1;
    public static final Integer GAME_ID = 2;

    /**
     * This method update all information from provide <code>MediaModel</code>.
     * <p>
     * @param media this <code>MediaModel</code> object has to have <code>mediaCategory</code> object.
     * @param result this <code>MediaModel</code> object has to have <code>externalReference</code> object.
     * @param typeReference reference source of information to the Media.
     * @param mediaApi implementation of MediaAPI.
     * <p>
     * @return <code>MediaModel</code> object with updated information about the provide Media.
     */
    default public MediaModel updateAllInformation(MediaModel media, MediaModel result, TypeReferenceModel typeReference, MediaApi mediaApi) {
        throw new UnsupportedOperationException();
    }

    default public MediaModel updateAllInformation(MediaModel media, MediaModel result) {
        throw new UnsupportedOperationException();
    }

    default public MediaModel updateArtworks(MediaModel media, MediaModel result, TypeReferenceModel typeReference, MediaApi mediaApi) {
        throw new UnsupportedOperationException();
    }

    default public List<AlternativeTitleModel> updateAlternativeTitles(MediaModel media, MediaModel result, TypeReferenceModel typeReference, MediaApi mediaApi) {
        throw new UnsupportedOperationException();
    }

    default public List<ExternalReferenceModel> updateExternalReferences(MediaModel media, MediaModel result, TypeReferenceModel typeReference, MediaApi mediaApi) {
        throw new UnsupportedOperationException();
    }

    default public List<GenreModel> updateGenres(MediaModel media, MediaModel result, TypeReferenceModel typeReference, MediaApi mediaApi) {
        throw new UnsupportedOperationException();
    }

    default public List<SeasonModel> updateSeason(MediaModel media, MediaModel result, TypeReferenceModel typeReference, MediaApi mediaApi) {
        throw new UnsupportedOperationException();
    }

    default public List<MediaModel> searchMedia(String search, MediaApi mediaApi, TypeReferenceModel typeReference, MediaCategoryModel mediaCategory) {
        throw new UnsupportedOperationException();
    }

    default public List<MediaModel> updateBasicAttributes(MediaModel media, MediaModel result, TypeReferenceModel typeReference, MediaApi mediaApi) {
        throw new UnsupportedOperationException();
    }

    default public MediaModel createMediaIfNotExist(MediaModel media, MediaService mediaService, ExternalReferenceService externalReferenceService, TypeReferenceModel typeReference) {
        MediaModel mediaResult = new MediaModel();

        for (ExternalReferenceModel ereference : media.getExternalReference()) {
            var external = externalReferenceService.findByReferenceAndType(ereference.getReference(), typeReference);
            if (external == null || external.isEmpty()) {
                return mediaService.save(media);
            }
        }

        return media;
    }
}
