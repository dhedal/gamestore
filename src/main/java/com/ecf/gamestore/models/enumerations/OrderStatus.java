package com.ecf.gamestore.models.enumerations;

import com.ecf.gamestore.util.CommandStatusDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.stream.Stream;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@JsonDeserialize(using = CommandStatusDeserializer.class)
public enum OrderStatus {
    UNDEFINED(0, "indéfinie"),
    VALIDATED(1, "validé"),
    DELIVERED(2, "livré");


    private Integer key;
    private String label;

    OrderStatus(Integer key, String label){
        this.key = key;
        this.label = label;
    }

    public Integer getKey() {
        return key;
    }

    public String getLabel() {
        return label;
    }

    public static OrderStatus getByKey(int key) {
        return Stream.of(OrderStatus.values())
                .filter(orderStatus -> orderStatus.key.intValue() == key)
                .findFirst().orElse(OrderStatus.UNDEFINED);
    }
}
