package com.ecf.gamestore.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SigninRequest {
//TODO: https://stackoverflow.com/questions/50535214/javax-validation-constraints-email-matching-invalid-email-address
    @NotBlank(message = "L'email' est obligatoire")
    @Email( regexp = ".+[@].+[\\.].+", message="le format d'email est invalide. ex : xxxx@xxx.xxx")
    private String email;
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit avoir au moins 8 caractères.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).+$", message = "Le mot de passe doit contenir à la fois des majuscules et des minuscules.")
    @Pattern(regexp = ".*\\d.*", message = "Le mot de passe doit contenir au moins un chiffre")
    @Pattern(regexp = ".*[!@#$%&*?:+-].*", message = "Le mot de passe doit contenir au moins un caractère spécial")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
