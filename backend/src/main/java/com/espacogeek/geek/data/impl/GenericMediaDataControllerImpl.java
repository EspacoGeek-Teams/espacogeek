package com.espacogeek.geek.data.impl;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.data.api.MediaApi;
import com.espacogeek.geek.models.AlternativeTitleModel;
import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.GenreModel;
import com.espacogeek.geek.models.MediaCategoryModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.models.SeasonModel;
import com.espacogeek.geek.models.TypeReferenceModel;
import com.espacogeek.geek.services.AlternativeTitlesService;
import com.espacogeek.geek.services.ExternalReferenceService;
import com.espacogeek.geek.services.GenreService;
import com.espacogeek.geek.services.MediaService;
import com.espacogeek.geek.services.SeasonService;

@Component("genericMediaDataController")
public abstract class GenericMediaDataControllerImpl implements MediaDataController {

    @Autowired
    protected MediaService mediaService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private AlternativeTitlesService alternativeTitlesService;
    @Autowired
    private ExternalReferenceService externalReferenceService;
    @Autowired
    private SeasonService seasonService;

    /**
     * @see MediaDataController#updateAllInformation(MediaModel, MediaModel, TypeReferenceModel, MediaApi)
     */
    @Override
    public MediaModel updateAllInformation(MediaModel media, MediaModel result, TypeReferenceModel typeReference, MediaApi mediaApi) {
        if (result == null) {
            var id = media.getExternalReference().stream()
                    .filter(
                            (externalReference) -> externalReference.getTypeReference().getId()
                                    .equals(typeReference.getId()))
                    .findFirst().get().getReference();
            result = mediaApi.getDetails(Integer.valueOf(id));
        }
        if (result == null) {
            return media;
        }

        try {
            createMediaIfNotExistAndIfExistReturnIt(media, mediaService, externalReferenceService, typeReference);
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateAlternativeTitles(media, result, typeReference, mediaApi);
        updateExternalReferences(media, result, typeReference, mediaApi);
        updateArtworks(media, result, typeReference, mediaApi);
        updateGenres(media, result, typeReference, mediaApi);
        updateSeason(media, result, typeReference, mediaApi);

        media.setUpdateAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        media = mediaService.save(media);

        result = new MediaModel();
        return media;
    }

    @Override
    public MediaModel updateArtworks(MediaModel media, MediaModel result, TypeReferenceModel typeReference, MediaApi mediaApi) {
        MediaModel rawArtwork = new MediaModel();

        if (result == null) {
            var externalReferences = media.getExternalReference();
            for (ExternalReferenceModel externalReference : externalReferences) {
                if (externalReference.getTypeReference().getId().equals(typeReference.getId())) {
                    rawArtwork = mediaApi.getArtwork(Integer.valueOf(externalReference.getReference()));
                }
            }
        } else {
            rawArtwork = result;
        }

        media.setCover(rawArtwork.getCover());
        media.setBanner(rawArtwork.getBanner());

        mediaService.save(media);

        return media;
    }

    @Override
    public List<AlternativeTitleModel> updateAlternativeTitles(MediaModel media, MediaModel result, TypeReferenceModel typeReference, MediaApi mediaApi) {
        List<AlternativeTitleModel> allAlternativeTitles = new ArrayList<>();

        if (result == null) {
            for (ExternalReferenceModel reference : media.getExternalReference()) {
                if (reference.getTypeReference().equals(typeReference)) {
                    try {
                        allAlternativeTitles = mediaApi.getAlternativeTitles(Integer.valueOf(reference.getReference()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            allAlternativeTitles = result.getAlternativeTitles();
        }

        var alternativeTitles = new ArrayList<AlternativeTitleModel>();

        for (AlternativeTitleModel title : allAlternativeTitles) {
            if (media.getAlternativeTitles() == null) {
                alternativeTitles.add(new AlternativeTitleModel(null, title.getName(), media));
            } else {
                if (media.getAlternativeTitles().stream().noneMatch((alternativeTitle) -> alternativeTitle.getName().equals(title.getName()))) {
                    alternativeTitles.add(new AlternativeTitleModel(null, title.getName(), media));
                }
            }
        }

        var newTitles = media.getAlternativeTitles() == null ? new ArrayList<AlternativeTitleModel>() : media.getAlternativeTitles();
        var savedTitles = alternativeTitlesService.saveAll(alternativeTitles);
        newTitles.addAll(savedTitles == null || savedTitles.isEmpty() ? new ArrayList<>() : savedTitles);

        return newTitles;
    }

    @Override
    public List<ExternalReferenceModel> updateExternalReferences(MediaModel media, MediaModel result, TypeReferenceModel typeReference, MediaApi mediaApi) {
        List<ExternalReferenceModel> rawExternalReferences = new ArrayList<ExternalReferenceModel>();

        if (result == null) {
            for (ExternalReferenceModel reference : media.getExternalReference()) {
                if (reference.getTypeReference().equals(typeReference)) {
                    try {
                        rawExternalReferences = mediaApi.getExternalReference(Integer.valueOf(reference.getReference()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            rawExternalReferences = result.getExternalReference();
        }

        for (ExternalReferenceModel reference : rawExternalReferences) {
            if (CollectionUtils.isEmpty(media.getExternalReference()) || media.getExternalReference().stream().noneMatch((eReference) -> eReference.equals(reference))) {
                reference.setMedia(media);
                if (media.getExternalReference() == null) media.setExternalReference(new ArrayList<>());
                media.getExternalReference().add(reference);
            }
        }

        return externalReferenceService.saveAll(media.getExternalReference());
    }

    @Override
    public List<GenreModel> updateGenres(MediaModel media, MediaModel result, TypeReferenceModel typeReference, MediaApi mediaApi) {
        List<GenreModel> genres = new ArrayList<>();
        List<GenreModel> rawGenres = new ArrayList<>();
        var medias = new ArrayList<MediaModel>();
        medias.add(media);

        if (result == null) {
            for (ExternalReferenceModel reference : media.getExternalReference()) {
                if (reference.getTypeReference().equals(typeReference)) {
                    try {
                        rawGenres = mediaApi.getGenre(Integer.valueOf(reference.getReference()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            rawGenres = result.getGenre();
        }

        if (CollectionUtils.isEmpty(rawGenres)) {
            return new ArrayList<>();
        }

        rawGenres.forEach((rawGenre) -> {
            if (!media.getGenre().stream().anyMatch((genre) -> genre.getName().equals(rawGenre.getName()))) {
                rawGenre.setMedias(medias);
                genres.add(rawGenre);
            }
        });

        var newGenres = new ArrayList<GenreModel>();
        if (!genres.isEmpty()) {
            media.setGenre(genres);
            genreService.saveAll(genres);
        }
        newGenres.addAll(media.getGenre() == null ? new ArrayList<>() : media.getGenre());

        return newGenres;
    }

    @Override
    public List<SeasonModel> updateSeason(MediaModel media, MediaModel result, TypeReferenceModel typeReference, MediaApi mediaApi) {
        List<SeasonModel> seasons = new ArrayList<>();
        List<SeasonModel> rawSeasons = new ArrayList<>();

        if (result == null) {
            for (ExternalReferenceModel reference : media.getExternalReference()) {
                if (reference.getTypeReference().equals(typeReference)) {
                    rawSeasons = mediaApi.getSeason(Integer.valueOf(reference.getReference()));
                }
            }
        } else {
            rawSeasons = result.getSeason();
        }

        if (CollectionUtils.isEmpty(rawSeasons)) {
            return new ArrayList<>();
        }

        rawSeasons.forEach((rawSeason) -> {
            if (!media.getSeason().stream().anyMatch((season) -> season.getName().equals(rawSeason.getName()))) {
                seasons.add(new SeasonModel(null, rawSeason.getName(), rawSeason.getAirDate(), null, rawSeason.getAbout(), rawSeason.getCover(), rawSeason.getSeasonNumber(), rawSeason.getEpisodeCount(), media));
            }
        });

        var savedSeasons = seasonService.saveAll(seasons);
        List<SeasonModel> newSeasons = media.getSeason() == null ? new ArrayList<>() : media.getSeason();
        newSeasons.addAll(savedSeasons == null ? new ArrayList<>() : savedSeasons);

        return newSeasons;
    }

    @Override
    public List<MediaModel> searchMedia(String search, MediaApi mediaApi, TypeReferenceModel typeReference, MediaCategoryModel mediaCategory) {
        var rawMediaSearchList = mediaApi.doSearch(search);
        var result = new ArrayList<MediaModel>();

        for (MediaModel mediaSearch : rawMediaSearchList) {
            var media = new MediaModel();
            media.setMediaCategory(mediaCategory);
            
            try {
                media = createMediaIfNotExistAndIfExistReturnIt(mediaSearch, mediaService, externalReferenceService, typeReference);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (media != null) {
                updateBasicAttributes(media, mediaSearch, typeReference, mediaApi);
                updateArtworks(media, mediaSearch, typeReference, mediaApi);
                updateExternalReferences(media, mediaSearch, typeReference, mediaApi);
                updateAlternativeTitles(media, mediaSearch, typeReference, mediaApi);
            }
        }

        return result;
    }

    @Override
    public MediaModel updateBasicAttributes(MediaModel media, MediaModel result, TypeReferenceModel typeReference, MediaApi mediaApi) {
        MediaModel rawMedia = new MediaModel();

        if (result == null) {
            for (ExternalReferenceModel reference : media.getExternalReference()) {
                if (reference.getTypeReference().equals(typeReference)) {
                    // TODO Get basic info
                }
            }
        } else {
            rawMedia = result;
        }

        MediaModel mediaResult = new MediaModel();

        media.setAbout(rawMedia.getAbout());
        media.setMediaCategory(rawMedia.getMediaCategory());
        media.setName(rawMedia.getName());

        return mediaResult;
    }
}
