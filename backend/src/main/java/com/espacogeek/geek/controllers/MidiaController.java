package com.espacogeek.geek.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;

import com.espacogeek.geek.models.MidiaModel;
import com.espacogeek.geek.services.MidiaService;

public class MidiaController {
    @Autowired
    private MidiaService midiaService;

    @QueryMapping
    public List<Optional<MidiaModel>> findMidia() {
        
        return null;
    }
}
