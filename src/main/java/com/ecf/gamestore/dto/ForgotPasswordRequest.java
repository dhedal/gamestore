package com.ecf.gamestore.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class ForgotPasswordRequest {

    @NotBlank(message = "L'email' est obligatoire")
    @Email( regexp = ".+[@].+[\\.].+", message="le format d'email est invalide. ex : xxxx@xxx.xxx")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
