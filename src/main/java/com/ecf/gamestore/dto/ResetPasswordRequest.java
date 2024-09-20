package com.ecf.gamestore.dto;

import com.ecf.gamestore.validation.constraint.ValidUUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ResetPasswordRequest {

    @NotBlank(message = "Le token est obligatoire")
    @ValidUUID(message = "Le token doit être valide")
    private String token;
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit avoir au moins 8 caractères.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).+$", message = "Le mot de passe doit contenir à la fois des majuscules et des minuscules.")
    @Pattern(regexp = ".*\\d.*", message = "Le mot de passe doit contenir au moins un chiffre")
    @Pattern(regexp = ".*[!@#$%&*?:+-].*", message = "Le mot de passe doit contenir au moins un caractère spécial")
    private String password;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
