package com.ecf.gamestore.dto;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ForgotPasswordResponse {

    private boolean ok;
    private boolean emailSent;
    private List<String> messages = new ArrayList<>();

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

    public void addMessage(String message) {
        if(StringUtils.hasText(message)) this.messages.add(message);
    }
}
