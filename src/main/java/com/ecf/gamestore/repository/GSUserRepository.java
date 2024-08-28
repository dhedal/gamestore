package com.ecf.gamestore.repository;

import com.ecf.gamestore.models.GSUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GSUserRepository extends JpaRepository<GSUser, Long> {
    public GSUser findByUuid(String uuid);
    public GSUser findByEmail(String email);
}
