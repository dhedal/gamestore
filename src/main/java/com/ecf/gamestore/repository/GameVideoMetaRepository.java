package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.GameVideoMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameVideoMetaRepository extends JpaRepository<GameVideoMeta, Long> {

    public GameVideoMeta findByUuid(String uuid);
}
