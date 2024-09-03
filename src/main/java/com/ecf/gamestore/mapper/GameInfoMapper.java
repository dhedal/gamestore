package com.ecf.gamestore.mapper;

import com.ecf.gamestore.dto.GameInfoDTO;
import com.ecf.gamestore.models.GameInfo;

import java.util.Objects;

public class GameInfoMapper {
    public static GameInfoDTO toDTO(GameInfo gameInfo) {
        if(Objects.isNull(gameInfo)) return null;

        GameInfoDTO dto = new GameInfoDTO();
        dto.setUuid(gameInfo.getUuid());
        dto.setTitle(gameInfo.getTitle());
        dto.setSummary(gameInfo.getSummary());
        dto.setFirstReleaseDate(gameInfo.getFirstReleaseDate());
        dto.setGenres(gameInfo.getGenres());
        dto.setPegi(gameInfo.getPegi());
        dto.setPlatforms(gameInfo.getPlatforms());
        dto.setCoverUrl(gameInfo.getCoverUrl());

        return dto;
    }
}
