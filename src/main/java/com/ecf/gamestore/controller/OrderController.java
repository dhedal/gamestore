package com.ecf.gamestore.controller;

import com.ecf.gamestore.dto.GameArticleDTO;
import com.ecf.gamestore.dto.GameFilterDTO;
import com.ecf.gamestore.dto.OrderDTO;
import com.ecf.gamestore.dto.ValidationOrderResponse;
import com.ecf.gamestore.mapper.GameArticleMapper;
import com.ecf.gamestore.models.GSUser;
import com.ecf.gamestore.models.GameArticle;
import com.ecf.gamestore.models.Order;
import com.ecf.gamestore.models.Promotion;
import com.ecf.gamestore.models.enumerations.GameGenre;
import com.ecf.gamestore.models.enumerations.Platform;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;
import java.util.Objects;


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
    public ResponseEntity<ValidationOrderResponse> register (@RequestBody OrderDTO orderDTO) {
        LOG.debug("## register (@RequestBody OrderDTO orderDTO)");
        ValidationOrderResponse response = new ValidationOrderResponse();
        try {
            LOG.debug(orderDTO.toString());
            response = this.orderService.register(response, orderDTO);
        }catch (Exception e) {
            LOG.error(e.toString());
        } catch (Throwable e) {
            LOG.error(e.toString());
        }
        return ResponseEntity.ok(response);
    }
}
