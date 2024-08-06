package com.espacogeek.geek.data.impl;

import java.sql.SQLIntegrityConstraintViolationException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.data.api.MediaApi;
import com.espacogeek.geek.exception.GenericException;
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
import com.espacogeek.geek.services.MediaCategoryService;
import com.espacogeek.geek.services.MediaService;
import com.espacogeek.geek.services.SeasonService;
import com.espacogeek.geek.services.TypeReferenceService;

import jakarta.annotation.PostConstruct;

@Component("serieController")
public class SerieControllerImpl implements MediaDataController {
    @Autowired
    private MediaService mediaService;
    @Autowired
    private MediaApi tvSeriesApi;
    @Autowired
    private MediaCategoryService mediaCategoryService;
    @Autowired
    private TypeReferenceService typeReferenceService;
    @Autowired
    private ExternalReferenceService externalReferenceService;
    @Autowired
    private AlternativeTitlesService alternativeTitleService;
    @Autowired
    private GenreService genreService;
    @Autowired
    private SeasonService seasonService;
    private TypeReferenceModel typeReference;
    private MediaCategoryModel mediaCategory;

    @PostConstruct
    private void init() {
        this.typeReference = typeReferenceService.findById(TMDB_ID)
            .orElseThrow(() -> new GenericException("Type Reference not found"));
        this.mediaCategory = mediaCategoryService.findById(SERIE_ID)
            .orElseThrow(() -> new GenericException("Category not found"));
    }

    /**
     * This method update and add title of TV Series.
     * <p>
     * Every day at 9:00AM this function is executed.
     */
    // @Scheduled(cron = "* * 9 * * *")
    @Scheduled(initialDelay = 1)
    @SuppressWarnings("unused")
    private void updateTvSeries() {
        try {
            var jsonArrayDailyExport = tvSeriesApi.updateTitles();

            for (int i = 0; i < jsonArrayDailyExport.size(); i++) {
                var json = (JSONObject) jsonArrayDailyExport.get(i);

                var externalReferenceExisted = externalReferenceService.findByReferenceAndType(json.get("id").toString(), typeReference);

                if (!externalReferenceExisted.isPresent()) {
                    // TODO It can return a too many request error if execute more than twenty time a second, fixed it
                    if (tvSeriesApi.getKeyword(Integer.valueOf(json.get("id").toString())).stream().anyMatch((keyword) -> {
                        return keyword.getName().toLowerCase() == "anime" ? false : true;
                    })) {
                        var media = new MediaModel(null, json.get("original_name").toString(), null, null, null, null, null, this.mediaCategory, null, null, null, null, null, null, null, null);
                        var externalReference = new ExternalReferenceModel(null, json.get("id").toString(), null, typeReference);

                        media.setId(externalReferenceExisted.get().getMedia().getId());
                        externalReference.setId(externalReferenceExisted.get().getId());

                        var mediaSaved = mediaService.save(media);
                        externalReference.setMedia(mediaSaved);

                        var referenceSaved = externalReferenceService.save(externalReference);
                        List<ExternalReferenceModel> referenceListSaved = new ArrayList<>();
                        referenceListSaved.add(referenceSaved);
                        mediaSaved.setExternalReference(referenceListSaved);

                        media.setAlternativeTitles(updateAlternativeTitles(mediaSaved, null));
                    }
                }
            }

            System.out.println("SUCCESS TO UPDATE TV SERIES, AT " + LocalDateTime.now());

        } catch (Exception e) {
            System.out.println(MessageFormat.format("*# ------- FAILED TO UPDATE TV SERIES, AT {0} ------- *#", LocalDateTime.now()));
            e.printStackTrace();
            System.out.println("*# ----------------------------------------- *#");
        }
    }

    // public String updateCoverImage(MediaModel media, MediaModel result) {
    //     var externalReferences = media.getExternalReference();
    //     for (ExternalReferenceModel externalReference : externalReferences) {
    //         if (externalReference.getTypeReference().getId().equals(typeReference.getId())) {
    //             try {
    //                 var endpointImage = tvSeriesApi.getArtwork(Integer.valueOf(externalReference.getReference())).getPosters().getFirst().getFilePath();
    //                 var ext = endpointImage.split("\\.")[1];
    //                 var url = new URI(MediaApi.URL_IMAGE_TMDB + endpointImage).toURL();
    //                 var image = ImageIO.read(url);
    //                 var file = new File(
    //                         MessageFormat.format("imageDB/TVSerie/{0}/cover.{1}", media.getId().toString(), ext));
    //                 try {
    //                     ImageIO.write(image, ext, file);
    //                 } catch (FileNotFoundException | IIOException e) {
    //                     file.mkdirs();
    //                     ImageIO.write(image, ext, file);
    //                 }
    //             } catch (NumberFormatException | URISyntaxException | IOException e) {
    //                 e.printStackTrace();
    //             }
    //         }
    //     }
    //     return "";
    // }

