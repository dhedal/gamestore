package com.ecf.gamestore.dto;

import com.ecf.gamestore.models.Order;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class OrderSearchResponse {

    private boolean ok;
    private List<OrderDTO> orders;
    private List<String> messages = new ArrayList<>();

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }

    public List<OrderDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDTO> orders) {
        this.orders = orders;
    }

    public List<String> getMessages() {
        return messages;
    }

    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public void addMessage(String message){
        if(StringUtils.hasText(message)) this.messages.add(message);
    }
}
