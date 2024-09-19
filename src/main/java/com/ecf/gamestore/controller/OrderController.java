package com.ecf.gamestore.controller;

import com.ecf.gamestore.dto.HomePageDataDTO;
import com.ecf.gamestore.dto.OrderDTO;
import com.ecf.gamestore.dto.OrderRequest;
import com.ecf.gamestore.dto.OrderResponse;
import com.ecf.gamestore.mapper.HomePageDataMapper;
import com.ecf.gamestore.mapper.OrderMapper;
import com.ecf.gamestore.models.HomePageData;
import com.ecf.gamestore.models.Order;
import com.ecf.gamestore.models.OrderLine;
import com.ecf.gamestore.service.GSUserService;
import com.ecf.gamestore.service.OrderService;
import com.ecf.gamestore.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
@RequestMapping("api/order")
public class OrderController {
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);

    private OrderService orderService;
    private GSUserService gsUserService;

    @Autowired
    public OrderController(OrderService orderService,
                           @Lazy GSUserService gsUserService) {
        this.orderService = orderService;
        this.gsUserService = gsUserService;
    }

    @PostMapping(path="/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderResponse> register (@RequestBody OrderRequest orderRequest) {
        LOG.debug("## register (@RequestBody OrderDTO orderDTO)");
        OrderResponse response = new OrderResponse();
        try {
            LOG.debug(orderRequest.toString());
            response = this.orderService.register(orderRequest, response);
        }catch (Exception e) {
            LOG.error(e.toString());
            response.addMessage("Un problème est survenu, veuillez réessayer ultérieurement");
        } catch (Throwable e) {
            LOG.error(e.toString());
            response.addMessage("Un problème est survenu, veuillez réessayer ultérieurement");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping(path="/created-at/{dateIso}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderDTO>> getOrdersByCreatedAt(@PathVariable String dateIso) {
        LOG.debug("## getOrdersByCreatedAt(LocalDate datePickup)");
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
            LocalDateTime createdAt = LocalDateTime.parse(dateIso, formatter);
            List<Order> orders = this.orderService.getOrdersByCreatedAt(createdAt);
            if(!CollectionUtils.isNullOrEmpty(orders)) {
                return ResponseEntity.ok(OrderMapper.toDTOs(orders));
            }

        } catch (Exception e) {
            LOG.error(e.toString());
        }
        return ResponseEntity.ok(List.of());
    }

    @GetMapping(path="/user/{userUuid}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<OrderDTO>> getOrdersByUserUuid(@PathVariable String userUuid) {
        LOG.debug("## getOrdersByCreatedAt(String userUuid)");
        try {
            List<Order> orders = this.orderService.getOrdersByUserUuid(userUuid);
            if(!CollectionUtils.isNullOrEmpty(orders)) {
                return ResponseEntity.ok(OrderMapper.toDTOs(orders));
            }

        } catch (Exception e) {
            LOG.error(e.toString());
        }
        return ResponseEntity.ok(List.of());
    }
}
