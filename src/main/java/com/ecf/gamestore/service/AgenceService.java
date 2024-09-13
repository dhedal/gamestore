package com.ecf.gamestore.service;

import com.ecf.gamestore.models.Agence;
import com.ecf.gamestore.repository.AgenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AgenceService extends AbstractService<AgenceRepository, Agence>{
    private static final Logger LOG = LoggerFactory.getLogger(AgenceService.class);

    @Autowired
    public AgenceService(AgenceRepository repository) {
        super(repository);
    }

    public Agence findByUuid(String uuid) {
        LOG.debug("## findByUuid(String agence)");
        if(!StringUtils.hasText(uuid)) return null;
        return this.repository.findByUuid(uuid);
    }
}
