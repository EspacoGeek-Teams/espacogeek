package com.espacogeek.geek.API;

import java.io.IOException;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.TvSeriesResultsPage;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.TvSeriesAppendToResponse;

public class TvSeriesAPI {
    private TmdbApi tmdbApi;

    public TvSeriesAPI() {
        tmdbApi = new TmdbApi("");
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
