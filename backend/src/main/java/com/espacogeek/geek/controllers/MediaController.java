package com.espacogeek.geek.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.services.MediaService;
import com.espacogeek.geek.types.MediaInput;

@Controller
public class MediaController {
    @Autowired
    private MediaService mediaService;

    @Autowired
    private MediaDataController mediaDataController;

    @QueryMapping
    public List<MediaModel> findSerie(@Argument(name = "filter") MediaInput mediaInput) {
        var medias = this.mediaService.findSerieByIdOrName(mediaInput.getId(), mediaInput.getName());

        medias.forEach((media) -> {
            if (media.getPathCover() == null) {
                media.setPathCover(mediaDataController.handleCoverImage(media));
            }
        });
        
        return medias;
    }
}
