package com.espacogeek.geek.services;

import java.util.List;

import com.espacogeek.geek.models.GenreModel;

public interface GenreService {
    public List<GenreModel> findAllByNames(List<String> names);
}
