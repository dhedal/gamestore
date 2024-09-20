package com.ecf.gamestore.controller;

import com.ecf.gamestore.dto.*;
import com.ecf.gamestore.service.PasswordResetTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/reset-password")
public class RestPasswordController {

    private static final Logger LOG = LoggerFactory.getLogger(RestPasswordController.class);
    private final PasswordResetTokenService passwordResetTokenService;

    @Autowired
    public RestPasswordController(
            PasswordResetTokenService passwordResetTokenService) {
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @GetMapping(value = "/check-token/{token}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> checkToken(@PathVariable String token) {
        LOG.debug("## checkToken(String token)");
        LOG.debug(token);
        Boolean response = false;
        try {
            return ResponseEntity.ok(Boolean.valueOf(this.passwordResetTokenService.isTokenValid(token)));
        } catch(Exception ex) {
            LOG.error(ex.toString());
        }

        return ResponseEntity.ok(Boolean.FALSE);

    }

    @PostMapping(value = "/reset", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResetPasswordResponse> resetPassword(@RequestBody ResetPasswordRequest request) {
        LOG.debug("## resetPassword (ResetPasswordRequest request");
        ResetPasswordResponse response = new ResetPasswordResponse();
        try {
            response = this.passwordResetTokenService.resetPassword(request, response);
        } catch (Exception ex) {
            LOG.error(ex.toString());
            response.addMessage("Un problème serveur est survenu, veuillez réessayer ultérieurement");
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/create-token", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ForgotPasswordResponse> createToken(@RequestBody ForgotPasswordRequest request) {
        LOG.debug("## createToken (ForgotPasswordRequest request");
        ForgotPasswordResponse response = new ForgotPasswordResponse();
        try {
            response = this.passwordResetTokenService.createToken(request, response);
        } catch (Exception ex) {
            LOG.error(ex.toString());
            response.addMessage("Un problème serveur est survenu, veuillez réessayer ultérieurement");
        }
        return ResponseEntity.ok(response);
    }
}
