package com.ecf.gamestore.service;

import com.ecf.gamestore.dto.*;
import com.ecf.gamestore.models.GSUser;
import com.ecf.gamestore.models.PasswordResetToken;
import com.ecf.gamestore.repository.PasswordResetTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Service
public class PasswordResetTokenService extends AbstractService<PasswordResetTokenRepository, PasswordResetToken>{

    private static final Logger LOG = LoggerFactory.getLogger(PasswordResetTokenService.class);
    @Value("${password.reset.token.expiration.hours}")
    private long TOKEN_EXPIRATION_HOURS;

    private final Validator validator;
    private PasswordEncoder passwordEncoder;
    private final GSUserService gsUserService;
    private final MailService mailService;

    @Autowired
    public PasswordResetTokenService(
            PasswordResetTokenRepository repository,
            @Lazy GSUserService gsUserService,
            @Lazy MailService mailService,
            @Lazy Validator validator,
            @Lazy PasswordEncoder passwordEncoder
            ) {
        super(repository);
        this.gsUserService = gsUserService;
        this.mailService = mailService;
        this.repository = repository;
        this.validator = validator;
        this.passwordEncoder = passwordEncoder;
    }
    public boolean isTokenValid(String token) {
        LOG.debug("## isTokenValid: " + TOKEN_EXPIRATION_HOURS);
        if(Objects.isNull(token) || token.isEmpty()) return false;

        PasswordResetToken passwordResetToken = this.repository.findByToken(token).orElse(null);

        if(Objects.isNull(passwordResetToken)) return false;

        if(passwordResetToken.getExpiryDate().isAfter(LocalDateTime.now())) return true;

        this.repository.delete(passwordResetToken);
        return false;
    }


    public ResetPasswordResponse resetPassword(ResetPasswordRequest request, ResetPasswordResponse response) {
        LOG.debug("## resetPassword(ResetPasswordRequest request, ResetPasswordResponse response)");

        if(Objects.isNull(request)){
            throw new IllegalArgumentException("ResetPasswordRequest ne doit pas être null");
        }
        if(Objects.isNull(response)){
            throw new IllegalArgumentException("ResetPasswordResponse ne doit pas être null");
        }

        Set<ConstraintViolation<ResetPasswordRequest>> violations = this.validator.validate(request);
        if(!violations.isEmpty()) {
            for(ConstraintViolation<ResetPasswordRequest> violation : violations) {
                response.addMessage(violation.getMessage());
            }
            return response;
        }

        PasswordResetToken passwordResetToken = this.repository.findByToken(request.getToken()).orElse(null);
        if(Objects.isNull(passwordResetToken)) {
            response.addMessage("Le token est introuvable, veuillez passer par l'étape: j'ai oublié mon mot de passe");
            return response;
        }

        GSUser user = passwordResetToken.getUser();
        if(Objects.isNull(user)) {
            response.addMessage("L'utilisateur est introuvable.");
            return response;
        }

        user.setPassword(this.passwordEncoder.encode(request.getPassword()));
        this.gsUserService.save(user);

        response.setOk(true);

        try {
            this.delete(passwordResetToken.getId());
        } catch (Throwable e) {
            LOG.error(e.toString());
        }

        return response;
    }

    public ForgotPasswordResponse createToken(ForgotPasswordRequest request, ForgotPasswordResponse response) {
        LOG.debug("## createToken(ForgotPasswordRequest request, ForgotPasswordResponse response)");

        if(Objects.isNull(request)){
            throw new IllegalArgumentException("ForgotPasswordRequest ne doit pas être null");
        }
        if(Objects.isNull(response)){
            throw new IllegalArgumentException("ForgotPasswordResponse ne doit pas être null");
        }

        Set<ConstraintViolation<ForgotPasswordRequest>> violations = this.validator.validate(request);
        if(!violations.isEmpty()) {
            for(ConstraintViolation<ForgotPasswordRequest> violation : violations) {
                response.addMessage(violation.getMessage());
            }
            return response;
        }

        GSUser user = this.gsUserService.getByEmail(request.getEmail());
        if(Objects.isNull(user)) {
            response.addMessage("Cet email n'existe pas");
            return response;
        }

        PasswordResetToken passwordResetToken = this.repository.findByUser(user);
        if(Objects.isNull(passwordResetToken)) {
            passwordResetToken = new PasswordResetToken();
            passwordResetToken.setUser(user);
        }
        passwordResetToken.setToken(UUID.randomUUID().toString());
        passwordResetToken.setExpiryDate(LocalDateTime.now().plusHours(TOKEN_EXPIRATION_HOURS));
        passwordResetToken = this.save(passwordResetToken);

        response.setEmailSent(this.mailService.sendResetPasswordLink(user.getEmail(), passwordResetToken.getToken()));

        response.setOk(true);
        return response;
    }
}
