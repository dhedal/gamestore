package com.ecf.gamestore.dto;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SignupResponse {

    private boolean ok = false;
    private List<String> messages = new ArrayList<>();
    private boolean emailSent;

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String message) {
        if(StringUtils.hasText(message)) this.messages.add(message);
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

    public void setEmailSent(boolean b) {
        this.emailSent = true;
    }
}
