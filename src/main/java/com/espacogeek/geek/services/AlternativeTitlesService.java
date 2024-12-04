package com.espacogeek.geek.services;

import java.util.List;

import org.springframework.dao.DataIntegrityViolationException;

import com.espacogeek.geek.models.AlternativeTitleModel;

public interface AlternativeTitlesService {
    public List<AlternativeTitleModel> saveAll(List<AlternativeTitleModel> alternativeTitles) throws DataIntegrityViolationException;
}
