package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.GameInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameInfoRepository extends JpaRepository<GameInfo, Long> {

    public GameInfo findByUuid(String uuid);
}
