package com.espacogeek.geek.services;

import java.util.Optional;

import com.espacogeek.geek.models.ApiKeyModel;

public interface ApiKeyService {
    /** 
     * Get api key by id provided.
     * @param id apikey 
     */
    Optional<ApiKeyModel> findById(Integer id);
}
