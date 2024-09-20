package com.ecf.gamestore.dto;

import com.ecf.gamestore.models.enumerations.OrderStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class OrderSearchRequest {

    @NotBlank(message = "L'email' est obligatoire")
    @Email( regexp = ".+[@].+[\\.].+", message="le format d'email est invalide. ex : xxxx@xxx.xxx")
    private String email;

    @NotNull(message = "Le status de la commande doit être indiqué")
    private OrderStatus status;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
