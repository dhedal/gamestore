package com.ecf.gamestore.service;

import com.ecf.gamestore.models.GameInfo;
import com.ecf.gamestore.models.enumerations.GameGenre;
import com.ecf.gamestore.models.enumerations.Platform;
import com.ecf.gamestore.repository.GameInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class GameInfoService extends AbstractService<GameInfoRepository, GameInfo>{
    private static final Logger LOG = LoggerFactory.getLogger(GameInfoService.class);

    @Autowired
    public GameInfoService(GameInfoRepository repository){
        super(repository);
    }

    public List<GameInfo> findByPlatformAndGenres(Platform platform, List<GameGenre> genres) {
        LOG.debug("## findByPlatformAndGenres(Platform platform, List<GameGenre> genres)");
        if(Objects.nonNull(platform) && Objects.nonNull(genres) && !genres.isEmpty()) {
            String genresString = genres.stream()
                    .map(GameGenre::getKey)
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            return this.repository.findByPlatformAndGenresDynamic(platform, genres);
        }
        return List.of();
    }
}
