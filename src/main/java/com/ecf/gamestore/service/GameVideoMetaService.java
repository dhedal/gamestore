package com.ecf.gamestore.service;

import com.ecf.gamestore.models.GameVideoMeta;
import com.ecf.gamestore.repository.GameVideoMetaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameVideoMetaService extends AbstractService<GameVideoMetaRepository, GameVideoMeta>{
    private static final Logger LOG = LoggerFactory.getLogger(GameVideoMetaService.class);

    @Autowired
    public GameVideoMetaService(GameVideoMetaRepository repository){
        super(repository);
    }
}
