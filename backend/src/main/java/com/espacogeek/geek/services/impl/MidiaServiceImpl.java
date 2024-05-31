package com.espacogeek.geek.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espacogeek.geek.models.MidiaModel;
import com.espacogeek.geek.repositories.MidiaRepository;
import com.espacogeek.geek.services.MidiaService;

@Service
public class MidiaServiceImpl implements MidiaService {
    @Autowired
    private MidiaRepository midiaRepository;


    @Override
    /**
     * @see MidiaService#save(MidiaModel)
     */
    public void save(MidiaModel midiaModel) {
        midiaRepository.save(midiaModel);        
    }

    @Override
    public List<Optional<MidiaModel>> findSerieByIdOrName(Integer id, String Name) {
        // ...
        return null;
    }    
}
