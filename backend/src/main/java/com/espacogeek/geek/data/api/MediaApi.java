package com.espacogeek.geek.data.api;

import java.util.List;

import org.json.simple.JSONArray;

import com.espacogeek.geek.models.AlternativeTitleModel;
import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.GenreModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.models.SeasonModel;

import info.movito.themoviedbapi.model.keywords.Keyword;

public interface MediaApi {
    public final static String URL_IMAGE_TMDB = "https://image.tmdb.org/t/p/original";
    public final static Integer TMDB_API_KEY_ID = 1;

    public JSONArray updateTitles();

    public MediaModel getDetails(Integer id);

    public MediaModel getArtwork(Integer id);

    default public List<Keyword> getKeyword(Integer id) {
        throw new UnsupportedOperationException("getKeyword() method as not implemented.");
    }

    public List<AlternativeTitleModel> getAlternativeTitles(Integer id);

    public List<ExternalReferenceModel> getExternalReference(Integer id);

    public List<GenreModel> getGenre(Integer id);

    public List<SeasonModel> getSeason(Integer id);
}
