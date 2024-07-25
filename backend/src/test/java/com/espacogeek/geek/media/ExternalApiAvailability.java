package com.espacogeek.geek.media;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.rmi.ConnectException;
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
import com.espacogeek.geek.models.ApiKeyModel;
import com.espacogeek.geek.services.ApiKeyService;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.responses.TmdbResponseException;
import info.movito.themoviedbapi.tools.TmdbException;

@Nested
@SpringBootTest
@DisplayName("API Availability")
@ExtendWith(MockitoExtension.class)
public class ExternalApiAvailability {
    
    @Autowired
    private ApiKeyService apiKeyService;

    @Test
    void tmdb() throws TmdbException, ConnectException {
        boolean status = false;
        ApiKeyModel key = apiKeyService.findById(MediaApi.TMDB_API_KEY_ID).orElseThrow(() -> new NoSuchElementException("API key not found in database."));
        
        try {
            status = new TmdbApi(key.getKey()).getAuthentication().validateKey().getSuccess();
        } catch (TmdbResponseException e) {
            throw new ConnectException("Failed to connect The Movie Database servers.");
        }

        assertEquals(status, true);
    }
}
