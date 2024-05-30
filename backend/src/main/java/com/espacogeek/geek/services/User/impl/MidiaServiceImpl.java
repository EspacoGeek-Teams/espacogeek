package com.espacogeek.geek.services.User.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.espacogeek.geek.models.MidiaModel;
import com.espacogeek.geek.services.MidiaService;

@Service
public class MidiaServiceImpl implements MidiaService {
    @Autowired
    private MidiaService midiaService;

    @Override
    public List<Optional<MidiaModel>> findMidiaByIdOrName(Integer id, String Name) {
        return midiaService.findMidiaByIdOrName(id, Name);
    }

    @Override
    public List<Optional<MidiaModel>> findSerieByIdOrName(Integer id, String Name) {
        // ...
        return null;
    }    
}
