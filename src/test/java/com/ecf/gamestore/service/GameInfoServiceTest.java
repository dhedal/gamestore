package com.ecf.gamestore.service;

import com.ecf.gamestore.models.GameInfo;
import com.ecf.gamestore.models.enumerations.GameGenre;
import com.ecf.gamestore.models.enumerations.Platform;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class GameInfoServiceTest {

    @Autowired
    private GameInfoService gameInfoService;


    @Test
    public void findByPlatformAndGenresTest() {
        Platform platform = Platform.XBOX_SERIES;
        List<GameGenre> genres = List.of(GameGenre.FIGHTING, GameGenre.RACING);
        List<GameInfo> gameInfos = this.gameInfoService.findByPlatformAndGenres(platform, genres);
        assertNotNull(gameInfos);
        assertFalse(gameInfos.isEmpty());

        List<Long> ids = List.of(4L, 9L, 10L);
        int count = (int) gameInfos.stream().filter(gameInfo -> ids.contains(gameInfo.getId())).count();
        assertEquals(ids.size(), count);
    }
}
