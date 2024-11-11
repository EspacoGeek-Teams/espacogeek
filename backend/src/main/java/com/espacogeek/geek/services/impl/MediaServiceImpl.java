package com.espacogeek.geek.services.impl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.espacogeek.geek.data.MediaDataController;
import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.models.TypeReferenceModel;
import com.espacogeek.geek.repositories.MediaRepository;
import com.espacogeek.geek.services.MediaCategoryService;
import com.espacogeek.geek.services.MediaService;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.transaction.Transactional;

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
    @SuppressWarnings("unchecked")
    @Override
    public MediaModel save(MediaModel media) {
        return (MediaModel) this.mediaRepository.save(media);
    }

    /**
     * @see MediaService#saveAll(List<MediaModel>)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MediaModel> saveAll(List<MediaModel> medias) {
        return this.mediaRepository.saveAll(medias);
    }

    /**
     * @see MediaService#findSerieByIdOrName(Integer, String, Pageable)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<MediaModel> findSerieByIdOrName(Integer id, String name, Pageable pageable) {
        if (id != null) {
            return (Page<MediaModel>) this.mediaRepository.findById(id).orElseGet(null);
        }

        return this.mediaRepository.findMediaByNameOrAlternativeTitleAndMediaCategory(name, name,
                mediaCategoryService.findById(MediaDataController.SERIE_ID).get().getIdMediaCategory(), pageable);
    }

    /**
     * @see MediaService#findSerieByIdOrName(Integer, String, Map<String,
     *      List<String>>)
     */
    @SuppressWarnings("unchecked")
    @Override
    public List<MediaModel> findSerieByIdOrName(Integer id, String name, Map<String, List<String>> requestedFields) {
        var medias = new ArrayList<MediaModel>();

        if (id != null) {
            medias.add((MediaModel) this.mediaRepository.findById(id).orElseGet(null));
            return medias;
        }

        var results = mediaRepository.findMediaByNameOrAlternativeTitleAndMediaCategory(name, name,
                mediaCategoryService.findById(MediaDataController.SERIE_ID).get().getIdMediaCategory(), requestedFields);

        return results;
    }

    /**
     * @see MediaService#findGameByIdOrName(Integer, String, Pageable)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Page<MediaModel> findGameByIdOrName(Integer id, String name, Pageable pageable) {
        if (id != null) {
            return (Page<MediaModel>) this.mediaRepository.findById(id).orElseGet(null);
        }

        return this.mediaRepository.findMediaByNameOrAlternativeTitleAndMediaCategory(name, name,
                mediaCategoryService.findById(MediaDataController.GAME_ID).get().getIdMediaCategory(), pageable);
    }

    /**
     * @see MediaService#findById(Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Optional<MediaModel> findById(Integer idMedia) {
        return this.mediaRepository.findById(idMedia);
    }

    /**
     * @see MediaService#findByReferenceAndTypeReference(ExternalReferenceModel,
     *      TypeReferenceModel)
     */
    @SuppressWarnings("unchecked")
    @Override
    public Optional<MediaModel> findByReferenceAndTypeReference(ExternalReferenceModel reference,
            TypeReferenceModel typeReference) {
        return this.mediaRepository.findOneMediaByExternalReferenceAndTypeReference(reference.getReference(),
                typeReference);
    }

    /**
     * @see MediaService#findByIdEager(Integer)
     */
    @SuppressWarnings("unchecked")
    @Override
    @Transactional
    public Optional<MediaModel> findByIdEager(Integer id) {
        var fieldList = new ArrayList<Field>();
        MediaModel media = (MediaModel) mediaRepository.findById(id).orElseGet(null);
        if (media == null) return Optional.empty();

        for (Field field : media.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(OneToMany.class) || field.isAnnotationPresent(ManyToMany.class)) {
                fieldList.add(field);
            }
        }

        for (Field field : fieldList) {
            try {
                String getterName = "get" + capitalize(field.getName());
                Method getter = media.getClass().getMethod(getterName);
                var fieldValue = getter.invoke(media);
                if (fieldValue instanceof List) {
                    ((List<?>) fieldValue).size(); // This will initialize the collection
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        return Optional.ofNullable(media);
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);

    }
}
