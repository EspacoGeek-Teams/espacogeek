package com.espacogeek.geek.media;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.espacogeek.geek.data.api.MediaApi;
import com.espacogeek.geek.services.ApiKeyService;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.tools.TmdbException;

@Nested
@SpringBootTest
@DisplayName("API Availability")
@ExtendWith(MockitoExtension.class)
public class ExternalApiAvailability {
    
    @Autowired
    private ApiKeyService apiKeyService;

    @Test
    void tmdb() throws TmdbException {
        var key = apiKeyService.findById(MediaApi.TMDB_API_KEY_ID).orElseThrow(() -> new NoSuchElementException("API key not found in database."));
        assertEquals(new TmdbApi(key.getKey()).getAuthentication().validateKey().getSuccess(), true);
    }
}
