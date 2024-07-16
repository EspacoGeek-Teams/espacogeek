package com.espacogeek.geek.data.impl;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.data.api.MediaApi;
import com.espacogeek.geek.exception.GenericException;
import com.espacogeek.geek.models.AlternativeTitleModel;
import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.GenreModel;
import com.espacogeek.geek.models.MediaCategoryModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.models.TypeReferenceModel;
import com.espacogeek.geek.services.AlternativeTitlesService;
import com.espacogeek.geek.services.ExternalReferenceService;
import com.espacogeek.geek.services.GenreService;
import com.espacogeek.geek.services.MediaCategoryService;
import com.espacogeek.geek.services.MediaService;
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
    @SuppressWarnings("unused")
    private void updateTvSeries() {
        try {
            var jsonArrayDailyExport = tvSeriesApi.updateTitles(); // * @AbigailGeovana pega a lista de todos os titulos de serie
            var medias = new ArrayList<MediaModel>();
            var externalReferences = new ArrayList<ExternalReferenceModel>();

            for (int i = 0; i < jsonArrayDailyExport.size(); i++) {
                var json = (JSONObject) jsonArrayDailyExport.get(i);

                // TODO It will return a too many request error if execute more than twenty time a second, fixed it
                // * @AbigailGeovana só vai entrar nesse if se a media não for anime
                if (tvSeriesApi.getKeyword(Integer.valueOf(json.get("id").toString())).stream().anyMatch((keyword) -> {
                    return keyword.getName().toLowerCase() == "anime" ? false : true;
                })) {
                    var externalReferenceList = new ArrayList<ExternalReferenceModel>();

                    var media = new MediaModel(TMDB_ID, json.get("original_name").toString(), SERIE_ID, IMDB_ID, null, null, null, this.mediaCategory, null, null, null, null, null, null, null);    
                    var externalReference = new ExternalReferenceModel(IMDB_ID, json.get("id").toString(), null, typeReference);

                    var externalReferenceExisted = externalReferenceService.findByReferenceAndType(externalReference.getReference(), typeReference);
                    if (externalReferenceExisted.isPresent()) {
                        media.setId(externalReferenceExisted.get().getMedia().getId()); 
                        externalReference.setId(externalReferenceExisted.get().getId());
                    }
    
                    externalReferenceList.add(externalReference);
                    media.setExternalReference(externalReferenceList);
                    externalReference.setMedia(media);
                    media.setAlternativeTitles(updateAlternativeTitles(media, null));
    
                    externalReferences.add(externalReference);
                    medias.add(media);
                }
            }

            mediaService.saveAll(medias);
            externalReferenceService.saveAll(externalReferences);

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
            if (media.getAlternativeTitles().isEmpty()) {
                alternativeTitles.add(new AlternativeTitleModel(null, title.getName(), media));
            } else {
                if (!media.getAlternativeTitles().stream().anyMatch((alternativeTitle) -> alternativeTitle.getName().equals(title.getName()))) {
                    alternativeTitles.add(new AlternativeTitleModel(null, title.getName(), media));
                }
            }
        }

        var newTitles = media.getAlternativeTitles();
        newTitles.addAll(alternativeTitleService.saveAll(alternativeTitles));

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
            reference.getMedia().getExternalReference().forEach((external) -> {
                if (!external.equals(reference)) {
                    externalReferences.add(reference);
                }
            });
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
        List<MediaModel> mediaList = new ArrayList<>();
        mediaList.add(media);
        
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
                rawGenre.setMedias(mediaList);
                genres.add(rawGenre);                
            }
        });

        var newGenres = genreService.saveAll(genres);
        newGenres.addAll(media.getGenre());

        return newGenres;
    }

    /**
     * @see MediaDataController#getAllInformation(MediaModel)
     */
    @Override
    public MediaModel updateAllInformation(MediaModel media, MediaModel result) {
        if (result == null) {
            var idSerie = media.getExternalReference().stream()
                .filter(
                    (externalReference) -> externalReference.getTypeReference().getId().equals(this.typeReference.getId()))
                .findFirst().get().getReference();
            result = tvSeriesApi.getDetails(Integer.valueOf(idSerie));
        }

        if(result.getId() == null) {
            return media;
        }

        updateAlternativeTitles(media, result);
        updateExternalReferences(media, result);
        updateArtworks(media, result);
        updateGenres(media, result);
        
        media.setUpdateAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        media = mediaService.save(media);

        result = new MediaModel();
        return media;
    }
}
