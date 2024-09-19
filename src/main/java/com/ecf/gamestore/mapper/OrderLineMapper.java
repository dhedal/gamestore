package com.ecf.gamestore.mapper;

import com.ecf.gamestore.dto.OrderLineDTO;
import com.ecf.gamestore.models.OrderLine;
import com.ecf.gamestore.utils.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class OrderLineMapper {

    public static OrderLineDTO toDTO(OrderLine orderLine) {
        if(Objects.isNull(orderLine)) return null;
        OrderLineDTO orderLineDTO = new OrderLineDTO();
        orderLineDTO.setUuid(orderLine.getUuid());
        orderLineDTO.setQuantity(orderLine.getQuantity());
        orderLineDTO.setOrderUuid(orderLine.getOrder().getUuid());
        orderLineDTO.setGameArticle(GameArticleMapper.toDTO(orderLine.getGameArticle()));
        orderLineDTO.setPromotion(PromotionMapper.toDTO(orderLine.getPromotion()));

        return orderLineDTO;
    }

    public static List<OrderLineDTO> toDTOs(List<OrderLine> orderLines) {
        if(CollectionUtils.isNullOrEmpty(orderLines)) return List.of();
        return orderLines.stream()
                .map(OrderLineMapper::toDTO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

    }
}
