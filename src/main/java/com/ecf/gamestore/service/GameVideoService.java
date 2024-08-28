package com.ecf.gamestore.service;

import com.ecf.gamestore.models.GameVideo;
import com.ecf.gamestore.repository.GameVideoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class GameVideoService extends AbstractService<GameVideoRepository, GameVideo> {
    private static final Logger LOG = LoggerFactory.getLogger(GameVideoService.class);

    private GameVideoMetaService gameVideoMetaServiceService;
    private PromotionService promotionService;

    @Autowired
    public GameVideoService(GameVideoRepository repository){
        super(repository);
    }

    @Autowired
    public void setGameVideoMetaService(@Lazy GameVideoMetaService gameVideoMetaServiceService) {
        this.gameVideoMetaServiceService = gameVideoMetaServiceService;
    }

    @Autowired
    public void setPromotionService(@Lazy PromotionService promotionService) {
        this.promotionService = promotionService;
    }


}
