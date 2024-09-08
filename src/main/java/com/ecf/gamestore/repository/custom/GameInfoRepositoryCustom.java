package com.ecf.gamestore.repository.custom;

import com.ecf.gamestore.models.GameInfo;
import com.ecf.gamestore.models.enumerations.GameGenre;
import com.ecf.gamestore.models.enumerations.Platform;

import java.util.List;

public interface GameInfoRepositoryCustom {
    public List<GameInfo> findByPlatformAndGenresDynamic(Platform platform, List<GameGenre> genres);
}
