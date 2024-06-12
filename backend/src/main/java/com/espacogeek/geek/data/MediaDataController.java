package com.espacogeek.geek.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.espacogeek.geek.data.api.TvSeriesAPI;
import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.MediaCategoryModel;
import com.espacogeek.geek.models.MediaModel;
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

    @Scheduled(fixedRate = 3600000)
    public void updateTvSeries() {
        try {
            var jsonArrayDailyExport = tvSeriesAPI.updateTitles();
            List<MediaModel> medias = new ArrayList<>();

            var category = mediaCategoryService.findById(1).get();
            var typeReference = typeReferenceService.findById(1).get();

            for (int i = 0; i < jsonArrayDailyExport.size(); i++) {
                var json = (JSONObject) jsonArrayDailyExport.get(i);

                var externalReference = new ExternalReferenceModel(null, json.get("id").toString(), null, typeReference);
                var media = new MediaModel(null, json.get("original_title").toString(), null, null, null, null, category, null, null, null, null);
                               
                externalReference.setMediaModal(media);

                medias.add(media);
            }
            
            mediaService.saveAll(medias);

        } catch (Exception e) {
            System.out.println("FAILED TO UPDATE TVSERIES");
            System.out.println(e);
        }
    }

    public List<Optional<MediaModel>> search(String query) throws IOException, TmdbException {
        @SuppressWarnings("unchecked")
        TvSeriesResultsPage result = (TvSeriesResultsPage) ((Results<TvSeries>) tvSeriesAPI.doSearch(query).get(2)).getResults();
        
        // fazer doSearch retornar um objeto TypeReference em vez de um numero 
        
        List<Optional<MediaModel>> medias = new ArrayList<>();
        
        for (TvSeries serie : result) {
            var media = new MediaModel(null, serie.getName(), null, null, serie.getOverview(), null, null, null, null, null, null);
            this.mediaService.save(media);
            medias.add(Optional.of(media));
        }
        
        return medias;
    }
}
