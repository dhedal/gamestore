package com.ecf.gamestore.service;

import com.ecf.gamestore.models.GameArticle;
import com.ecf.gamestore.models.GameInfo;
import com.ecf.gamestore.models.enumerations.GameGenre;
import com.ecf.gamestore.models.enumerations.Platform;
import com.ecf.gamestore.repository.GameArticleRepository;
import com.ecf.gamestore.utils.CollectionUtils;
import com.ecf.gamestore.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


    /**
     *
     * @param platform
     * @param genres
     * @param page
     * @param limit
     * @return
     * @throws IllegalArgumentException
     */
    public List<GameArticle> getGameArticles(Platform platform, List<GameGenre> genres, Integer page, Integer limit) throws IllegalArgumentException{
        return this.getGameArticles(
                platform, genres, Double.valueOf(0), Double.valueOf(100), page, limit);
    }

    /**
     * Les paramètres sont des filtres de recherche sur une platform.<br>
     * Si genres est null ou empty
     * alors faire une recherche sur platform par genres
     * sinon return la liste d'article de  platform
     * @param platform Platform
     * @param genres List<GameGenre>
     * @param page Integer
     * @param limit Integer
     * @return
     */
    public List<GameArticle> getGameArticles(Platform platform, List<GameGenre> genres, Double priceMin, Double priceMax, Integer page, Integer limit) throws IllegalArgumentException{
        LOG.debug("## getGameArticles(Platform platform, List<GameGenre> genres, Double priceMin, Double priceMax, Integer page, Integer limit)");

        if(ObjectUtils.isNulls(platform, page, limit) || Objects.equals(Platform.UNDEFINED, platform) ||
                page.intValue() < 0 || limit.intValue() <= 0) throw new IllegalArgumentException("Arguments invalids");


        if(Objects.isNull(priceMin)) priceMin = Double.valueOf(0);
        if(Objects.isNull(priceMax)) priceMax = Double.valueOf(100);
        if(priceMin.doubleValue() > priceMax.doubleValue()) priceMin = priceMax;

        if(CollectionUtils.isNullOrEmpty(genres))
            return this.repository.findByPlatformAndPriceBetween(platform, priceMin, priceMax, PageRequest.of(page, limit));

        List<GameInfo> gameInfos = this.gameInfoService.findByPlatformAndGenres(platform, genres);
        if(CollectionUtils.isNullOrEmpty(gameInfos)) return List.of();

        return this.repository.findByPlatformAndGameInfosAndPriceBetween(platform, gameInfos, priceMin, priceMax, PageRequest.of(page, limit));
    }

    /**
     *
     * @return
     */
    public Map<Platform, Long> countGameArticleByPlatforms() {
        LOG.debug("## countGameArticleByPlatforms()");
        List<Object[]> results = this.repository.countGameArticleByPlatforms(Platform.values());
        Map<Platform, Long> countByPlatformMap = new HashMap<>();
        for(Object [] result : results) {
            countByPlatformMap.put((Platform) result[0], (Long) result[1]);
        }
        return countByPlatformMap;
    }


}
