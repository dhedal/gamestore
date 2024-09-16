package com.ecf.gamestore.service;

import com.ecf.gamestore.dto.SigninRequest;
import com.ecf.gamestore.dto.SigninResponse;
import com.ecf.gamestore.dto.SignupRequest;
import com.ecf.gamestore.dto.SignupResponse;
import com.ecf.gamestore.mapper.GSUserMapper;
import com.ecf.gamestore.models.GSUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Objects;
import java.util.Set;

@Service
public class AuthenticationService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationService.class);

    private GSUserService gsUserService;
    private MailService mailService;
    private Validator validator;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationService(
            @Lazy GSUserService gsUserService,
            MailService mailService,
            Validator validator,
            BCryptPasswordEncoder  passwordEncoder){
        this.gsUserService = gsUserService;
        this.mailService = mailService;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public SigninResponse authentication(SigninRequest request, SigninResponse response) {
        LOG.debug("## authentication(SigninRequest request, SigninResponse response)");

        if(Objects.isNull(request)){
            throw new IllegalArgumentException("SigninRequest ne doit pas être null");
        }
        if(Objects.isNull(response)){
            throw new IllegalArgumentException("SigninResponse ne doit pas être null");
        }

        Set<ConstraintViolation<SigninRequest>> violations = this.validator.validate(request);
        if(!violations.isEmpty()) {
            for(ConstraintViolation<SigninRequest> violation : violations) {
                response.addMessage(violation.getMessage());
            }
            return response;
        }

        GSUser user = this.gsUserService.getByEmail(request.getEmail());

       if(Objects.isNull(user)) {
           response.addMessage("L'email est introuvable.");
           return response;
        }

        if(!this.passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            response.addMessage("le mot de passe ne correspond pas.");
            return response;
        }

        response.setUser(GSUserMapper.toDTO(user));
        return response;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public SignupResponse save(SignupRequest request, SignupResponse response) {
        LOG.debug("## save(SignupRequest request, SignupResponse response)");

        if(Objects.isNull(request)){
            throw new IllegalArgumentException("SignupRequest ne doit pas être null");
        }
        if(Objects.isNull(response)){
            throw new IllegalArgumentException("SignupResponse ne doit pas être null");
        }

        Set<ConstraintViolation<SignupRequest>> violations = this.validator.validate(request);
        if(!violations.isEmpty()) {
            for(ConstraintViolation<SignupRequest> violation : violations) {
                response.addMessage(violation.getMessage());
            }
            return response;
        }

        if(this.gsUserService.isEmailExist(request.getEmail())) {
            response.addMessage("L'email existe déjà.");
            return response;
        }

        GSUser user = GSUserMapper.toGSUser(request);
        user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        user = this.gsUserService.save(user);

        response.setOk(true);

        if(this.mailService.sendSignupConfirmation(user)) {
            response.setEmailSent(true);
        }

        return response;
    }
}
