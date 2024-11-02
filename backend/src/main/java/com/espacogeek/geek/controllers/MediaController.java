package com.espacogeek.geek.controllers;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.SelectedField;

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

    /**
     * Finds Series (MediaModel) objects by their ID or name.
     *
     * @param id   The ID of the Series (MediaModel) object to find.
     * @param name The name of the Series (MediaModel) object to find.
     * @return A list of Series (MediaModel) objects that match the provided ID or
     *         name.
     */
    @QueryMapping(name = "tvserie")
    public List<MediaModel> getSerie(@Argument Integer id, @Argument String name,
            DataFetchingEnvironment dataFetchingEnvironment) {

        name = name == null ? null : name.trim();

        if (name == null & id == null || name == "" & id == null) {
            return new ArrayList<>();
        }

        var requestedFields = dataFetchingEnvironment.getSelectionSet()
                .getFields()
                .stream()
                .map(SelectedField::getName)
                .map((item) -> "m." + item)
                .collect(Collectors.toList());

        var medias = this.mediaService.findSerieByIdOrName(id, name, requestedFields);

        return Utils.updateMedia(medias, serieController);
    }

    /**
     * Finds Game (MediaModel) objects by their ID or name.
     *
     * @param id   The ID of the Game (MediaModel) object to find.
     * @param name The name of the Game (MediaModel) object to find.
     * @return A list of Game (MediaModel) objects that match the provided ID or
     *         name.
     */
    @QueryMapping(name = "game")
    public List<MediaModel> getGame(@Argument Integer id, @Argument String name) {

        name = name == null ? null : name.trim();

        if (name == null & id == null || name == "" & id == null) {
            return new ArrayList<>();
        }

        if (id != null) {
            var media = mediaService.findGameByIdOrName(id, null);
            if (media != null)
                media = Utils.updateGenericMedia(media, genericMediaDataController,
                        typeReferenceService.findById(MediaDataController.IGDB_ID).get(), gamesAndVNsAPI);
            return media;
        }

        return genericMediaDataController.searchMedia(name, gamesAndVNsAPI,
                typeReferenceService.findById(MediaDataController.IGDB_ID).orElseThrow(),
                mediaCategoryService.findById(MediaDataController.GAME_ID).orElseThrow());
    }

    /**
     * Finds Visual Novel (MediaModel) objects by their ID or name.
     *
     * @param id   The ID of the Visual Novel (MediaModel) object to find.
     * @param name The name of the Visual Novel (MediaModel) object to find.
     * @return A list of Visual Novel (MediaModel) objects that match the provided
     *         ID or name.
     */
    @QueryMapping(name = "vn")
    public List<MediaModel> getVisualNovel(@Argument Integer id, @Argument String name) {

        name = name == null ? null : name.trim();

        if (name == null & id == null || name == "" & id == null) {
            return new ArrayList<>();
        }

        if (id != null) {
            var media = mediaService.findGameByIdOrName(id, null); // do a find by vn
            if (media != null)
                media = Utils.updateGenericMedia(media, genericMediaDataController,
                        typeReferenceService.findById(MediaDataController.IGDB_ID).get(), gamesAndVNsAPI);
            return media;
        }

        return genericMediaDataController.searchMedia(name, gamesAndVNsAPI,
                typeReferenceService.findById(MediaDataController.IGDB_ID).orElseThrow(),
                mediaCategoryService.findById(MediaDataController.VN_ID).orElseThrow());
    }
}
