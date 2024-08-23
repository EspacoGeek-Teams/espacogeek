package com.espacogeek.geek.data.api.impl;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.igdb.apicalypse.APICalypse;
import com.api.igdb.exceptions.RequestException;
import com.api.igdb.request.IGDBWrapper;
import com.api.igdb.request.ProtoRequestKt;
import com.api.igdb.request.TwitchAuthenticator;
import com.api.igdb.utils.TwitchToken;
import com.espacogeek.geek.data.api.MediaApi;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.services.ApiKeyService;

import jakarta.annotation.PostConstruct;
import proto.Game;
import proto.Search;

@Component("gamesAndVNsAPI")
public class GamesAndVNsApiImpl implements MediaApi {

    @Autowired
    private ApiKeyService apiKeyService;
    private IGDBWrapper wrapper;

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
    }

    @Override
    public List<MediaModel> doSearch(String search) {
        var apicalypse = new APICalypse().fields("name, game, alternative_name");
        List<MediaModel> medias = new ArrayList<>();

        try {
            var searchGames = ProtoRequestKt.search(wrapper, apicalypse);

            searchGames.forEach((game) -> {
                medias.add(new MediaModel(null, game.getGame().getName(), null, null, null, null, null, null, null, null, null, null, null, null, null, null));
            });

        } catch (RequestException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return medias;
    }


}
