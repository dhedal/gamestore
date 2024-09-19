package com.ecf.gamestore.dto;

import com.ecf.gamestore.validation.constraint.ValidUUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserInfoRequest {
    @NotBlank(message = "L'uuid est obligatoire")
    @ValidUUID(message = "L'uuid doit être valide")
    private String uuid;
    @NotBlank(message = "Le prénom est obligatoire")
    @Size(min = 3, max = 50, message = "le prénom doit avoir entre 3 et 50 charactères")
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(min = 3, max = 50, message = "le nom doit avoir entre 3 et 50 charactères")
    private String lastName;
    @NotBlank(message = "L'adresse est obligatoire")
    @Size(min=3, max = 100, message = "l'adresse ne doit contenir pas plus de 100 charactères")
    private String streetAddress;
    @NotBlank(message = "Le code postal est obligatoire")
    @Pattern(regexp = "^\\d{5}(-\\d{4})?$", message = "Le code postal doit comporter 5 chiffres ou être au format 5+4 (ex: 12345 ou 12345-6789).")
    private String zipCode;
    @NotBlank(message = "La ville est obligatoire")
    private String city;
    @NotBlank(message = "Le pays est obligatoire")
    private String country;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

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
}
