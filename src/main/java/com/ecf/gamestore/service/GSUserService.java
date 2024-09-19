package com.ecf.gamestore.service;

import com.ecf.gamestore.dto.UserInfoRequest;
import com.ecf.gamestore.dto.UserInfoResponse;
import com.ecf.gamestore.mapper.GSUserMapper;
import com.ecf.gamestore.models.GSUser;
import com.ecf.gamestore.models.embeddables.Address;
import com.ecf.gamestore.repository.GSUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;

@Service
public class GSUserService extends AbstractService<GSUserRepository, GSUser> {
    private static final Logger LOG = LoggerFactory.getLogger(GSUserService.class);

    private Validator validator;

    @Autowired
    public GSUserService(
            GSUserRepository repository,
            @Lazy Validator validator){
        super(repository);
        this.validator = validator;
    }

    /**
     *
     * @param email
     * @return
     */
    public GSUser getByEmail(String email) {
        LOG.debug("## getByEmail(String email)");
        if(!StringUtils.hasText(email)) return null;
        return this.repository.findByEmail(email).orElse(null);

    }

    public boolean isEmailExist(String email) {
        LOG.debug("## isEmailExist(String email) ");
        return Objects.nonNull(this.getByEmail(email));
    }

    public GSUser getByUuid(String uuid) {
        LOG.debug("## getByUuid(String uuid)");
        return this.repository.findByUuid(uuid);
    }

    public UserInfoResponse updateUserInfo(UserInfoRequest request, UserInfoResponse response) {
        LOG.debug("## updateUserInfo(UserInfoRequest request, , UserInfoResponse response");

        if(Objects.isNull(request)){
            throw new IllegalArgumentException("UserInfoRequest ne doit pas être null");
        }
        if(Objects.isNull(response)){
            throw new IllegalArgumentException("UserInfoResponse ne doit pas être null");
        }

        Set<ConstraintViolation<UserInfoRequest>> violations = this.validator.validate(request);
        if(!violations.isEmpty()) {
            for(ConstraintViolation<UserInfoRequest> violation : violations) {
                response.addMessage(violation.getMessage());
            }
            return response;
        }

        GSUser user = this.repository.findByUuid(request.getUuid());
        if(Objects.isNull(user)) {
            response.addMessage("l'utilisateur est introuvable");
            response.addMessage("Déconnectez vous du site, puis reconnectez vous");
            return response;
        }

        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        Address address = user.getAddress();
        address.setStreetAddress(request.getStreetAddress());
        address.setZipCode(request.getZipCode());
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());
        user.setAddress(address);

        this.save(user);
        response.setOk(true);
        response.setUser(GSUserMapper.toDTO(user));

        return response;
    }
}
