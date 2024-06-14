package com.espacogeek.geek.data.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.zip.GZIPInputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import com.espacogeek.geek.exception.GenericException;
import com.espacogeek.geek.services.ApiKeyService;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.tv.series.Images;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.TvSeriesAppendToResponse;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.annotation.PostConstruct;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class TvSeriesAPI {
    private TmdbApi tmdbApi;

    @Autowired
    private ApiKeyService apiKeyService;

    public final static String URL_IMAGE = "https://image.tmdb.org/t/p/original";

    @PostConstruct
    @RateLimiter(name = "tmdbapi", fallbackMethod = "fallbackMethod")
    private void init() {
        this.tmdbApi = new TmdbApi(this.apiKeyService.findById(1).get().getKey());
    }

    @SuppressWarnings("unused")
    private void fallbackMethod(RequestNotPermitted requestNotPermitted) {
        throw new GenericException(HttpStatus.TOO_MANY_REQUESTS.toString());
    }

    @SuppressWarnings("unchecked")
    public JSONArray updateTitles() throws IOException, ParseException {
        var now = LocalDateTime.now();

        // formatting the date to do request as tmdb pattern
        var month = String.valueOf(now.getMonth().getValue()).length() == 1 ? 0 + String.valueOf(now.getMonth().getValue()) : now.getMonth().getValue();
        var day = String.valueOf(now.getDayOfMonth()).length() == 1 ? 0+now.getDayOfMonth() : now.getDayOfMonth();
        var year = String.valueOf(now.getYear()).replace(".", "");

        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
            .url(MessageFormat.format("http://files.tmdb.org/p/exports/tv_series_ids_{0}_{1}_{2}.json.gz", month, day, year))
            .method("GET", null)
            .addHeader("Content-Type", "application/json")
            .build();
        Response response = client.newCall(request).execute();
        
        var inputStream = new GZIPInputStream(new ByteArrayInputStream(response.body().bytes()));
        var json = new String(inputStream.readAllBytes()).split("\n");

        JSONArray jsonArray = new JSONArray();
        for (var item : json) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(item);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    public TvSeriesDb getDetails(Integer id) throws TmdbException {
        return tmdbApi.getTvSeries().getDetails(id, "en-US", TvSeriesAppendToResponse.EXTERNAL_IDS, TvSeriesAppendToResponse.ALTERNATIVE_TITLES);
    }
    
    public Images getImageBySerie(Integer id) throws TmdbException {
    }
}
