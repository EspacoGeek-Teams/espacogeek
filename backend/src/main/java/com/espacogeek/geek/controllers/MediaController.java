package com.espacogeek.geek.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

import com.espacogeek.geek.exception.GenericException;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.services.MediaService;
import com.espacogeek.geek.types.MediaInput;

import info.movito.themoviedbapi.tools.TmdbException;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;

@Controller
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @QueryMapping
    public List<Optional<MediaModel>> findSerie(@Argument(name = "filter") MediaInput mediaInput) throws IOException, TmdbException {
        return this.mediaService.findSerieByIdOrName(mediaInput.getId(), mediaInput.getName());
    }
}
