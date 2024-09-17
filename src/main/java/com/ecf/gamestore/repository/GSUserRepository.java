package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.GSUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GSUserRepository extends JpaRepository<GSUser, Long> {
    public GSUser findByUuid(String uuid);
    public Optional<GSUser> findByEmail(String email);
}
