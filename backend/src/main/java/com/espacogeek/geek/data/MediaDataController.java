package com.espacogeek.geek.data;

import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import info.movito.themoviedbapi.model.core.Results;
import info.movito.themoviedbapi.model.core.TvSeries;
import info.movito.themoviedbapi.model.core.TvSeriesResultsPage;
import info.movito.themoviedbapi.tools.TmdbException;

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

    /**
     * This method update and add title of TV Series.
     * Every day at 9:00AM this function is executed.
     */
    @Scheduled(cron = "* * 9 * * *")
    public void updateTvSeries() {
        try {
            var jsonArrayDailyExport = tvSeriesAPI.updateTitles();
            var medias = new ArrayList<MediaModel>();
            var externalReferencies = new ArrayList<ExternalReferenceModel>();
            
            MediaCategoryModel category = mediaCategoryService.findById(1).orElseThrow(() -> new GenericException("Category not found"));
            TypeReferenceModel typeReference = typeReferenceService.findById(1).orElseThrow(() -> new GenericException("TypeReference not found"));

            for (int i = 0; i < jsonArrayDailyExport.size(); i++) {
                var json = (JSONObject) jsonArrayDailyExport.get(i);
                var externalReferenceList = new ArrayList<ExternalReferenceModel>();

                var media = new MediaModel(null, json.get("original_name").toString(), null, null, null, null, category, null, null, null, null);
                var externalReference = new ExternalReferenceModel(null, json.get("id").toString(), null, typeReference);

                var findReference = externalReferenceService.findByReferenceAndType(externalReference.getReference(), typeReference);
                if (findReference.isPresent()) {
                    media.setId(findReference.get().getMediaModal().getId());
                    externalReference.setId(findReference.get().getId());
                }

                externalReferenceList.add(externalReference);
                media.setExternalReferenceModel(externalReferenceList);
                externalReference.setMediaModal(media);

                externalReferencies.add(externalReference);
                medias.add(media);
            }

            mediaService.saveAll(medias);
            externalReferenceService.saveAll(externalReferencies);

            System.out.println("SUCCESS TO UPDATE TV SERIES, AT " + LocalDateTime.now());

        } catch (Exception e) {
            System.out.println(MessageFormat.format("*# ------- FAILED TO UPDATE TV SERIES, AT {0} ------- *#",
                    LocalDateTime.now()));
            e.printStackTrace();
            System.out.println("*# ----------------------------------------- *#");
        }
    }
    

}
