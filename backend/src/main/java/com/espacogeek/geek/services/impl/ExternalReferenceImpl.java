package com.espacogeek.geek.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.repositories.ExternalReferenceRepository;
import com.espacogeek.geek.services.ExternalReferenceService;

@Service
public class ExternalReferenceImpl implements ExternalReferenceService {

    @Autowired
    private ExternalReferenceRepository externalReferenceRepository;

    /**
     * @see ExternalReferenceService#findAll(MediaModel)
     */
    @Override
    public List<ExternalReferenceModel> findAll(MediaModel media) {
        return this.externalReferenceRepository.findAll(Example.of(media));
    }

    /**
     * @see ExternalReferenceService#findById(Integer)
     */
    @Override
    public Optional<ExternalReferenceModel> findById(Integer id) {
        return this.externalReferenceRepository.findById(id);
    }
    
}