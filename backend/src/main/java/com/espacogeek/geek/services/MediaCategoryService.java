package com.espacogeek.geek.services;

import java.util.Optional;

import com.espacogeek.geek.models.MediaCategoryModel;

public interface MediaCategoryService {
    Optional<MediaCategoryModel> findById(Integer id);
}
