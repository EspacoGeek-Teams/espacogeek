package com.espacogeek.geek.API.requests;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class TheMovieDB {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = ""; // add to DB
    public static final String language = "en-US";

    public String doRequest(Integer id, String midiaType) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
            .url(BASE_URL + midiaType + "/" + id + "?language=" + language)
            .get()
            .addHeader("accept", "application/json")
            .addHeader("Authorization", "Bearer " + API_KEY)
            .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
    }
}
