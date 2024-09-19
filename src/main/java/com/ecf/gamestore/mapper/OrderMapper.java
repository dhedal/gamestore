package com.ecf.gamestore.mapper;

import com.ecf.gamestore.dto.OrderDTO;
import com.ecf.gamestore.models.Order;
import com.ecf.gamestore.utils.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO toDTO(Order order) {
        if(Objects.isNull(order)) return null;
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setUuid(order.getUuid());
        orderDTO.setPickupDate(order.getPickupDate());
        orderDTO.setStatus(order.getStatus());
        orderDTO.setUser(GSUserMapper.toDTO(order.getUser()));
        orderDTO.setAgence(AgenceMapper.toDTO(order.getAgence()));
        orderDTO.setCreatedAt(order.getCreatedAt());
        orderDTO.setOrderLines(OrderLineMapper.toDTOs(order.getOrderLines()));
        return orderDTO;
    }

    public static List<OrderDTO> toDTOs(List<Order> orders) {
        if(CollectionUtils.isNullOrEmpty(orders)) return List.of();
        return orders.stream()
                .map(OrderMapper::toDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
