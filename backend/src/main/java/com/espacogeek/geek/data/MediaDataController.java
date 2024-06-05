package com.espacogeek.geek.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.espacogeek.geek.data.API.TvSeriesAPI;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.services.MediaService;

import info.movito.themoviedbapi.model.core.TvSeries;
import info.movito.themoviedbapi.tools.TmdbException;

@Component
public class MediaDataController {
    @Autowired
    private MediaService mediaService;

    @Autowired
    private TvSeriesAPI tvSeriesAPI;

    public List<Optional<MediaModel>> search(String query) throws IOException, TmdbException {
        var result = tvSeriesAPI.doSearch(query).getResults();
        List<Optional<MediaModel>> medias = new ArrayList<>();
        for (TvSeries serie : result) {
            var media = new MediaModel(null, serie.getName(), null, null, serie.getOverview(), null, null, null, null, null);
            this.mediaService.save(media);
            medias.add(Optional.of(media));
        }
        return medias;
    }
}
