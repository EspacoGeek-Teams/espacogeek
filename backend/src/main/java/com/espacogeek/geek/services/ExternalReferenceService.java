package com.espacogeek.geek.services;

import java.util.List;
import java.util.Optional;

import com.espacogeek.geek.models.ExternalReferenceModel;

public interface ExternalReferenceService {

    Optional<ExternalReferenceModel> findById(Integer id);

    List<Optional<ExternalReferenceModel>> findBy
}