    /**
     * @see MediaDataController#updateArtworks(MediaModel, MediaModel)
     */
    @Override
    public MediaModel updateArtworks(MediaModel media, MediaModel result) {
        MediaModel rawArtwork = new MediaModel();

        if (result == null) {
            var externalReferences = media.getExternalReference();
            for (ExternalReferenceModel externalReference : externalReferences) {
                if (externalReference.getTypeReference().getId().equals(this.typeReference.getId())) { // * @AbigailGeovana procura pela ID de referencia dele do tmdb
                    rawArtwork = tvSeriesApi.getArtwork(Integer.valueOf(externalReference.getReference()));
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

    /**
     * @see MediaDataController#updateAlternativeTitles(MediaModel, MediaModel)
     */
    @Override
    public List<AlternativeTitleModel> updateAlternativeTitles(MediaModel media, MediaModel result) {
        List<AlternativeTitleModel> allAlternativeTitles = new ArrayList<>();

        if (result == null) {
            for (ExternalReferenceModel reference : media.getExternalReference()) {
                if (reference.getTypeReference().equals(this.typeReference)) {
                    try {
                        allAlternativeTitles = tvSeriesApi.getAlternativeTitles(Integer.valueOf(reference.getReference()));
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
                if (!media.getAlternativeTitles().stream().anyMatch((alternativeTitle) -> alternativeTitle.getName().equals(title.getName()))) {
                    alternativeTitles.add(new AlternativeTitleModel(null, title.getName(), media));
                }
            }
        }

        var newTitles = media.getAlternativeTitles() == null ? new ArrayList<AlternativeTitleModel>() : media.getAlternativeTitles();
        var savedTitles = alternativeTitleService.saveAll(alternativeTitles);
        newTitles.addAll(savedTitles == null ? new ArrayList<>() : savedTitles);

        return newTitles;
    }

    /**
     * @see MediaDataController#updateExternalReferences(MediaModel, MediaModel)
     * @param result must have <code>typeReference</code>
     * @return <code>ExternalReferenceModel</code> object with the actual External References to given media
     */
    @Override
    public List<ExternalReferenceModel> updateExternalReferences(MediaModel media, MediaModel result) {
        List<ExternalReferenceModel> externalReferences = new ArrayList<ExternalReferenceModel>();
        List<ExternalReferenceModel> rawExternalReferences = new ArrayList<ExternalReferenceModel>();

        if (result == null) {
            for (ExternalReferenceModel reference : media.getExternalReference()) {
                if (reference.getTypeReference().equals(this.typeReference)) {
                    try {
                        rawExternalReferences = tvSeriesApi.getExternalReference(Integer.valueOf(reference.getReference()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            rawExternalReferences = result.getExternalReference();
        }

        for (ExternalReferenceModel reference : rawExternalReferences) {
            reference.setMedia(media);
            if (!media.getExternalReference().stream().anyMatch((eReference) -> eReference.getReference().equals(reference.getReference()))) {
                externalReferences.add(reference);
            }
        }

        var newExternal = externalReferenceService.saveAll(externalReferences);
        newExternal.addAll(media.getExternalReference());

        return newExternal;
    }


    /**
     * @see MediaDataController#updateGenres(MediaModel, MediaModel)
     */
    @Override
    public List<GenreModel> updateGenres(MediaModel media, MediaModel result) {
        List<GenreModel> genres = new ArrayList<>();
        List<GenreModel> rawGenres = new ArrayList<>();
        var medias = new ArrayList<MediaModel>();
        medias.add(media);

        if (result == null) {
            for (ExternalReferenceModel reference : media.getExternalReference()) {
                if (reference.getTypeReference().equals(this.typeReference)) {
                    try {
                        rawGenres = tvSeriesApi.getGenre(Integer.valueOf(reference.getReference()));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            rawGenres = result.getGenre();
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

    /**
     * @see MediaDataController#updateGenres(MediaModel, MediaModel)
     */
    @Override
    public MediaModel updateAllInformation(MediaModel media, MediaModel result) {
        if (result == null) {
            var idSerie = media.getExternalReference().stream()
                    .filter(
                            (externalReference) -> externalReference.getTypeReference().getId()
                                    .equals(this.typeReference.getId()))
                    .findFirst().get().getReference();
            result = tvSeriesApi.getDetails(Integer.valueOf(idSerie));
        }

        if (result == null) {
            return media;
        }

        updateAlternativeTitles(media, result);
        updateExternalReferences(media, result);
        updateArtworks(media, result);
        updateGenres(media, result);
        updateSeason(media, result);

        media.setUpdateAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        media = mediaService.save(media);

        result = new MediaModel();
        return media;
    }

    /**
     * @see MediaDataController#updateGenres(MediaModel, MediaModel)
     */
    @Override
    public List<SeasonModel> updateSeason(MediaModel media, MediaModel result) {
        List<SeasonModel> seasons = new ArrayList<>();
        List<SeasonModel> rawSeasons = new ArrayList<>();

        if (result == null) {
            for (ExternalReferenceModel reference : media.getExternalReference()) {
                if (reference.getTypeReference().equals(typeReference)) {
                    rawSeasons = tvSeriesApi.getSeason(Integer.valueOf(reference.getReference()));
                }
            }
        } else {
            rawSeasons = result.getSeason();
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
}
