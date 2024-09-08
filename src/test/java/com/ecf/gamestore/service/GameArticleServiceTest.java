package com.ecf.gamestore.service;

import com.ecf.gamestore.models.GameArticle;
import com.ecf.gamestore.models.enumerations.GameGenre;
import com.ecf.gamestore.models.enumerations.Platform;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class GameArticleServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(GameArticleServiceTest.class);

    @Autowired
    private GameArticleService gameArticleService;


//    @Test
//    public void findByPlatformAndGenres_defaultTest() {
//        Platform platform = Platform.PC;
//        List<GameGenre> genres = List.of();
//        List<GameArticle> gameArticles = this.gameArticleService.getGameArticles(
//                platform, genres, Double.valueOf(0), Double.valueOf(100), 0, 9);
//        assertNotNull(gameArticles);
//        assertFalse(gameArticles.isEmpty());
//
//        gameArticles.forEach(System.out::println);
//
//        List<Long> ids = List.of(9L, 26L, 29L);
//        int count = (int) gameArticles.stream()
//                .filter(gameArticle -> {
//                    return Objects.equals(platform, gameArticle.getPlatform()) && ids.contains(gameArticle.getId());
//                }).count();
//        assertEquals(ids.size(), count);
//    }
    @Test
    public void findByPlatformAndGenresTest() {
        Platform platform = Platform.XBOX_SERIES;
        List<GameGenre> genres = List.of(GameGenre.FIGHTING, GameGenre.RACING);
        List<GameArticle> gameArticles = this.gameArticleService.getGameArticles(platform, genres, null, null, 0, 10);
        assertNotNull(gameArticles);
        assertFalse(gameArticles.isEmpty());

//        gameArticles.forEach(System.out::println);

        List<Long> ids = List.of(9L, 26L, 29L);
        int count = (int) gameArticles.stream()
                .filter(gameArticle -> {
                    return Objects.equals(platform, gameArticle.getPlatform()) && ids.contains(gameArticle.getId());
                }).count();
        assertEquals(ids.size(), count);
    }

    @Test
    public void countGameArticleByPlatformsTest() {
        Map<Platform, Long> map = this.gameArticleService.countGameArticleByPlatforms();
//        map.forEach((platform, count) -> System.out.println("%s --> %s".formatted(platform, count)));
        assertNotNull(map);
        assertFalse(map.isEmpty());

        Arrays.stream(Platform.values()).forEach(platform -> {
            if(platform.equals(Platform.UNDEFINED)) {
                assertNull(map.get(platform));
            }
            else {
                Long count = map.get(platform);
                assertNotNull(count);
                assertTrue(count.longValue() > 0);
            }
        });
    }
}
