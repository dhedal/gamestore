package com.ecf.gamestore.service;

import com.ecf.gamestore.models.GameInfo;
import com.ecf.gamestore.repository.GameInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameInfoService extends AbstractService<GameInfoRepository, GameInfo>{
    private static final Logger LOG = LoggerFactory.getLogger(GameInfoService.class);

    @Autowired
    public GameInfoService(GameInfoRepository repository){
        super(repository);
    }

}
