package com.espacogeek.geek.data.API;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.espacogeek.geek.services.ApiKeyService;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbSearch;
import info.movito.themoviedbapi.model.core.TvSeriesResultsPage;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.TvSeriesAppendToResponse;
import jakarta.annotation.PostConstruct;
import okhttp3.MediaType;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class TvSeriesAPI {
    private TmdbApi tmdbApi;

    @Autowired
    private ApiKeyService apiKeyService;

    @PostConstruct
    private void init() {
        this.tmdbApi = new TmdbApi(this.apiKeyService.findById(1).get().getKey());
    }

    @Scheduled(fixedRate = 3600000)
    private void updateTitles() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
            .url("http://files.tmdb.org/p/exports/tv_series_ids_05_15_2024.json.gz")
            .method("GET", null)
            .addHeader("Content-Type", "application/json")
            .build();
        Response response = client.newCall(request).execute();
    }

    public TvSeriesDb getDetails(Integer id) throws TmdbException {
        return tmdbApi.getTvSeries().getDetails(id, "en-US", TvSeriesAppendToResponse.EXTERNAL_IDS, TvSeriesAppendToResponse.ALTERNATIVE_TITLES);
    }
    
    public Map<Integer, Object> doSearch(String query) throws IOException, TmdbException {
        var returnResult = new HashMap<Integer, Object>();
        
        returnResult.put(1, 1); // "1" is the code for The Movie Database
        returnResult.put(2, tmdbApi.getSearch().searchTv(query, null, null, null, null, null));

        return returnResult;
    }
    
    //TODO: get people (characters, staff, cast) function
    //TODO: get external IDs function
}
