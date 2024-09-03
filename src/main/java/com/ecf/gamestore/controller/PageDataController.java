package com.ecf.gamestore.controller;

import com.ecf.gamestore.dto.HomePageDataDTO;
import com.ecf.gamestore.mapper.GameArticleMapper;
import com.ecf.gamestore.mapper.HomePageDataMapper;
import com.ecf.gamestore.models.HomePageData;
import com.ecf.gamestore.service.HomePageDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/page-data")
public class PageDataController {
    private static final Logger LOG = LoggerFactory.getLogger(PageDataController.class);

    private HomePageDataService homePageDataService;

    @Autowired
    PageDataController(HomePageDataService homePageDataService) {
        this.homePageDataService = homePageDataService;
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
}
