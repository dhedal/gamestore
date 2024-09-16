package com.ecf.gamestore.dto;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SigninResponse {

    private GSUserDTO user;
    private String token;
    private long expiresIn;
    private List<String> messages = new ArrayList<>();

    public GSUserDTO getUser() {
        return user;
    }

    public void setUser(GSUserDTO user) {
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String message) {
        if(StringUtils.hasText(message))this.messages.add(message);
    }
}
