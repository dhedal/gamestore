package com.ecf.gamestore.controller;

import com.ecf.gamestore.dto.HomePageDataDTO;
import com.ecf.gamestore.mapper.GameArticleMapper;
import com.ecf.gamestore.service.GameArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("api/game")
public class GameArticleController {
    private static final Logger LOG = LoggerFactory.getLogger(GameArticleController.class);

    private final GameArticleService gameArticleService;

    @Autowired
    public GameArticleController(GameArticleService gameArticleService) {
        this.gameArticleService = gameArticleService;
    }

}
