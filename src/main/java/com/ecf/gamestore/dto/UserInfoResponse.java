package com.ecf.gamestore.dto;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class UserInfoResponse {

    private boolean ok = false;
    private List<String> messages = new ArrayList<>();
    private GSUserDTO user;

    public boolean isOk() {
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

    public GSUserDTO getUser() {
        return user;
    }

    public void setUser(GSUserDTO user) {
        this.user = user;
    }

    public void addMessage(String message) {
        if(StringUtils.hasText(message)) this.messages.add(message);
    }
}
