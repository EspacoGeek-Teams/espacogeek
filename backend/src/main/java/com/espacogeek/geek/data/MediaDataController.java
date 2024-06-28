package com.espacogeek.geek.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import javax.imageio.IIOException;
import javax.imageio.ImageIO;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.espacogeek.geek.data.api.TvSeriesAPI;
import com.espacogeek.geek.exception.GenericException;
import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.MediaCategoryModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.models.TypeReferenceModel;
import com.espacogeek.geek.services.ExternalReferenceService;
import com.espacogeek.geek.services.MediaCategoryService;
import com.espacogeek.geek.services.MediaService;
import com.espacogeek.geek.services.TypeReferenceService;

import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import jakarta.annotation.PostConstruct;

@Component
public class MediaDataController {
    @Autowired
    private MediaService mediaService;
    
    @Autowired
    private TvSeriesAPI tvSeriesAPI;

    @Autowired
    private MediaCategoryService mediaCategoryService;

    @Autowired
    private TypeReferenceService typeReferenceService;

    @Autowired
    private ExternalReferenceService externalReferenceService;

    private TypeReferenceModel typeReference;

    private MediaCategoryModel mediaCategory;

    private static final Integer TMDB_ID = 1;

    private static final Integer TVDB_ID = 2;

    private static final Integer IMDB_ID = 3;

