package com.espacogeek.geek.API.requestcontrollers;

import java.io.IOException;

import com.espacogeek.geek.API.requests.TheMovieDB;
import com.fasterxml.jackson.databind.util.JSONPObject;

public class TvSeriesAPI extends TheMovieDB {
    private static final String midiaType = "tv";

    public JSONPObject getDetails(Integer id) {
        return new JSONPObject("tv", doRequest(id, midiaType));
    }
    
    //TODO: search function
    public void doSearch() throws IOException {

    }
    
    //TODO: get people (characters, staff, cast) function
    //TODO: get external IDs function
}
