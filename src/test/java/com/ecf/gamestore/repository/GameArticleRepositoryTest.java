package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.GameArticle;
import com.ecf.gamestore.models.GameInfo;
import com.ecf.gamestore.models.enumerations.GameGenre;
import com.ecf.gamestore.models.enumerations.Platform;
import com.ecf.gamestore.service.GameArticleServiceTest;
import com.ecf.gamestore.service.GameInfoService;
import com.ecf.gamestore.utils.CollectionUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class GameArticleRepositoryTest {
    private static final Logger LOG = LoggerFactory.getLogger(GameArticleRepositoryTest.class);

    @Autowired
    private GameArticleRepository gameArticleRepository;

    @Autowired
    private GameInfoService gameInfoService;

    @Test
    public void findByPlatformAndPriceBetween_whereStockNotNull_Test() {
        Platform platform = Platform.PS_5;
        Double priceMin = Double.valueOf(35);
        Double priceMax = Double.valueOf(42);
        Integer page = Integer.valueOf(0);
        Integer limit = Integer.valueOf(9);

        List<GameArticle> gameArticles = this.gameArticleRepository.findByPlatformAndPriceBetween(
                platform, priceMin, priceMax, PageRequest.of(page, limit));

        assertFalse(CollectionUtils.isNullOrEmpty(gameArticles));


        int count = (int) gameArticles.stream()
                .filter(gameArticle -> gameArticle.getStock().intValue() > 0)
                .filter(gameArticle -> Objects.equals(gameArticle.getPlatform(), platform))
                .filter(gameArticle -> gameArticle.getPrice().doubleValue() >= priceMin.doubleValue())
                .filter(gameArticle -> gameArticle.getPrice().doubleValue() <= priceMax.doubleValue())
                .count();
        assertTrue(count == gameArticles.size());
        assertTrue(count <= limit.intValue());
    }

    @Test
    public void findByPlatformAndGameInfosAndPriceBetween_whereStockNotNull_Test() {

        Platform platform = Platform.SWITCH;
        List< GameGenre> genres = List.of(GameGenre.PUZZLE,GameGenre.RPG,GameGenre.ADVENTURE);
        List<GameInfo> gameInfos = this.gameInfoService.findByPlatformAndGenres(platform, genres);


        Double priceMin = Double.valueOf(35);
        Double priceMax = Double.valueOf(42);
        Integer page = Integer.valueOf(0);
        Integer limit = Integer.valueOf(9);

        List<GameArticle> gameArticles = this.gameArticleRepository.findByPlatformAndGameInfosAndPriceBetween(
                platform, gameInfos, priceMin, priceMax, PageRequest.of(page, limit));

        assertFalse(CollectionUtils.isNullOrEmpty(gameArticles));


        int count = (int) gameArticles.stream()
                .filter(gameArticle -> gameArticle.getStock().intValue() > 0)
                .filter(gameArticle -> Objects.equals(gameArticle.getPlatform(), platform))
                .filter(gameArticle -> gameArticle.getPrice().doubleValue() >= priceMin.doubleValue())
                .filter(gameArticle -> gameArticle.getPrice().doubleValue() <= priceMax.doubleValue())
                .count();
        assertTrue(count == gameArticles.size());
        assertTrue(count <= limit.intValue());

        if(count == gameInfos.size()) return;

        List<Long> idBlackList = gameArticles.stream()
                .map(GameArticle::getGameInfo)
                .map(GameInfo::getId)
                .collect(Collectors.toList());

        List<GameInfo> newGameInfos = gameInfos.stream()
                .filter(gameInfo -> !idBlackList.contains(gameInfo.getId()))
                .collect(Collectors.toList());

        List<GameArticle> newGameArticles = this.gameArticleRepository.findByPlatformAndGameInfosAndPriceBetween(
                platform, newGameInfos, Double.valueOf(0), Double.valueOf(100), PageRequest.of(page, limit));

        int newCount = (int) newGameArticles.stream()
                .filter(gameArticle -> gameArticle.getStock().intValue() > 0)
                .filter(gameArticle -> Objects.equals(gameArticle.getPlatform(), platform))
                .filter(gameArticle -> {
                    return gameArticle.getPrice().doubleValue() < priceMin.doubleValue() ||
                            gameArticle.getPrice().doubleValue() > priceMax.doubleValue();
                })
                .count();

        assertTrue(newCount == newGameArticles.size());
        assertTrue(newCount <= limit.intValue());
        assertTrue(count + newCount == gameInfos.size());
    }

}
