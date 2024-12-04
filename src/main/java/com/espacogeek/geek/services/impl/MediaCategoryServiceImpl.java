package com.espacogeek.geek.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espacogeek.geek.models.MediaCategoryModel;
import com.espacogeek.geek.repositories.MediaCategoryRepository;
import com.espacogeek.geek.services.MediaCategoryService;

/**
 * A Implementation class of MediaCategoryService @see MediaCategoryService
 */
@Service
public class MediaCategoryServiceImpl implements MediaCategoryService {
    
    @Autowired
    private MediaCategoryRepository mediaCategoryRepository;

    /**
     * @see MediaCategoryService#findById(Integer)
     */
    @Override
    public Optional<MediaCategoryModel> findById(Integer id) {
        return mediaCategoryRepository.findById(id);
    }
}
