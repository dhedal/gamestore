package com.ecf.gamestore.controller;

import com.ecf.gamestore.dto.HomePageDataDto;
import com.ecf.gamestore.service.GameVideoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/game")
public class GameVideoController {
    private static final Logger LOG = LoggerFactory.getLogger(GameVideoController.class);

    private final GameVideoService gameVideoService;

    @Autowired
    public GameVideoController(GameVideoService gameVideoService) {
        this.gameVideoService = gameVideoService;
    }

    @GetMapping(path="/home_page_data", produces = "application/hal+json")
    public ResponseEntity<HomePageDataDto> getHomePageData() {
        LOG.debug("## getHomePageData");
        try {
            return ResponseEntity.ok(new HomePageDataDto());
        } catch (Exception e) {
            LOG.error(e.toString());
        }
        return ResponseEntity.ok(new HomePageDataDto());
    }

}
