package com.espacogeek.geek.mediaTest.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.espacogeek.geek.data.api.MediaApi;
import com.espacogeek.geek.services.ApiKeyService;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.tools.TmdbException;

@Nested
@DisplayName("API Availability")
public class ExternalApiAvailability {
    
    @Autowired
    private ApiKeyService apiKeyService;

    @Test
    void tmdb() throws TmdbException {
        var api = apiKeyService.findById(MediaApi.TMDB_API_ID).get().getKey();
        assertEquals(new TmdbApi(api).getAuthentication().validateKey().getSuccess(), true);
    }
}
