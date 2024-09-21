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
import com.espacogeek.geek.utils.Utils;

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

        return Utils.updateMedia(medias, serieController);
    }

    @QueryMapping(name = "game")
    public List<MediaModel> getGame(@Argument Integer id, @Argument String name) {

        name = name == null ? null : name.trim();

        if (name == null & id == null || name == "" & id == null) {
            return new ArrayList<>();
        }

        if (id != null) {
            var media = mediaService.findGameByIdOrName(id, null);
            if (media != null) media = Utils.updateGenericMedia(media, genericMediaDataController, typeReferenceService.findById(MediaDataController.IGDB_ID).get(), gamesAndVNsAPI);
            return media;
        }

        return genericMediaDataController.searchMedia(name, gamesAndVNsAPI, typeReferenceService.findById(MediaDataController.IGDB_ID).orElseThrow(), mediaCategoryService.findById(MediaDataController.GAME_ID).orElseThrow());
    }
}
