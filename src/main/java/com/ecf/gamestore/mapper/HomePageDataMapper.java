package com.ecf.gamestore.mapper;

import com.ecf.gamestore.dto.HomePageDataDTO;
import com.ecf.gamestore.models.HomePageData;

import java.util.Objects;

public class HomePageDataMapper {
    public static HomePageDataDTO toDTO(HomePageData homePageData) {
        HomePageDataDTO dto = new HomePageDataDTO();

        if(Objects.nonNull(homePageData)) {
            dto.setUuid(homePageData.getUuid());
            dto.setGameArticles(GameArticleMapper.toDTOs(homePageData.getGamesArticles()));
            dto.setPromotions(GameArticleMapper.promotionsToGameArticles(homePageData.getPromotions()));
        }

        return dto;
    }
}
