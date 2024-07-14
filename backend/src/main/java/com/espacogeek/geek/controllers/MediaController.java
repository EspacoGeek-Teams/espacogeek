package com.espacogeek.geek.controllers;

import java.io.File;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
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
    private MediaDataController serieController;

    @QueryMapping
    public List<MediaModel> findSerie(@Argument(name = "filter") MediaInput mediaInput) {
        var medias = this.mediaService.findSerieByIdOrName(mediaInput.getId(), mediaInput.getName());
        var newMedias = new ArrayList<MediaModel>();

        for (MediaModel media: medias) {
            // * @AbigailGeovana se a ultima atualizacão for dentro do estipulado pega do endpoint change do tmdb se não pega todos os dados completo
            LocalDate mediaUpdateAt = media.getUpdateAt() == null ? null : LocalDate.ofInstant(media.getUpdateAt().toInstant(), ZoneId.systemDefault());

            if (mediaUpdateAt == null) {
                media = serieController.updateAllInformation(media, null);
            } else if (ChronoUnit.DAYS.between(mediaUpdateAt, LocalDate.now()) < 14 && ChronoUnit.DAYS.between(mediaUpdateAt, LocalDate.now()) > 1) {
                // TODO a method to get only the fields updated
                // ! by now we'll use updateAllInformation
                media = serieController.updateAllInformation(media, null);
            } else if (ChronoUnit.DAYS.between(mediaUpdateAt, LocalDate.now()) > 14) {
                media = serieController.updateAllInformation(media, null);
            }
            newMedias.add(media);
        }
        return newMedias;
    }
}
