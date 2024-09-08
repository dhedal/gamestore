package com.ecf.gamestore.controller;

import com.ecf.gamestore.dto.CacheDataDTO;
import com.ecf.gamestore.dto.HomePageDataDTO;
import com.ecf.gamestore.mapper.HomePageDataMapper;
import com.ecf.gamestore.models.HomePageData;
import com.ecf.gamestore.service.GameArticleService;
import com.ecf.gamestore.service.HomePageDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/page-data")
public class PageDataController {
    private static final Logger LOG = LoggerFactory.getLogger(PageDataController.class);

    private HomePageDataService homePageDataService;
    private GameArticleService gameArticleService;

    @Autowired
    PageDataController(
            HomePageDataService homePageDataService,
            @Lazy GameArticleService gameArticleService) {
        this.homePageDataService = homePageDataService;
        this.gameArticleService = gameArticleService;
    }

    @GetMapping(path="/home_page_data", produces = "application/hal+json")
    public ResponseEntity<HomePageDataDTO> getHomePageData() {
        LOG.debug("## getHomePageData");
        try {
            HomePageData homePageData = this.homePageDataService.fetchHomePageData();
            return ResponseEntity.ok(HomePageDataMapper.toDTO(homePageData));
        } catch (Exception e) {
            LOG.error(e.toString());
        }
        return ResponseEntity.ok(new HomePageDataDTO());
    }

    @GetMapping(path="/cache-data", produces = "application/hal+json")
    public ResponseEntity<CacheDataDTO> getCacheData() {
        LOG.debug("## getGameListPageData");
        try {
            CacheDataDTO cacheDataDTO = new CacheDataDTO();
            cacheDataDTO.parseAndSetGameCountMap(this.gameArticleService.countGameArticleByPlatforms());
            LOG.debug(cacheDataDTO.toString());
            return ResponseEntity.ok(cacheDataDTO);
        } catch (Exception e) {
            LOG.error(e.toString());
        }

        return ResponseEntity.ok(new CacheDataDTO());
    }
}
