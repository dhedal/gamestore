package com.ecf.gamestore.dto;

import java.util.ArrayList;
import java.util.List;

public class OrderResponse {

    private String order;
    private boolean ok;
    private boolean emailSent;

    private List<String> messages = new ArrayList<>();

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

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String message){
        this.messages.add(message);
    }
}
