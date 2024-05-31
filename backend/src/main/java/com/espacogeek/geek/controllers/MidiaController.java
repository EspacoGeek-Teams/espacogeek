package com.espacogeek.geek.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.espacogeek.geek.data.MidiaDataController;
import com.espacogeek.geek.models.MidiaModel;
import com.espacogeek.geek.services.MidiaService;
import com.espacogeek.geek.types.MidiaInput;

import info.movito.themoviedbapi.tools.TmdbException;

@Controller
public class MidiaController {
    @Autowired
    private MidiaService midiaService;

    @QueryMapping
    public List<Optional<MidiaModel>> findSerie(@Argument(name = "filter") MidiaInput midiaInput) throws IOException, TmdbException {
        return new MidiaDataController().search(midiaInput.getName());
    }
}
