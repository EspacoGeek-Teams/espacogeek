package com.espacogeek.geek.services;

import java.util.List;
import java.util.Optional;

import com.espacogeek.geek.models.MediaModel;

public interface MediaService {
    List<Optional<MediaModel>> findSerieByIdOrName(Integer id, String name);

    MediaModel save(MediaModel mediaModel);

    List<MediaModel> saveAll(List<MediaModel> medias);
}
