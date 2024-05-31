package com.espacogeek.geek.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.espacogeek.geek.data.API.TvSeriesAPI;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.services.MediaService;

import info.movito.themoviedbapi.model.core.TvSeries;
import info.movito.themoviedbapi.tools.TmdbException;

public class MediaDataController {
    @Autowired
    private MediaService MediaService;

    public List<Optional<MediaModel>> search(String query) throws IOException, TmdbException {
        var result = new TvSeriesAPI().doSearch(query).getResults();
        List<Optional<MediaModel>> Medias = new ArrayList<>();
        for (TvSeries serie : result) {
            var Media = new MediaModel(null, serie.getName(), null, null, serie.getOverview(), null, null, null, null,null);
            MediaService.save(Media);
            Medias.add(Optional.of(Media));
        }
        return Medias;
    }
}
