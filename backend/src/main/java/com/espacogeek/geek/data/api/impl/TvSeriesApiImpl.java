package com.espacogeek.geek.data.api.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.data.api.MediaApi;
import com.espacogeek.geek.exception.GenericException;
import com.espacogeek.geek.models.AlternativeTitleModel;
import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.GenreModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.services.ApiKeyService;
import com.espacogeek.geek.services.GenreService;
import com.espacogeek.geek.services.MediaCategoryService;
import com.espacogeek.geek.services.TypeReferenceService;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.AlternativeTitle;
import info.movito.themoviedbapi.model.core.Genre;
import info.movito.themoviedbapi.model.keywords.Keyword;
import info.movito.themoviedbapi.model.tv.series.ExternalIds;
import info.movito.themoviedbapi.model.tv.series.Images;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.TvSeriesAppendToResponse;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import jakarta.annotation.PostConstruct;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component("tvSeriesApi")
public class TvSeriesApiImpl implements MediaApi {
    private TmdbApi tmdbApi;

    @Autowired
    private ApiKeyService apiKeyService;

    @Autowired
    private MediaCategoryService mediaCategoryService;

    @Autowired
    private TypeReferenceService typeReferenceService;

    @Autowired
    private GenreService genreService;

    @PostConstruct
    private void init() {
        this.tmdbApi = new TmdbApi(this.apiKeyService.findById(1).get().getKey());
    }

    @SuppressWarnings("unused")
    private void fallbackMethod(RequestNotPermitted requestNotPermitted) {
        throw new GenericException(HttpStatus.TOO_MANY_REQUESTS.toString());
    }

    /**
     * @see MediaApi#updateTitles()
     * 
     * This function get the daily datajump available by tmdb
     * 
     * @return a JSON Array with all serie titles
     * @throws IOException
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    @Override
    public JSONArray updateTitles() {
        var now = LocalDateTime.now();

        // formatting the date to do request as tmdb pattern
        var month = String.valueOf(now.getMonth().getValue()).length() == 1
                ? 0 + String.valueOf(now.getMonth().getValue())
                : now.getMonth().getValue();
        var day = String.valueOf(now.getDayOfMonth()).length() == 1 ? 0 + now.getDayOfMonth() : now.getDayOfMonth();
        var year = String.valueOf(now.getYear()).replace(".", "");

        // * @AbigailGeovana faz o request
        OkHttpClient client = new OkHttpClient().newBuilder().build();
        Request request = new Request.Builder()
                .url(MessageFormat.format("http://files.tmdb.org/p/exports/tv_series_ids_{0}_{1}_{2}.json.gz", month,
                        day, year))
                .method("GET", null)
                .addHeader("Content-Type", "application/json")
                .build();
        
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // * @AbigailGeovana Descompacta e transforma em uma string
        GZIPInputStream inputStream = null;
        String[] json = null;
        try {
            inputStream = new GZIPInputStream(new ByteArrayInputStream(response.body().bytes()));
            json = new String(inputStream.readAllBytes()).split("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // * @AbigailGeovana tranforma em json
        JSONArray jsonArray = new JSONArray();
        for (var item : json) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) parser.parse(item);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    /**
     * @see MediaApi#updateTitles(Integer)
     */
    // @RateLimiter(name = "tmdbapi", fallbackMethod = "fallbackMethod") // * @AbigailGeovana limita as requisições a essa função, a configuração "tmdbapi" fica no arquivo application.proprieters. E chama a função "fallbackMethod()" se estorar o limite
    @Override
    public MediaModel getDetails(Integer id) {
        TvSeriesDb rawSerieDetails = new TvSeriesDb();
        try {
            rawSerieDetails = tmdbApi.getTvSeries().getDetails(id, "en-US", TvSeriesAppendToResponse.EXTERNAL_IDS, TvSeriesAppendToResponse.ALTERNATIVE_TITLES, TvSeriesAppendToResponse.IMAGES); // * @AbigailGeovana TvSeriesAppendToResponse.* serve para mim solicitar mais dados
        } catch (TmdbException e) {
            e.printStackTrace();
        } 

        MediaModel serie = new MediaModel(
                null,
                rawSerieDetails.getName(),
                rawSerieDetails.getNumberOfEpisodes(),
                rawSerieDetails.getEpisodeRunTime().isEmpty() ? null : rawSerieDetails.getEpisodeRunTime().getFirst(),
                rawSerieDetails.getOverview(),
                rawSerieDetails.getPosterPath() == null ? null : URL_IMAGE_TMDB + rawSerieDetails.getPosterPath(),
                rawSerieDetails.getBackdropPath() == null ? null : URL_IMAGE_TMDB + rawSerieDetails.getBackdropPath(),
                mediaCategoryService.findById(MediaDataController.SERIE_ID).get(),
                formatExternalReference(rawSerieDetails.getExternalIds(), rawSerieDetails.getId()),
                null,
                null,
                formatGenre(rawSerieDetails.getGenres()),
                null,
                null,
                formatAlternativeTitles(rawSerieDetails.getAlternativeTitles().getResults()));
        
        return serie;
    }
    
