package com.espacogeek.geek.services;

import java.util.List;
import com.espacogeek.geek.models.MediaModel;

public interface MediaService {
    List<MediaModel> findSerieByIdOrName(Integer id, String name);

    MediaModel save(MediaModel media);

    List<MediaModel> saveAll(List<MediaModel> medias);
}
