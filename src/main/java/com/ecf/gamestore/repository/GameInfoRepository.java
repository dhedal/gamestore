package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.GameArticle;
import com.ecf.gamestore.models.GameInfo;
import com.ecf.gamestore.models.enumerations.Platform;
import com.ecf.gamestore.repository.custom.GameInfoRepositoryCustom;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameInfoRepository extends JpaRepository<GameInfo, Long>, GameInfoRepositoryCustom {

    public GameInfo findByUuid(String uuid);
}
