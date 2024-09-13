package com.ecf.gamestore.service;

import com.ecf.gamestore.dto.OrderDTO;
import com.ecf.gamestore.models.*;
import com.ecf.gamestore.models.enumerations.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private GSUserService gsUserService;
    @Autowired
    private AgenceService agenceService;

    @Test
    public void register_test() {
        try {
            GSUser user = this.gsUserService.findById(Long.valueOf(1));
            assertNotNull(user);

            Agence agence = this.agenceService.findById(Long.valueOf(1));
            assertNotNull(agence);

            Promotion promotion = this.promotionService.findById(Long.valueOf(3));
            assertNotNull(promotion);

            GameArticle gameArticle = promotion.getGameArticle();
            assertNotNull(gameArticle);

            Integer count = 1;

            OrderDTO orderDTO = new OrderDTO();
            Map<String, Integer> articleOrderMap = new HashMap<>();
            articleOrderMap.put(gameArticle.getUuid(), count);
            orderDTO.setArticles(articleOrderMap);
            orderDTO.setDate(LocalDate.now());
            orderDTO.setAgence(agence.getUuid());

            Order order = this.orderService.register(orderDTO);
            assertNotNull(order);

            assertEquals(order.getPickupDate(), orderDTO.getDate());
            assertEquals(order.getUser().getId(), user.getId());
            assertEquals(order.getAgence().getId(), agence.getId());
            assertEquals(order.getStatus(), OrderStatus.VALIDATED);
            assertNotNull(order.getOrderLines());
            assertTrue(order.getOrderLines().size() == orderDTO.getArticles().size());

            OrderLine orderLine = order.getOrderLines().get(0);
            assertEquals(orderLine.getOrder().getId(), order.getId());
            assertEquals(orderLine.getQuantity(), articleOrderMap.get(orderLine.getGameArticle().getUuid()));
            assertEquals(orderLine.getPromotion().getId(), promotion.getId());

        } catch (Throwable e) {
            e.printStackTrace();
            fail();
        }
    }
}
