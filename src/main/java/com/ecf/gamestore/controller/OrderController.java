package com.ecf.gamestore.controller;

import com.ecf.gamestore.dto.GameArticleDTO;
import com.ecf.gamestore.dto.GameFilterDTO;
import com.ecf.gamestore.dto.OrderDTO;
import com.ecf.gamestore.mapper.GameArticleMapper;
import com.ecf.gamestore.models.GameArticle;
import com.ecf.gamestore.models.Promotion;
import com.ecf.gamestore.models.enumerations.GameGenre;
import com.ecf.gamestore.models.enumerations.Platform;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;


@Controller
@RequestMapping("api/order")
public class OrderController {
    private static final Logger LOG = LoggerFactory.getLogger(OrderController.class);


    @PostMapping(path="/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OrderDTO> register (@RequestBody OrderDTO orderDTO) {
        LOG.debug("## register (@RequestBody OrderDTO orderDTO)");
        try {
            LOG.debug(orderDTO.toString());

        }catch (Exception e) {
            LOG.error(e.toString());
        }
        return ResponseEntity.ok(orderDTO);
    }
}
