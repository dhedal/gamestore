package com.ecf.gamestore.dto;

import java.util.List;

public class ValidationOrderResponse {

    private String order;
    private boolean ok;
    private boolean emailSent;

    private List<String> errors = List.of();

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public boolean isEmailSent() {
        return emailSent;
    }

    public void setEmailSent(boolean emailSent) {
        this.emailSent = emailSent;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public void addMessage(String message){
        this.errors.add(message);
    }
}
