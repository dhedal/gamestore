package com.ecf.gamestore.converter;

import com.ecf.gamestore.models.enumerations.OrderStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Objects;

@Converter(autoApply = true)
public class CommandStatusAttributeConverter implements AttributeConverter<OrderStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(OrderStatus orderStatus) {
        return Objects.isNull(orderStatus) ? OrderStatus.UNDEFINED.getKey() : orderStatus.getKey();
    }

    @Override
    public OrderStatus convertToEntityAttribute(Integer key) {
        return Objects.isNull(key) ? OrderStatus.UNDEFINED : OrderStatus.getByKey(key.intValue());
    }
}
