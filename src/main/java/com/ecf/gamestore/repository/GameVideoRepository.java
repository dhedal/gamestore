package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.GameVideo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameVideoRepository extends JpaRepository<GameVideo, Long> {
    public GameVideo findByUuid(String uuid);
}
