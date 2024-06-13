package com.espacogeek.geek.data.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
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

    public JSONArray updateTitles() throws IOException, ParseException {
        var now = LocalDateTime.now();

        // formatting the date to do request as tmdb pattern
        var month = new String().valueOf(now.getMonth().getValue()).length() == 1 ? 0 + new String().valueOf(now.getMonth().getValue()) : now.getMonth().getValue();
        var day = new String().valueOf(now.getDayOfMonth()).length() == 1 ? 0+now.getDayOfMonth() : now.getDayOfMonth();
        var year = new String().valueOf(now.getYear()).replace(".", "");

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
            .url(MessageFormat.format("http://files.tmdb.org/p/exports/tv_series_ids_{0}_{1}_{2}.json.gz", month, day, year))
            .method("GET", null)
            .addHeader("Content-Type", "application/json")
            .build();
        Response response = client.newCall(request).execute();
        
        InputStream inputSteam = new GZIPInputStream(new ByteArrayInputStream(response.body().bytes()));
        var stringJsonArray = IOUtils.toString(inputSteam);
        var jsonParse = new JSONParser();
        return (JSONArray) jsonParse.parse(stringJsonArray);
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
