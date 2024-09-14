package com.ecf.gamestore.service;

import com.ecf.gamestore.dto.OrderDTO;
import com.ecf.gamestore.dto.ValidationOrderResponse;
import com.ecf.gamestore.models.*;
import com.ecf.gamestore.models.enumerations.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Locale;
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
    @Autowired
    private GameArticleService gameArticleService;

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
            int stock = gameArticle.getStock().intValue();

            int count = 1;

            OrderDTO orderDTO = new OrderDTO();
            Map<String, Integer> articleOrderMap = new HashMap<>();
            articleOrderMap.put(gameArticle.getUuid(), Integer.valueOf(count));
            orderDTO.setArticles(articleOrderMap);
            orderDTO.setDate(LocalDate.now());
            orderDTO.setAgence(agence.getUuid());
            ValidationOrderResponse response = new ValidationOrderResponse();
            response = this.orderService.register(response, orderDTO);

            assertTrue(response.isOk());
            assertTrue(response.isEmailSent());

            Order order = this.orderService.findByUuid(response.getOrder());
            assertNotNull(order);
            assertNotNull(order.getId());
            assertNotNull(order.getUuid());
            assertNotNull(order.getCreatedAt());

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

            GameArticle article = this.gameArticleService.findById(gameArticle.getId());
            assertNotNull(article);
            assertTrue(stock - count == article.getStock().intValue());

        } catch (Throwable e) {
            e.printStackTrace();
            fail();
        }
    }
}
