package com.ecf.gamestore.controller;

import com.ecf.gamestore.dto.OrderRequest;
import com.ecf.gamestore.dto.OrderResponse;
import com.ecf.gamestore.service.GSUserService;
import com.ecf.gamestore.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


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
}
