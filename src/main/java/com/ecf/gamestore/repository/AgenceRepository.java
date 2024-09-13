package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.Agence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgenceRepository extends JpaRepository<Agence, Long> {

    public Agence findByUuid(String uuid);
}
