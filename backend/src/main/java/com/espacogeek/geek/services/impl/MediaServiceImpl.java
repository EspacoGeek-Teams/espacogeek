package com.espacogeek.geek.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.repositories.MediaRepository;
import com.espacogeek.geek.services.MediaService;

@Service
public class MediaServiceImpl implements MediaService {
    @Autowired
    private MediaRepository mediaRepository;


    @Override
    /**
     * @see MediaService#save(MediaModel)
     */
    public void save(MediaModel mediaModel) {
        this.mediaRepository.save(mediaModel);        
    }

    @Override
    public List<Optional<MediaModel>> findSerieByIdOrName(Integer id, String name) {
        // ...
        return null;
    }    
}