    /**
     * @see MediaApi#getArtwork(Integer)
     */
    @Override
    public MediaModel getArtwork(Integer id) {
        Images rawArtwork = new Images();
        try {
            rawArtwork = tmdbApi.getTvSeries().getImages(id, "en");
        } catch (TmdbException e) {
            e.printStackTrace();
        }
        var media = new MediaModel();

        media.setCover(URL_IMAGE_TMDB + rawArtwork.getLogos().getFirst());
        media.setBanner(URL_IMAGE_TMDB + rawArtwork.getBackdrops().getFirst());

        return media;
    }

    /**
     * @see MediaApi#getArtwork(Integer)
     */
    @Override
    public List<Keyword> getKeyword(Integer id) {
        try {
            return tmdbApi.getTvSeries().getKeywords(id).getResults();
        } catch (TmdbException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * @see MediaApi#getAlternativeTitles(Integer)
     */
    @Override
    public List<AlternativeTitleModel> getAlternativeTitles(Integer id) {
        List<AlternativeTitle> rawAlternativeTitles = new ArrayList<>();
        try {
            rawAlternativeTitles = tmdbApi.getTvSeries().getAlternativeTitles(id).getResults();
        } catch (TmdbException e) {
            e.printStackTrace();
        }
        return formatAlternativeTitles(rawAlternativeTitles);
    }

    private List<AlternativeTitleModel> formatAlternativeTitles(List<AlternativeTitle> rawAlternativeTitles) {
        var alternativeTitles = new ArrayList<AlternativeTitleModel>();

        for (AlternativeTitle title : rawAlternativeTitles) {
            alternativeTitles.add(new AlternativeTitleModel(null, title.getTitle(), null));
        }

        return alternativeTitles;
    }

    /**
     * @see MediaApi#getExternalReference(Integer)
     */
    @Override
    public List<ExternalReferenceModel> getExternalReference(Integer id) {
        ExternalIds rawExternalReferences = new ExternalIds();
        try {
            rawExternalReferences = tmdbApi.getTvSeries().getExternalIds(id);
        } catch (TmdbException e) {
            e.printStackTrace();
        }
        return formatExternalReference(rawExternalReferences, id);
    }

    private List<ExternalReferenceModel> formatExternalReference(ExternalIds rawExternalReferences, Integer id) {
        var externalReferences = new ArrayList<ExternalReferenceModel>();

        externalReferences.add(new ExternalReferenceModel(null, id.toString(), null,
                typeReferenceService.findById(MediaDataController.TMDB_ID).get()));
        externalReferences.add(new ExternalReferenceModel(null, rawExternalReferences.getTvdbId(), null,
                typeReferenceService.findById(MediaDataController.TVDB_ID).get()));
        externalReferences.add(new ExternalReferenceModel(null, rawExternalReferences.getImdbId(), null,
                typeReferenceService.findById(MediaDataController.IMDB_ID).get()));

        return externalReferences;
    }

    /**
     * @see MediaApi#getGenre(Integer)
     */
    @Override
    public List<GenreModel> getGenre(Integer id) {
        TvSeriesDb rawSerieDetails = new TvSeriesDb();
        try {
            rawSerieDetails = tmdbApi.getTvSeries().getDetails(id, "en-US", TvSeriesAppendToResponse.EXTERNAL_IDS, TvSeriesAppendToResponse.ALTERNATIVE_TITLES, TvSeriesAppendToResponse.IMAGES); // * @AbigailGeovana TvSeriesAppendToResponse.* serve para mim solicitar mais dados
        } catch (TmdbException e) {
            e.printStackTrace();
        }

        return formatGenre(rawSerieDetails.getGenres());
    }

    private List<GenreModel> formatGenre(List<Genre> rawGenres) {
        List<GenreModel> genres = new ArrayList<GenreModel>();

        genres = genreService.findAllByNames(rawGenres.stream().map((rawGenre) -> rawGenre.getName()).toList());

        return genres;
    }
}
