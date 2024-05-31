package com.espacogeek.geek.data;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.espacogeek.geek.data.API.TvSeriesAPI;
import com.espacogeek.geek.models.MidiaModel;
import com.espacogeek.geek.services.MidiaService;

import info.movito.themoviedbapi.model.core.TvSeries;
import info.movito.themoviedbapi.tools.TmdbException;

public class MidiaDataController {
    @Autowired
    private MidiaService midiaService;

    public List<Optional<MidiaModel>> search(String query) throws IOException, TmdbException {
        var result = new TvSeriesAPI().doSearch(query).getResults();
        List<Optional<MidiaModel>> midias = new ArrayList<>();
        for (TvSeries serie : result) {
            var midia = new MidiaModel(null, serie.getName(), null, null, serie.getOverview(), null, null, null, null,null);
            midiaService.save(midia);
            midias.add(Optional.of(midia));
        }
        return midias;
    }
}
