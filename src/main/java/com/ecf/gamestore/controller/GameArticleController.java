package com.ecf.gamestore.controller;

import com.ecf.gamestore.dto.GameArticleDTO;
import com.ecf.gamestore.dto.GameFilterDTO;
import com.ecf.gamestore.dto.HomePageDataDTO;
import com.ecf.gamestore.mapper.GameArticleMapper;
import com.ecf.gamestore.models.GameArticle;
import com.ecf.gamestore.models.Promotion;
import com.ecf.gamestore.models.enumerations.GameGenre;
import com.ecf.gamestore.models.enumerations.Platform;
import com.ecf.gamestore.service.GameArticleService;
import com.ecf.gamestore.service.PromotionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Controller
@RequestMapping("api/game")
public class GameArticleController {
    private static final Logger LOG = LoggerFactory.getLogger(GameArticleController.class);

    private final GameArticleService gameArticleService;
    private final PromotionService promotionService;

    @Autowired
    public GameArticleController(GameArticleService gameArticleService, PromotionService promotionService) {
        this.gameArticleService = gameArticleService;
        this.promotionService = promotionService;
    }

    @PostMapping (path="/filter", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, GameArticleDTO>> getGamesByFilter (@RequestBody GameFilterDTO filterDTO) {
        LOG.debug("## getGamesByFilter (@RequestBody GameFilterDTO filterDTO)");
        try {
            LOG.debug(filterDTO.toString());
            Platform platform = Platform.getByKey(filterDTO.getPlatformKey());
            List<GameGenre> genres = filterDTO.getGenreKeys()
                    .stream()
                    .map(GameGenre::getByKey)
                    .collect(Collectors.toList());
            List<GameArticle> games = this.gameArticleService.getGameArticles(platform, genres,
                    filterDTO.getPriceMin(), filterDTO.getPriceMax(), filterDTO.getPage(), filterDTO.getLimit());

            List<Promotion> promotions = this.promotionService.getPromotions(games);

            return ResponseEntity.ok(GameArticleMapper.promotionsAndGameArticlesToMapGameArticleDTO(promotions, games));
        }catch (Exception e) {
            LOG.error(e.toString());
        }
        return ResponseEntity.ok(Map.of());
    }

}
