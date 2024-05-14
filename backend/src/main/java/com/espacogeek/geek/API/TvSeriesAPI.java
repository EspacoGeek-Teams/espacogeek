package com.espacogeek.geek.API;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TvSeriesAPI {
    public static final String BASE_URL = "https://api.themoviedb.org/3/tv/";
    public static final String API_KEY = ""; // add to DB
    public static final String language = "en-US";

    public void getTvSeriesDetails() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(BASE_URL + "1402?language=" + language)
                .get()
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        Response response = client.newCall(request).execute();
        // System.out.println(response.body().string());
    }
    
    //TODO: search function
    //TODO: get people (characters, staff, cast) function
    //TODO: get external IDs function
}
