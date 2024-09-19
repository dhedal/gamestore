package com.ecf.gamestore.service;

import com.ecf.gamestore.dto.*;
import com.ecf.gamestore.mapper.GSUserMapper;
import com.ecf.gamestore.models.GSUser;
import com.ecf.gamestore.models.embeddables.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.*;
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
    private JwtService jwtService;
    private AuthenticationManager authenticationManager;

    @Autowired
    public AuthenticationService(
            @Lazy GSUserService gsUserService,
            MailService mailService,
            AuthenticationManager authenticationManager,
            JwtService jwtService,
            @Lazy Validator validator,
            BCryptPasswordEncoder  passwordEncoder){
        this.gsUserService = gsUserService;
        this.mailService = mailService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
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

        try {
            this.authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            GSUser user = this.gsUserService.getByEmail(request.getEmail());
            if(Objects.nonNull(user)) {
                response.setUser(GSUserMapper.toDTO(user));
                response.setToken(this.jwtService.generateToken(user));
                response.setExpiresIn(this.jwtService.getJwtExpiration());
            }
        } catch (BadCredentialsException e) {
            response.addMessage("Échec de l'authentification : Mauvais identifiants");
            return response;
        } catch (LockedException e) {
            response.addMessage("Échec de l'authentification : Le compte est verrouillé");
            return response;
        } catch (DisabledException e) {
            response.addMessage("Échec de l'authentification : Le compte est désactivé");
            return response;
        } catch (AccountExpiredException e) {
            response.addMessage("Échec de l'authentification : Le compte a expiré");
            return response;
        } catch (CredentialsExpiredException e) {
            response.addMessage("Échec de l'authentification : Les informations d'identification ont expiré");
            return response;
        } catch (Exception e) {
            response.addMessage("Échec de l'authentification : " + e.getMessage());
            return response;
        }

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
