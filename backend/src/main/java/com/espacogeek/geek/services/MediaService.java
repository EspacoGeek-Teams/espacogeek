package com.espacogeek.geek.services;

import java.util.List;
import java.util.Optional;

import com.espacogeek.geek.models.MediaModel;

public interface MediaService {
    List<Optional<MediaModel>> findSerieByIdOrName(Integer id, String Name);

    void save(MediaModel MediaModel);
}
