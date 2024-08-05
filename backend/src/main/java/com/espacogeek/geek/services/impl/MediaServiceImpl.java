package com.espacogeek.geek.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.repositories.MediaRepository;
import com.espacogeek.geek.services.MediaCategoryService;
import com.espacogeek.geek.services.MediaService;

/**
 * A Implementation class of MediaService @see MediaService
 */
@Service
public class MediaServiceImpl implements MediaService {
    @SuppressWarnings("rawtypes")
    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private MediaCategoryService mediaCategoryService;

    /**
     * @see MediaService#save(MediaModel)
     */
    @Override
    public MediaModel save(MediaModel media) {
        return this.mediaRepository.save(media);
    }

    /**
     * @see MediaService#saveAll(List<MediaModel>)
     */
    @Override
    public List<MediaModel> saveAll(List<MediaModel> medias) {
        return this.mediaRepository.saveAll(medias);
    }

    /**
     * @see MediaService#findSerieByIdOrName(Integer, String)
     */
    @Override
    public List<MediaModel> findSerieByIdOrName(Integer id, String name) {
        var medias = new ArrayList<MediaModel>();

        if (id != null) {
            medias.add(this.mediaRepository.findById(id).orElseGet(null));
            return medias;
        }

        return this.mediaRepository.findMediaByIdOrNameOrAlternativeTitleAndMediaCategory(id, name, name, mediaCategoryService.findById(MediaDataController.SERIE_ID).get());
    }
}
