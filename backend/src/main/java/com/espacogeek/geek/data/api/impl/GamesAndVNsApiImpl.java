package com.espacogeek.geek.data.api.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.exceptions.RequestException;
import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.ProtoRequestKt;
import com.api.igdb.request.TwitchAuthenticator;
import com.api.igdb.utils.ImageBuilderKt;
import com.api.igdb.utils.ImageSize;
import com.api.igdb.utils.ImageType;
import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.data.api.MediaApi;
import com.espacogeek.geek.models.AlternativeTitleModel;
import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.MediaCategoryModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.models.TypeReferenceModel;
import com.espacogeek.geek.services.ApiKeyService;
import com.espacogeek.geek.services.MediaCategoryService;
import com.espacogeek.geek.services.TypeReferenceService;

import jakarta.annotation.PostConstruct;
import proto.Search;

@Component("gamesAndVNsAPI")
public class GamesAndVNsApiImpl implements MediaApi {

    @Autowired
    private ApiKeyService apiKeyService;
    private IGDBWrapper wrapper;
    private TypeReferenceModel typeReference;
    @Autowired
    private TypeReferenceService typeReferenceService;
    @Autowired
    private MediaCategoryService mediaCategoryService;
    private MediaCategoryModel category;
    private final static String VN_ID = "34"; // VN Genre ID in IGDB

    @PostConstruct
    private void init() {
        var tAuth = TwitchAuthenticator.INSTANCE;
        var clientId = apiKeyService.findById(IGDB_CLIENT_ID).orElseThrow().getKey();
        var clientSecrete = apiKeyService.findById(IGDB_CLIENT_SECRET).orElseThrow().getKey();
        var tokenId = apiKeyService.findById(IGDB_TOKEN).orElseThrow();
        var token = tAuth.requestTwitchToken(clientId, clientSecrete);

        if (!token.getAccess_token().equals(tokenId.getKey())) {
            tokenId.setKey(token.getAccess_token());
            apiKeyService.save(tokenId);
        }

        wrapper = IGDBWrapper.INSTANCE;
        wrapper.setCredentials(clientId, tokenId.getKey());

        typeReference = typeReferenceService.findById(MediaDataController.IGDB_ID).orElseThrow();
        category = mediaCategoryService.findById(MediaDataController.GAME_ID).orElseThrow();
    }

    @Override
    public List<MediaModel> doSearch(String search, MediaCategoryModel mediaCategoryModel) {
        var apicalypse = new APICalypse().search(search).fields("game.age_ratings, game.aggregated_rating, game.alternative_names.name, game.artworks.image_id, game.cover.image_id, game.name").where("game.genres " + (mediaCategoryModel.getId() == MediaDataController.GAME_ID ? "!=" : "=") + " [" + VN_ID + "]");
        List<MediaModel> medias = new ArrayList<>();

        try {
            var searchGames = ProtoRequestKt.search(wrapper, apicalypse);

            for (Search result : searchGames) {
                if (result.getGame() != null) {
                    var media = new MediaModel();
                    var reference = new ExternalReferenceModel(null, String.valueOf(result.getGame().getId()), media, typeReference);
                    if (!reference.getReference().equals("0")) break;

                    media.setName(result.getGame().getName());
                    media.setCover(!"".equals(result.getGame().getCover().getImageId()) ? ImageBuilderKt.imageBuilder(result.getGame().getCover().getImageId(), ImageSize.SCREENSHOT_HUGE, ImageType.PNG) : null);
                    media.setBanner(result.getGame().getArtworksList().isEmpty() ? null : ImageBuilderKt.imageBuilder(result.getGame().getArtworksList().getFirst().getImageId() , ImageSize.SCREENSHOT_HUGE, ImageType.PNG));

                    var alternativeTitles = new ArrayList<AlternativeTitleModel>();
                    for (proto.AlternativeName title : result.getGame().getAlternativeNamesList()) {
                        alternativeTitles.add(new AlternativeTitleModel(null, title.getName(), media));
                    }
                    media.setAlternativeTitles(alternativeTitles);
                    media.setExternalReference(Arrays.asList(reference));

                    media.setMediaCategory(category);

                    medias.add(media);
                }
            }

        } catch (RequestException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        // return medias.stream().filter((media) -> !media.getExternalReference().getFirst().getReference().equals("0")).toList();
        return medias;
    }
}
