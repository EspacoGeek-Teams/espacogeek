package com.espacogeek.geek.services.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.repositories.MediaRepository;
import com.espacogeek.geek.services.MediaService;

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private MediaRepository mediaRepository;

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
        return mediaRepository.findMediaByIdOrName(id, name);
    }
}
