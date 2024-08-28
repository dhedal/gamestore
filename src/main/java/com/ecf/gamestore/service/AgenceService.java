package com.ecf.gamestore.service;

import com.ecf.gamestore.models.Agence;
import com.ecf.gamestore.repository.AgenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AgenceService extends AbstractService<AgenceRepository, Agence>{
    private static final Logger LOG = LoggerFactory.getLogger(AgenceService.class);

    public AgenceService(AgenceRepository repository) {
        super(repository);
    }
}
