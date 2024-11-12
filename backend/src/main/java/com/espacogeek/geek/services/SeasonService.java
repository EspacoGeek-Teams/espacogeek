package com.espacogeek.geek.services;

import java.util.List;

import com.espacogeek.geek.models.SeasonModel;

public interface SeasonService {
    public List<SeasonModel> saveAll(List<SeasonModel> seasons);
}
