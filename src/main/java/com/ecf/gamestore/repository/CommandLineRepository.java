package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.CommandLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandLineRepository extends JpaRepository<CommandLine, Long> {
}
