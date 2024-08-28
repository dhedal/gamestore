package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandRepository extends JpaRepository<Command, Long> {
}
