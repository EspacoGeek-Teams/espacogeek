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
        return this.mediaRepository.findMediaByIdOrNameOrAlternativeTitleAndMediaCategory(id, name, name, mediaCategoryService.findById(MediaDataController.SERIE_ID).get());
    }
    /**
     * @see MediaService#findSerieJoinFetchedByIdOrName(Integer, String)
     */
    @Override
    public List<MediaModel> findSerieJoinFetchedByIdOrName(Integer id, String name) {
        var category = mediaCategoryService.findById(MediaDataController.SERIE_ID).get();
        var medias = new HashMap<Integer, MediaModel>();

        this.mediaRepository.findMediaByIdOrNameOrAlternativeTitleAndMediaCategoryJoinFetchWithAlternativeTitles(id, name, name, category).forEach((mediaJoinFetched) -> {
            medias.compute(mediaJoinFetched.getId(), (idMedia, existingMedia) -> {
                if (existingMedia != null) {
                    existingMedia.setAlternativeTitles(mediaJoinFetched.getAlternativeTitles());
                    return existingMedia;
                } else {
                    return mediaJoinFetched;
                }
            });
        });

        this.mediaRepository.findMediaByIdOrNameOrAlternativeTitleAndMediaCategoryJoinFetchWithCompany(id, name, name, category).forEach((mediaJoinFetched) -> {
            medias.compute(mediaJoinFetched.getId(), (idMedia, existingMedia) -> {
                if (existingMedia != null) {
                    existingMedia.setCompany(mediaJoinFetched.getCompany());
                    return existingMedia;
                } else {
                    return mediaJoinFetched;
                }
            });
        });

        this.mediaRepository.findMediaByIdOrNameOrAlternativeTitleAndMediaCategoryJoinFetchWithGenre(id, name, name, category).forEach((mediaJoinFetched) -> {
            medias.compute(mediaJoinFetched.getId(), (idMedia, existingMedia) -> {
                if (existingMedia != null) {
                    existingMedia.setGenre(mediaJoinFetched.getGenre());
                    return existingMedia;
                } else {
                    return mediaJoinFetched;
                }
            });
        });

        this.mediaRepository.findMediaByIdOrNameOrAlternativeTitleAndMediaCategoryJoinFetchWithExternalReference(id, name, name, category).forEach((mediaJoinFetched) -> {
            medias.compute(mediaJoinFetched.getId(), (idMedia, existingMedia) -> {
                if (existingMedia != null) {
                    existingMedia.setExternalReference(mediaJoinFetched.getExternalReference());
                    return existingMedia;
                } else {
                    return mediaJoinFetched;
                }
            });
        });

        this.mediaRepository.findMediaByIdOrNameOrAlternativeTitleAndMediaCategoryJoinFetchWithPeople(id, name, name, category).forEach((mediaJoinFetched) -> {
            medias.compute(mediaJoinFetched.getId(), (idMedia, existingMedia) -> {
                if (existingMedia != null) {
                    existingMedia.setPeople(mediaJoinFetched.getPeople());
                    return existingMedia;
                } else {
                    return mediaJoinFetched;
                }
            });
        });

        this.mediaRepository.findMediaByIdOrNameOrAlternativeTitleAndMediaCategoryJoinFetchWithSeason(id, name, name, category).forEach((mediaJoinFetched) -> {
            medias.compute(mediaJoinFetched.getId(), (idMedia, existingMedia) -> {
                if (existingMedia != null) {
                    existingMedia.setSeason(mediaJoinFetched.getSeason());
                    return existingMedia;
                } else {
                    return mediaJoinFetched;
                }
            });
        });
        
        return new ArrayList<>(medias.values());
    }
}
