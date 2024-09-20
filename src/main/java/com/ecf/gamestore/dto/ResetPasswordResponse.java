package com.ecf.gamestore.dto;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class ResetPasswordResponse {

    private boolean ok;

    private List<String> messages = new ArrayList<>();

    public boolean getOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
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
