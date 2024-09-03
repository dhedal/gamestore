package com.ecf.gamestore.services;

import com.ecf.gamestore.dto.GameArticleDTO;
import com.ecf.gamestore.mapper.GameArticleMapper;
import com.ecf.gamestore.models.GameArticle;
import com.ecf.gamestore.service.GameArticleService;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class GameArticleServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(GameArticleServiceTest.class);

    @Autowired
    private GameArticleService gameArticleService;

    @Test
    public void testGetLastGames() {
        List<GameArticle> gameArticles = this.gameArticleService.getLastGames(5);

        assertNotNull(gameArticles);

        gameArticles.forEach(System.out::println);
    }
}
