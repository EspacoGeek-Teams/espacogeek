package com.espacogeek.geek.services;

import java.util.List;
import java.util.Optional;

import com.espacogeek.geek.models.MidiaModel;

public interface MidiaService {
    List<Optional<MidiaModel>> findMidiaByIdOrName(Integer id, String Name);

    List<Optional<MidiaModel>> findSerieByIdOrName(Integer id, String Name);
}
