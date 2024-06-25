package com.espacogeek.geek.services.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espacogeek.geek.models.TypeReferenceModel;
import com.espacogeek.geek.repositories.TypeReferenceRepository;
import com.espacogeek.geek.services.TypeReferenceService;

/**
 * A Implementation class of TypeReferenceRepository @see TypeReferenceRepository
 */
@Service
public class TypeReferenceServiceImpl implements TypeReferenceService {
    
    @Autowired
    private TypeReferenceRepository typeReferenceRepository;
    
    /**
     * @see TypeReferenceService#findById(Integer)
     */
    @Override
    public Optional<TypeReferenceModel> findById(Integer id) {
        return this.typeReferenceRepository.findById(id);
    }
}