    private static final Integer SERIE_ID = 1;

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
    public void updateTvSeries() {
        try {
            var jsonArrayDailyExport = tvSeriesAPI.updateTitles(); // * @AbigailGeovana pega a lista de todos os titulos de serie
            var medias = new ArrayList<MediaModel>();
            var externalReferences = new ArrayList<ExternalReferenceModel>();

            for (int i = 0; i < jsonArrayDailyExport.size(); i++) {
                var json = (JSONObject) jsonArrayDailyExport.get(i);

                // TODO It will return a too many request error if execute more than twenty time a second, fixed it
                // * @AbigailGeovana só vai entrar nesse if se a media não for anime
                if (tvSeriesAPI.getKeyword(Integer.valueOf(json.get("id").toString())).stream().anyMatch((keyword) -> {
                    return keyword.getName().toLowerCase() == "anime" ? false : true;
                })) {
                    var externalReferenceList = new ArrayList<ExternalReferenceModel>();
    
                    var media = new MediaModel(null, json.get("original_name").toString(), null, null, null, null, null, this.mediaCategory, null, null, null, null, null, null);
                    var externalReference = new ExternalReferenceModel(null, json.get("id").toString(), null, this.typeReference);
    
                    var externalReferenceExisted = externalReferenceService.findByReferenceAndType(externalReference.getReference(), typeReference);
                    if (externalReferenceExisted.isPresent()) {
                        media.setId(externalReferenceExisted.get().getMedia().getId()); // * @AbigailGeovana Se a media já existir ela vai ter uma exteralReference. Para não repetir adicionamos o mesmo id da media para não repetir, define o id aí em vez de criar um novo registro no banco vai só atualizar o já existente
                        externalReference.setId(externalReferenceExisted.get().getId()); // * @AbigailGeovana para não repetir já que já existe, define o id aí em vez de criar um novo registro no banco vai só atualizar o já existente
                    }
    
                    externalReferenceList.add(externalReference);
                    media.setExternalReference(externalReferenceList);
                    externalReference.setMedia(media);
    
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
    
    /**
     * Save the image on disk on path "imageDB/TVSerie/{0}/cover.{1}", {0} is the serie code and {1} is ext of the file.
     * @deprecated
     * 
     * @param media that needs a id
     * @return a String containing URL of the tv serie cover 
     */
    @Deprecated
    public String handleCoverImage(MediaModel media) {
        var externalReferences = media.getExternalReference();
        for (ExternalReferenceModel externalReference : externalReferences) {
            if (externalReference.getTypeReference().getId().equals(typeReference.getId())) {
                try {
                    var endpointImage = tvSeriesAPI.getImageBySerie(Integer.valueOf(externalReference.getReference()))
                            .getPosters().getFirst().getFilePath();
                    var ext = endpointImage.split("\\.")[1];
                    var url = new URI(TvSeriesAPI.URL_IMAGE + endpointImage).toURL();
                    var image = ImageIO.read(url);
                    var file = new File(
                            MessageFormat.format("imageDB/TVSerie/{0}/cover.{1}", media.getId().toString(), ext));
                    try {
                        ImageIO.write(image, ext, file);
                    } catch (FileNotFoundException | IIOException e) {
                        file.mkdirs();
                        ImageIO.write(image, ext, file);
                    }
                } catch (NumberFormatException | TmdbException | URISyntaxException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return "";
    }

    /**
     * This method get the media and set cover and banner. It also persist to Database.
     * 
     * @param media media that doesn't have cover or banner
     * @throws GenericException when ExternalReference couldn't be found
     * @return Media object with cover and banner
     */
    public MediaModel handleArtworks(MediaModel media) {
        var externalReferences = media.getExternalReference();

        for (ExternalReferenceModel externalReference : externalReferences) {
            if (externalReference.getTypeReference().getId().equals(this.typeReference.getId())) { // * @AbigailGeovana procura pela ID de referencia dele do tmdb
                String banner = "";
                String cover = "";

                try {
                    cover = tvSeriesAPI.getImageBySerie(Integer.valueOf(externalReference.getReference())).getPosters()
                            .getFirst().getFilePath(); // * @AbigailGeovana Pega o endpoint da imagem

                    banner = tvSeriesAPI.getImageBySerie(Integer.valueOf(externalReference.getReference()))
                            .getBackdrops().getFirst().getFilePath(); // * @AbigailGeovana Pega o endpoint da imagem

                } catch (NumberFormatException | TmdbException e) {
                    e.printStackTrace();
                }

                media.setCover(TvSeriesAPI.URL_IMAGE + cover); // * @AbigailGeovana junta o restante da URL com o endpoint
                media.setBanner(TvSeriesAPI.URL_IMAGE + banner); // * @AbigailGeovana junta o restante da URL com o endpoint

                mediaService.save(media);

                return media;
            }
        }

        throw new GenericException("When trying to get images the External Reference couldn't be found!");
    }
    
    public MediaModel getAllInformation(MediaModel media) {
        TvSeriesDb serieInfo = null;

        try {
            serieInfo = tvSeriesAPI.getDetails(media.getExternalReference().stream()
                .filter(
                    (externalReference) -> externalReference.getTypeReference().getId().equals(this.typeReference.getId())
                ).findFirst().get().getId());
        } catch (TmdbException e) {
            e.printStackTrace();
        }

        if(serieInfo == null) {
            return media;
        }

        media.setAbout(serieInfo.getOverview() == null ? null : serieInfo.getOverview());
        media.setEpisodeLength(serieInfo.getEpisodeRunTime().isEmpty() ? null : serieInfo.getEpisodeRunTime().getFirst());
        media.setBanner(serieInfo.getBackdropPath() == null ? null : TvSeriesAPI.URL_IMAGE + serieInfo.getBackdropPath());
        media.setCover(serieInfo.getPosterPath() == null ? null : TvSeriesAPI.URL_IMAGE + serieInfo.getPosterPath());
        media.setTotalEpisodes(serieInfo.getNumberOfEpisodes() == null ? null : serieInfo.getNumberOfEpisodes());

        var tvdbReference = typeReferenceService.findById(TVDB_ID)
                .orElseThrow(() -> new GenericException("Type Reference not found"));
        var imdbReference = typeReferenceService.findById(IMDB_ID)
                .orElseThrow(() -> new GenericException("Type Reference not found"));
            
        var externalTvdb = new ExternalReferenceModel(null, serieInfo.getExternalIds().getTvdbId() == null ? null : serieInfo.getExternalIds().getTvdbId(), media, tvdbReference);    
        var externalImdb = new ExternalReferenceModel(null, serieInfo.getExternalIds().getImdbId() == null ? null : serieInfo.getExternalIds().getImdbId(), media, imdbReference);
        
        media.getExternalReference().forEach((external) -> {
            if (external.getTypeReference().equals(tvdbReference)) {
                externalTvdb.setId(external.getId());
            }
            if (external.getTypeReference().equals(imdbReference)) {
                externalImdb.setId(external.getId());
            }
        });

        externalReferenceService.save(externalTvdb);
        externalReferenceService.save(externalImdb);

        media = mediaService.save(media);

        return media;
    }
}
