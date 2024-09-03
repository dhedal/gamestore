package com.ecf.gamestore.service;

import com.ecf.gamestore.models.GameArticle;
import com.ecf.gamestore.models.GameInfo;
import com.ecf.gamestore.repository.GameArticleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GameArticleService extends AbstractService<GameArticleRepository, GameArticle> {
    private static final Logger LOG = LoggerFactory.getLogger(GameArticleService.class);

    private GameInfoService gameInfoService;
    private PromotionService promotionService;

    @Autowired
    public GameArticleService(
            GameArticleRepository repository,
            @Lazy GameInfoService gameInfoService,
            @Lazy PromotionService promotionService
            ){
        super(repository);
        this.gameInfoService = gameInfoService;
        this.promotionService = promotionService;
    }


}
