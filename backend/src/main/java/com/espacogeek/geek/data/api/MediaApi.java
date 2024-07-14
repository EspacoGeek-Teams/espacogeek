package com.espacogeek.geek.data.api;

import java.util.List;

import org.json.simple.JSONArray;

import com.espacogeek.geek.models.AlternativeTitleModel;
import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.MediaModel;

import info.movito.themoviedbapi.model.keywords.Keyword;

public interface MediaApi {
    public final static String URL_IMAGE_TMDB = "https://image.tmdb.org/t/p/original";

    public JSONArray updateTitles();

    public MediaModel getDetails(Integer id);

    public MediaModel getArtwork(Integer id);

    default public List<Keyword> getKeyword(Integer id) {
        throw new UnsupportedOperationException("getKeyword() method as not implemented.");
    }

    public List<AlternativeTitleModel> getAlternativeTitles(Integer id);

    public List<ExternalReferenceModel> getExternalReference(Integer id);
}
