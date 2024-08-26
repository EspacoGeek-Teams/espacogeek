package com.espacogeek.geek.data.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.models.TypeReferenceModel;
import com.espacogeek.geek.services.ApiKeyService;
import com.espacogeek.geek.services.TypeReferenceService;

import jakarta.annotation.PostConstruct;

@Component("gamesAndVNsAPI")
public class GamesAndVNsApiImpl implements MediaApi {

    @Autowired
    private ApiKeyService apiKeyService;
    private IGDBWrapper wrapper;
    private TypeReferenceModel typeReference;
    @Autowired
    private TypeReferenceService typeReferenceService;

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
    }

    @Override
    public List<MediaModel> doSearch(String search) {
        var apicalypse = new APICalypse().search(search).fields("game.age_ratings, game.aggregated_rating, game.alternative_names, game.artworks, game.cover, game.name");
        List<MediaModel> medias = new ArrayList<>();

        try {
            var searchGames = ProtoRequestKt.search(wrapper, apicalypse);

            var reference = new ExternalReferenceModel();
            reference.setTypeReference(typeReference);
            searchGames.forEach((result) -> {
                reference.setReference(String.valueOf(result.getGame().getId()));

                var media = new MediaModel();
                media.setName(result.getGame().getName());
                media.setCover(ImageBuilderKt.imageBuilder(result.getGame().getCover().getImageId(), ImageSize.SCREENSHOT_HUGE, ImageType.PNG));
                media.setBanner(result.getGame().getArtworksList() != null ? ImageBuilderKt.imageBuilder(result.getGame().getArtworksList().getFirst().getImageId() , ImageSize.SCREENSHOT_HUGE, ImageType.PNG) : null);

                var alternativeTitles = new AlternativeTitleModel();
                for (String titles : result.getAlternativeName().split(":")) {
                    alternativeTitles.setName(titles.trim());
                }
            });

        } catch (RequestException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return medias;
    }
}
