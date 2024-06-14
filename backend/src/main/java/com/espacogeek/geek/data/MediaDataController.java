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

    private static final Integer ID_TYPE_TVSERIE = 1;

    @PostConstruct
    private void init() {
        this.typeReference = typeReferenceService.findById(ID_TYPE_TVSERIE)
                .orElseThrow(() -> new GenericException("Type Reference not found"));
    }
    
    /**
     * This method update and add title of TV Series.
     * Every day at 9:00AM this function is executed.
     */
    @Scheduled(cron = "* * 9 * * *")
    public void updateTvSeries() {
        try {
            var jsonArrayDailyExport = tvSeriesAPI.updateTitles();
            var medias = new ArrayList<MediaModel>();
            var externalReferences = new ArrayList<ExternalReferenceModel>();

            MediaCategoryModel category = mediaCategoryService.findById(1)
                    .orElseThrow(() -> new GenericException("Category not found"));
            TypeReferenceModel typeReference = typeReferenceService.findById(1)
                    .orElseThrow(() -> new GenericException("TypeReference not found"));

            for (int i = 0; i < jsonArrayDailyExport.size(); i++) {
                var json = (JSONObject) jsonArrayDailyExport.get(i);
                var externalReferenceList = new ArrayList<ExternalReferenceModel>();

                var media = new MediaModel(null, json.get("original_name").toString(), null, null, null, null, category,
                        null, null, null, null);
                var externalReference = new ExternalReferenceModel(null, json.get("id").toString(), null,
                        typeReference);

                var findReference = externalReferenceService.findByReferenceAndType(externalReference.getReference(),
                        typeReference);
                if (findReference.isPresent()) {
                    media.setId(findReference.get().getMediaModal().getId());
                    externalReference.setId(findReference.get().getId());
                }

                externalReferenceList.add(externalReference);
                media.setExternalReferenceModel(externalReferenceList);
                externalReference.setMediaModal(media);

                externalReferences.add(externalReference);
                medias.add(media);
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
     * This method handle with cover image of tv serie provided.
     * 
     * @param media that needs a id
     * @return a String containing URL of the tv serie cover 
     * @throws NumberFormatException
     * @throws TmdbException
     */
    public String handleCoverImage(MediaModel media) {
        var externalReferences = media.getExternalReferenceModel();
        for (ExternalReferenceModel externalReference : externalReferences) {
            if (externalReference.getTypeReferenceModel().getId().equals(typeReference.getId())) {
                try {
                    var endpointImage = tvSeriesAPI.getImageBySerie(Integer.valueOf(externalReference.getReference())).getPosters().getFirst().getFilePath();
                    var ext = endpointImage.split("\\.")[1];
                    var url = new URI(TvSeriesAPI.URL_IMAGE+endpointImage).toURL();
                    var image = ImageIO.read(url);
                    var file = new File(MessageFormat.format("imageDB/TVSerie/{0}/cover.{1}", media.getId().toString(), ext));
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
}
