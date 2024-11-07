package com.espacogeek.geek.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.data.api.MediaApi;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.services.MediaCategoryService;
import com.espacogeek.geek.services.MediaService;
import com.espacogeek.geek.services.TypeReferenceService;
import com.espacogeek.geek.types.MediaPage;
import com.espacogeek.geek.utils.Utils;
import com.espacogeek.geek.exception.GenericException;

import graphql.schema.DataFetchingEnvironment;
import jakarta.transaction.Transactional;

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

    @QueryMapping(name = "media")
    public MediaModel getMediaById(@Argument Integer id) {
        return this.mediaService.findByIdEager(id).orElseThrow(() -> new GenericException("Media not found"));
    }

    /**
     * Finds Series (MediaModel) objects by their ID or name.
     *
     * @param id   The ID of the Series (MediaModel) object to find.
     * @param name The name of the Series (MediaModel) object to find.
     * @return A list of Series (MediaModel) objects that match the provided ID or
     *         name.
     */
    @QueryMapping(name = "tvserie")
    @Transactional
    public MediaPage getSerie(@Argument Integer id, @Argument String name, DataFetchingEnvironment dataFetchingEnvironment) {
        MediaPage response = new MediaPage();
        name = name == null ? null : name.trim();

        if (name == null & id == null || name == "" & id == null) {
            return response;
        }

        var medias = this.mediaService.findSerieByIdOrName(id, name, Utils.getPageable(dataFetchingEnvironment));

        response.setContent(Utils.updateMedia(medias.getContent(), serieController));
        response.setTotalPages(medias.getTotalPages());
        response.setTotalElements(medias.getTotalElements());
        response.setNumber(medias.getNumber());
        response.setSize(medias.getSize());

        return response;
    }

    /**
     * Finds Game (MediaModel) objects by their ID or name. When searching by ID, the name parameter is not used amd all fields are updated.
     *
     * @param id   The ID of the Game (MediaModel) object to find.
     * @param name The name of the Game (MediaModel) object to find.
     * @return A list of Game (MediaModel) objects that match the provided ID or
     *         name.
     */
    @QueryMapping(name = "game")
    public MediaPage getGame(@Argument Integer id, @Argument String name, DataFetchingEnvironment dataFetchingEnvironment) {
        MediaPage response = new MediaPage();
        name = name == null ? null : name.trim();

        if (name == null & id == null || name == "" & id == null) {
            return response;
        }

        if (id != null) {
            var medias = mediaService.findGameByIdOrName(id, null, Utils.getPageable(dataFetchingEnvironment));

            response.setTotalPages(medias.getTotalPages());
            response.setTotalElements(medias.getTotalElements());
            response.setNumber(medias.getNumber());
            response.setSize(medias.getSize());
            response.setContent(Utils.updateGenericMedia(medias.getContent(), genericMediaDataController, typeReferenceService.findById(MediaDataController.IGDB_ID).get(), gamesAndVNsAPI));

            return response;
        }

        var medias = genericMediaDataController.searchMedia(name, gamesAndVNsAPI, typeReferenceService.findById(MediaDataController.IGDB_ID).orElseThrow(), mediaCategoryService.findById(MediaDataController.GAME_ID).orElseThrow());

        response.setContent(Utils.updateGenericMedia(medias, genericMediaDataController, typeReferenceService.findById(MediaDataController.IGDB_ID).get(), gamesAndVNsAPI));

        return response;
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
    public MediaPage getVisualNovel(@Argument Integer id, @Argument String name, DataFetchingEnvironment dataFetchingEnvironment) {
        MediaPage response = new MediaPage();
        name = name == null ? null : name.trim();

        if (name == null & id == null || name == "" & id == null) {
            return response;
        }

        if (id != null) {
            var medias = mediaService.findGameByIdOrName(id, null, Utils.getPageable(dataFetchingEnvironment));

            response.setTotalPages(medias.getTotalPages());
            response.setTotalElements(medias.getTotalElements());
            response.setNumber(medias.getNumber());
            response.setSize(medias.getSize());
            response.setContent(Utils.updateGenericMedia(medias.getContent(), genericMediaDataController, typeReferenceService.findById(MediaDataController.IGDB_ID).get(), gamesAndVNsAPI));

            return response;
        }

        var medias = genericMediaDataController.searchMedia(name, gamesAndVNsAPI, typeReferenceService.findById(MediaDataController.IGDB_ID).orElseThrow(), mediaCategoryService.findById(MediaDataController.GAME_ID).orElseThrow());

        response.setContent(Utils.updateGenericMedia(medias, genericMediaDataController, typeReferenceService.findById(MediaDataController.IGDB_ID).get(), gamesAndVNsAPI));

        return response;
    }
}
