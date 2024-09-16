package com.ecf.gamestore.service;

import com.ecf.gamestore.models.GSUser;
import com.ecf.gamestore.repository.GSUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

@Service
public class GSUserService extends AbstractService<GSUserRepository, GSUser> {
    private static final Logger LOG = LoggerFactory.getLogger(GSUserService.class);

    @Autowired
    public GSUserService(GSUserRepository repository){
        super(repository);
    }

    /**
     *
     * @param email
     * @return
     */
    public GSUser getByEmail(String email) {
        if(!StringUtils.hasText(email)) return null;
        return this.repository.findByEmail(email);

    }

    public boolean isEmailExist(String email) {
        return Objects.nonNull(this.getByEmail(email));
    }
}
