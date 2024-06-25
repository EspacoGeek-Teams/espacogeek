package com.espacogeek.geek.controllers;

import java.io.File;
import java.net.MalformedURLException;
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

        medias.forEach((media) -> { // * @AbigailGeovana se a serie não tiver imagem ele busca uma imagem
            if (media.getBanner() == null || media.getCover() == null) {
                media = mediaDataController.handleArtworks(media);
            }
        });
        
        return medias;
    }
}
