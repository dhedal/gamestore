package com.ecf.gamestore.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

public class SignupRequest {
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 3, max = 50, message = "le prénom doit avoir entre 3 et 50 charactères")
    private String firstName;
    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 3, max = 50, message = "le nom doit avoir entre 3 et 50 charactères")
    private String lastName;
    @NotBlank(message = "L'email' est obligatoire")
    @Email( message="le format d'email est invalide. ex : xxxx@xxx.xxx")
    private String email;
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit avoir au moins 8 caractères.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z]).+$", message = "Le mot de passe doit contenir à la fois des majuscules et des minuscules.")
    @Pattern(regexp = ".*\\d.*", message = "Le mot de passe doit contenir au moins un chiffre")
    @Pattern(regexp = ".*[!@#$%&*?:+-].*", message = "Le mot de passe doit contenir au moins un caractère spécial")
    private String password;
    private Integer role;
    @NotBlank(message = "L'adresse est obligatoire")
    @Size(max = 100, message = "l'adresse ne doit contenir pas plus de 100 charactères")
    private String streetAddress;
    @NotBlank(message = "Le code postal est obligatoire")
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Le code postal doit comporter 5 chiffres ou être au format 5+4 (ex: 12345 ou 12345-6789).")
    private String zipCode;

    @NotBlank(message = "La ville est obligatoire")
    private String city;
    @NotBlank(message = "Le pays est obligatoire")
    private String country;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

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

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getRole() {
        return Objects.isNull(role) ? Integer.valueOf(0) : this.role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }
}
