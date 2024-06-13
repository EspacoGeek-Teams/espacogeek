package com.espacogeek.geek.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.espacogeek.geek.models.ExternalReferenceModel;
import com.espacogeek.geek.models.MediaModel;
import com.espacogeek.geek.models.TypeReferenceModel;
import com.espacogeek.geek.repositories.ExternalReferenceRepository;
import com.espacogeek.geek.services.ExternalReferenceService;

@Service
public class ExternalReferenceServiceImpl implements ExternalReferenceService {

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

    /**
     * @see ExternalReferenceService#save(ExternalReferenceModel)
     */
    @Override
    public ExternalReferenceModel save(ExternalReferenceModel externalReference) {
        return (ExternalReferenceModel) this.externalReferenceRepository.save(externalReference);
    }

    /**
     * @see ExternalReferenceService#saveAll(List<ExternalReferenceModel>)
     */
    @Override
    public List<ExternalReferenceModel> saveAll(List<ExternalReferenceModel> externalReferencies) {
        return this.externalReferenceRepository.saveAll(externalReferencies);
    }

    /**
     * @see ExternalReferenceService#findByIdAndType(Integer, TypeReferenceModel)
     */
    @Override
    public Optional<ExternalReferenceModel> findByReferenceAndType(String reference, TypeReferenceModel typeReference) {
        return this.externalReferenceRepository.findByReferenceAndTypeReferenceModel(reference, typeReference);
    }

    
}
