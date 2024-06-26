package com.espacogeek.geek.controllers;

import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
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
            // * @AbigailGeovana se a ultima atualizacão for dentro do estipulado pega do endpoint change do tmdb se não pega todos os dados completo
            var mediaUpdateAt = LocalDate.ofInstant(media.getUpdateAt().toInstant(), ZoneId.systemDefault());
            if(media.getUpdateAt() == null || ChronoUnit.DAYS.between(mediaUpdateAt, LocalDate.now()) > 14){
                // TODO chama a função para pegar tudo
            } else {
                
            }

            if (media.getBanner() == null || media.getCover() == null) { // * @AbigailGeovana se a serie não tiver imagem ele busca uma imagem
                media = mediaDataController.handleArtworks(media);
            }
        });
        
        return medias;
    }
}
