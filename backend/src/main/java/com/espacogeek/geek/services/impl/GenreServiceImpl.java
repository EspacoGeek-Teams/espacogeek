package com.espacogeek.geek.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espacogeek.geek.models.GenreModel;
import com.espacogeek.geek.repositories.GenreRepository;
import com.espacogeek.geek.services.GenreService;

@Service
public class GenreServiceImpl implements GenreService {

    @Autowired
    private GenreRepository genreRepository;

    /**
     * @see GenreService#findAllByName(String)
     */
    @Override
    public List<GenreModel> findAllByNames(List<String> names) {
        return genreRepository.findAllByNames(names);
    }
}
