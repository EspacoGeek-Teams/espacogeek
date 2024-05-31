package com.espacogeek.geek.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.services.MediaService;
import com.espacogeek.geek.types.MediaInput;

import info.movito.themoviedbapi.tools.TmdbException;

@Controller
public class MediaController {
    @Autowired
    private MediaService MediaService;

    @QueryMapping
    public List<Optional<MediaModel>> findSerie(@Argument(name = "filter") MediaInput MediaInput) throws IOException, TmdbException {
        return new MediaDataController().search(MediaInput.getName());
    }
}
