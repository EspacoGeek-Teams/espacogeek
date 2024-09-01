package com.espacogeek.geek.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.data.api.MediaApi;
import com.espacogeek.geek.data.impl.GenericMediaDataControllerImpl;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.services.MediaCategoryService;
import com.espacogeek.geek.services.MediaService;
import com.espacogeek.geek.services.TypeReferenceService;

@Controller
public class MediaController {
    @Autowired
    protected MediaService mediaService;
    @Autowired
    private MediaDataController serieController;
    @Autowired
    private MediaDataController genericMediaDataController;
    @Autowired
    private MediaApi gamesAndVNsAPI;
    @Autowired
    private TypeReferenceService typeReferenceService;
    @Autowired
    private MediaCategoryService mediaCategoryService;

    @QueryMapping(name = "tvserie")
    public List<MediaModel> getSerie(@Argument Integer id, @Argument String name) {

        name = name == null ? null : name.trim();

        if (name == null & id == null || name == "" & id == null) {
            return new ArrayList<>();
        }

        var medias = this.mediaService.findSerieByIdOrName(id, name);

        var newMedias = new ArrayList<MediaModel>();
        for (MediaModel media: medias) {
            LocalDate mediaUpdateAt = media.getUpdateAt() == null ? null : LocalDate.ofInstant(media.getUpdateAt().toInstant(), ZoneId.systemDefault());

            if (mediaUpdateAt == null || ChronoUnit.DAYS.between(mediaUpdateAt, LocalDate.now()) > 1) {
                media = serieController.updateAllInformation(media, null);
            }
            newMedias.add(media);
        }
        return newMedias;
    }

    @QueryMapping(name = "game")
    public List<MediaModel> getGame(@Argument Integer id, @Argument String name) {

        name = name == null ? null : name.trim();

        if (name == null & id == null || name == "" & id == null) {
            return new ArrayList<>();
        }

        var mediasApiResult = genericMediaDataController.searchMedia(name, gamesAndVNsAPI, typeReferenceService.findById(MediaDataController.IGDB_ID).orElseThrow(), mediaCategoryService.findById(MediaDataController.GAME_ID).orElseThrow());

        return mediasApiResult;
    }
}
