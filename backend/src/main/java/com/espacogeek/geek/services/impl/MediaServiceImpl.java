package com.espacogeek.geek.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.models.TypeReferenceModel;
import com.espacogeek.geek.repositories.MediaRepository;
import com.espacogeek.geek.services.MediaCategoryService;
import com.espacogeek.geek.services.MediaService;
import com.espacogeek.geek.utils.Utils;

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
        return (MediaModel) this.mediaRepository.save(media);
    }

    /**
     * @see MediaService#saveAll(List<MediaModel>)
     */
    @Override
    public List<MediaModel> saveAll(List<MediaModel> medias) {
        return this.mediaRepository.saveAll(medias);
    }

    /**
     * @see MediaService#findSerieByIdOrName(Integer, String, Pageable)
     */
    @Override
    public Page<MediaModel> findSerieByIdOrName(Integer id, String name, Pageable pageable) {
        if (id != null) {
            return (Page<MediaModel>) this.mediaRepository.findById(id).orElseGet(null);
        }

        return this.mediaRepository.findMediaByNameOrAlternativeTitleAndMediaCategory(name, name, mediaCategoryService.findById(MediaDataController.SERIE_ID).get().getId(), pageable);
    }

    /**
     * @see MediaService#findSerieByIdOrName(Integer, String, Map<String, List<String>>)
     */
    @Override
    public List<MediaModel> findSerieByIdOrName(Integer id, String name, Map<String, List<String>> requestedFields) {
        var medias = new ArrayList<MediaModel>();

        if (id != null) {
            medias.add((MediaModel) this.mediaRepository.findById(id).orElseGet(null));
            return medias;
        }

        var results = mediaRepository.findMediaByNameOrAlternativeTitleAndMediaCategory(name, name, mediaCategoryService.findById(MediaDataController.SERIE_ID).get().getId(), requestedFields);

        return results;
    }

    /**
     * @see MediaService#findGameByIdOrName(Integer, String, Pageable)
     */
    @Override
    public Page<MediaModel> findGameByIdOrName(Integer id, String name, Pageable pageable) {
        if (id != null) {
            return (Page<MediaModel>) this.mediaRepository.findById(id).orElseGet(null);
        }

        return this.mediaRepository.findMediaByNameOrAlternativeTitleAndMediaCategory(name, name, mediaCategoryService.findById(MediaDataController.GAME_ID).get().getId(), pageable);
    }

    /**
     * @see MediaService#findById(Integer)
     */
    @Override
    public Optional<MediaModel> findById(Integer idMedia) {
        return this.mediaRepository.findById(idMedia);
    }

    /**
     * @see MediaService#findByReferenceAndTypeReference(ExternalReferenceModel, TypeReferenceModel)
     */
    @Override
    public Optional<MediaModel> findByReferenceAndTypeReference(ExternalReferenceModel reference, TypeReferenceModel typeReference) {
        return this.mediaRepository.findOneMediaByExternalReferenceAndTypeReference(reference.getReference(), typeReference);
    }
}
