package com.ecf.gamestore.controller;

import com.ecf.gamestore.dto.AgenceDTO;
import com.ecf.gamestore.dto.CacheDataDTO;
import com.ecf.gamestore.dto.CartPageDataDTO;
import com.ecf.gamestore.dto.HomePageDataDTO;
import com.ecf.gamestore.mapper.AgenceMapper;
import com.ecf.gamestore.mapper.HomePageDataMapper;
import com.ecf.gamestore.models.HomePageData;
import com.ecf.gamestore.service.AgenceService;
import com.ecf.gamestore.service.GameArticleService;
import com.ecf.gamestore.service.HomePageDataService;
import com.ecf.gamestore.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("api/page-data")
public class PageDataController {
    private static final Logger LOG = LoggerFactory.getLogger(PageDataController.class);

    private HomePageDataService homePageDataService;
    private GameArticleService gameArticleService;
    private AgenceService agenceService;

    @Autowired
    PageDataController(
            HomePageDataService homePageDataService,
            @Lazy GameArticleService gameArticleService,
            @Lazy AgenceService agenceService) {
        this.homePageDataService = homePageDataService;
        this.gameArticleService = gameArticleService;
        this.agenceService = agenceService;
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

    @PostMapping(path="/cart_page_data", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CartPageDataDTO> getCartPageData(@RequestBody List<String> uuidList) {
        LOG.debug("## getCartPageData()");
        CartPageDataDTO cartPageDataDTO = new CartPageDataDTO();
        try {
            cartPageDataDTO.setAgences(AgenceMapper.toDTOs(this.agenceService.listAll()));
            if(!CollectionUtils.isNullOrEmpty(uuidList)) {
                cartPageDataDTO.setStockMap(this.gameArticleService.getStockMap(uuidList));
            }
        } catch (Exception e) {
            LOG.error(e.toString());
        }
        return ResponseEntity.ok(cartPageDataDTO);
    }
}
