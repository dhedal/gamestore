package com.ecf.gamestore.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ChangePasswordRequest extends SigninRequest{

    @NotBlank(message = "L'ancien mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit avoir au moins 8 caractères.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).+$", message = "Le mot de passe doit contenir à la fois des majuscules et des minuscules.")
    @Pattern(regexp = ".*\\d.*", message = "Le mot de passe doit contenir au moins un chiffre")
    @Pattern(regexp = ".*[!@#$%&*?:+-].*", message = "Le mot de passe doit contenir au moins un caractère spécial")
    private String passwordOld;

    public ChangePasswordRequest() {
        super();
    }

    public String getPasswordOld() {
        return passwordOld;
    }

    public void setPasswordOld(String passwordOld) {
        this.passwordOld = passwordOld;
    }
}
