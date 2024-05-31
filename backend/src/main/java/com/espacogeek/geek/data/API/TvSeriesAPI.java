package com.espacogeek.geek.data.API;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.espacogeek.geek.services.ApiKeyService;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.TvSeriesResultsPage;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.TvSeriesAppendToResponse;

public class TvSeriesAPI {
    private TmdbApi tmdbApi;

    @Autowired
    private ApiKeyService apiKeyService;

    public TvSeriesAPI() {
        tmdbApi = new TmdbApi(apiKeyService.findById(1).get().getKey());
    }

    public TvSeriesDb getDetails(Integer id) throws TmdbException {
        return tmdbApi.getTvSeries().getDetails(id, "en-US", TvSeriesAppendToResponse.EXTERNAL_IDS, TvSeriesAppendToResponse.ALTERNATIVE_TITLES);
    }
    
    //TODO: search function
    public TvSeriesResultsPage doSearch(String query) throws IOException, TmdbException {
        return tmdbApi.getSearch().searchTv(query, null, null, null, null, null);
    }
    
    //TODO: get people (characters, staff, cast) function
    //TODO: get external IDs function
}
