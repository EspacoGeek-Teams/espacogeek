package com.espacogeek.geek.data.impl;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.espacogeek.geek.data.api.MediaApi;
import com.espacogeek.geek.exception.GenericException;
import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.MediaCategoryModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.models.TypeReferenceModel;
import com.espacogeek.geek.services.ExternalReferenceService;
import com.espacogeek.geek.services.MediaCategoryService;
import com.espacogeek.geek.services.TypeReferenceService;

import jakarta.annotation.PostConstruct;

@Component("serieController")
public class SerieControllerImpl extends GenericMediaDataControllerImpl {
    @Autowired
    private MediaApi tvSeriesApi;
    @Autowired
    private MediaCategoryService mediaCategoryService;
    @Autowired
    private ExternalReferenceService externalReferenceService;
    @Autowired
    private TypeReferenceService typeReferenceService;
    private TypeReferenceModel typeReference;

    @PostConstruct
    private void init() {
        this.typeReference = typeReferenceService.findById(TMDB_ID).orElseThrow(() -> new GenericException("Type Reference not found"));
    }

    /**
     * This method update and add title of TV Series.
     * <p>
     * Every day at 9:00AM this function is executed.
     */
    // @Scheduled(cron = "* * 9 * * *")
    // @Scheduled(initialDelay = 1)
    @SuppressWarnings("unused")
    private void updateTvSeries() {
        MediaCategoryModel mediaCategory = mediaCategoryService.findById(SERIE_ID)
            .orElseThrow(() -> new GenericException("Category not found"));

        try {
            var jsonArrayDailyExport = tvSeriesApi.updateTitles();

            var media = new MediaModel();
            media.setMediaCategory(mediaCategory);

            var externalReference = new ExternalReferenceModel();
            externalReference.setTypeReference(typeReference);

            for (int i = 0; i < jsonArrayDailyExport.size(); i++) {

                var json = (JSONObject) jsonArrayDailyExport.get(i);

                if (tvSeriesApi.getKeyword(Integer.valueOf(json.get("id").toString())).stream().anyMatch((keyword) -> {
                    return keyword.getName().toLowerCase() == "anime" ? false : true;
                })) {

                    media.setName(json.get("original_name").toString());
                    externalReference.setReference(json.get("id").toString());

                    var externalReferenceExisted = externalReferenceService.findByReferenceAndType(externalReference.getReference(), typeReference);
                    if (externalReferenceExisted.isPresent()) {
                        media.setId(externalReferenceExisted.get().getMedia().getId());
                        externalReference.setIdExternalReference(externalReferenceExisted.get().getIdExternalReference());
                    }

                    var mediaSaved = mediaService.save(media);
                    externalReference.setMedia(mediaSaved);

                    var referenceSaved = externalReferenceService.save(externalReference);
                    List<ExternalReferenceModel> referenceListSaved = new ArrayList<>();
                    referenceListSaved.add(referenceSaved);
                    mediaSaved.setExternalReference(referenceListSaved);

                    media.setAlternativeTitles(updateAlternativeTitles(mediaSaved, null, typeReference, tvSeriesApi));
                }
            }

            System.out.println("SUCCESS TO UPDATE TV SERIES, AT " + LocalDateTime.now());

        } catch (Exception e) {
            System.out.println(MessageFormat.format("*# ------- FAILED TO UPDATE TV SERIES, AT {0} ------- *#", LocalDateTime.now()));
            e.printStackTrace();
            System.out.println("*# ----------------------------------------- *#");
        }
    }

    @Override
    public MediaModel updateAllInformation(MediaModel media, MediaModel result) {
        return super.updateAllInformation(media, result, this.typeReference, this.tvSeriesApi);
    }

}
