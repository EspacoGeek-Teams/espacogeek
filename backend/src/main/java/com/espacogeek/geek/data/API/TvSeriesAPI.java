package com.espacogeek.geek.data.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.exception.GenericException;
import com.espacogeek.geek.models.AlternativeTitleModel;
import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.MediaCategoryModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.services.ApiKeyService;
import com.espacogeek.geek.services.MediaCategoryService;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.model.core.AlternativeTitle;
import info.movito.themoviedbapi.model.keywords.Keyword;
import info.movito.themoviedbapi.model.tv.series.Images;
import info.movito.themoviedbapi.model.tv.series.TvSeriesDb;
import info.movito.themoviedbapi.tools.TmdbException;
import info.movito.themoviedbapi.tools.appendtoresponse.TvSeriesAppendToResponse;
import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import jakarta.annotation.PostConstruct;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class TvSeriesAPI {
    private TmdbApi tmdbApi;

    @Autowired
    private ApiKeyService apiKeyService;

    @Autowired
    private MediaCategoryService mediaCategoryService;

    public final static String URL_IMAGE = "https://image.tmdb.org/t/p/original";

    @PostConstruct
    private void init() {
        this.tmdbApi = new TmdbApi(this.apiKeyService.findById(1).get().getKey());
    }

    @SuppressWarnings("unused")
    private void fallbackMethod(RequestNotPermitted requestNotPermitted) {
        throw new GenericException(HttpStatus.TOO_MANY_REQUESTS.toString());
    }

    /**
     * This function get the daily datajump avaliable by tmdb
     * 
     * @return a JSON Array with all serie titles
     * @throws IOException
     * @throws ParseException
     */
    @SuppressWarnings("unchecked")
    public JSONArray updateTitles() throws IOException, ParseException {
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
        Response response = client.newCall(request).execute();

        // * @AbigailGeovana Descompacta e transforma em uma string
        var inputStream = new GZIPInputStream(new ByteArrayInputStream(response.body().bytes()));
        var json = new String(inputStream.readAllBytes()).split("\n");

        // * @AbigailGeovana tranforma em json
        JSONArray jsonArray = new JSONArray();
        for (var item : json) {
            JSONParser parser = new JSONParser();
            JSONObject jsonObject = (JSONObject) parser.parse(item);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    // @RateLimiter(name = "tmdbapi", fallbackMethod = "fallbackMethod") // * @AbigailGeovana limita as requisições a essa função, a configuração "tmdbapi" fica no arquivo application.proprieters. E chama a função "fallbackMethod()" se estorar o limite
    public MediaModel getDetails(Integer id) throws TmdbException {
        var rawSerieDetails = tmdbApi.getTvSeries().getDetails(id, "en-US", TvSeriesAppendToResponse.EXTERNAL_IDS, TvSeriesAppendToResponse.ALTERNATIVE_TITLES, TvSeriesAppendToResponse.IMAGES); // * @AbigailGeovana TvSeriesAppendToResponse.* serve para mim solicitar mais dados

        MediaModel serie = new MediaModel(
            null, 
            rawSerieDetails.getName(), 
            rawSerieDetails.getNumberOfEpisodes(), 
            rawSerieDetails.getEpisodeRunTime().isEmpty() ? null : rawSerieDetails.getEpisodeRunTime().getFirst(),
            rawSerieDetails.getOverview(), 
            rawSerieDetails.getPosterPath() == null ? null : URL_IMAGE + rawSerieDetails.getPosterPath(),
            rawSerieDetails.getBackdropPath() == null ? null : URL_IMAGE + rawSerieDetails.getBackdropPath(),
            mediaCategoryService.findById(MediaDataController.SERIE_ID).get(),
            null, 
            null,
            null,
            null,
            null,
            null,
            null
        );

        return serie;
    }
    
    public Images getImageBySerie(Integer id) throws TmdbException {
        return tmdbApi.getTvSeries().getImages(id, "en");
    }

    public List<Keyword> getKeyword(Integer id) throws TmdbException {
        return tmdbApi.getTvSeries().getKeywords(id).getResults();
    }

    public List<AlternativeTitleModel> getAlternativeTitles(Integer id) throws TmdbException {
        var rawAlternativeTitles = tmdbApi.getTvSeries().getAlternativeTitles(id).getResults();
        var alternativeTitles = new ArrayList<AlternativeTitleModel>();

        for (AlternativeTitle title : rawAlternativeTitles) {
            alternativeTitles.add(new AlternativeTitleModel(id, title.getTitle(), null));
        }

        return alternativeTitles;
    }

    public Map<Integer, ExternalReferenceModel> getExternalReference(Integer id) throws TmdbException {
        var rawExternalReferences = tmdbApi.getTvSeries().getExternalIds(id);
        var externalReferences = new HashMap<Integer, ExternalReferenceModel>();

        externalReferences.put(MediaDataController.TMDB_ID, new ExternalReferenceModel(null, id.toString(), null, null));
        externalReferences.put(MediaDataController.TVDB_ID, new ExternalReferenceModel(null, rawExternalReferences.getTvdbId(), null, null));
        externalReferences.put(MediaDataController.IMDB_ID, new ExternalReferenceModel(null, rawExternalReferences.getImdbId(), null, null));

        return externalReferences;
    }
}
