package com.ecf.gamestore.service;

import com.ecf.gamestore.models.GameArticle;
import com.ecf.gamestore.models.Promotion;
import com.ecf.gamestore.repository.PromotionRepository;
import com.ecf.gamestore.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
public class PromotionService extends AbstractService<PromotionRepository, Promotion>{
    private static final Logger LOG = LoggerFactory.getLogger(PromotionService.class);

    public PromotionService(PromotionRepository repository){
        super(repository);
    }

    /**
     *
     * @param gameArticles
     * @return
     */
    public List<Promotion> getPromotions(List<GameArticle> gameArticles) {
        LOG.debug("## getPromotions(List<GameArticle> gameArticles)");

        if(CollectionUtils.isNullOrEmpty(gameArticles)) return List.of();

        return this.repository.findByGameArticleInAndStartDateAfterAndEndDateBefore(gameArticles, LocalDate.now(), LocalDate.now());
    }

}
