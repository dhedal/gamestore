package com.ecf.gamestore.service;

import com.ecf.gamestore.dto.OrderDTO;
import com.ecf.gamestore.models.*;
import com.ecf.gamestore.models.enumerations.OrderStatus;
import com.ecf.gamestore.repository.OrderRepository;
import com.ecf.gamestore.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderService extends AbstractService<OrderRepository, Order>{
    private static final Logger LOG = LoggerFactory.getLogger(OrderService.class);

    private OrderLineService orderLineService;
    private GameArticleService gameArticleService;
    private PromotionService promotionService;
    private AgenceService agenceService;
    private GSUserService gsUserService;
    private MailService mailService;

    @Autowired
    public OrderService(
            OrderRepository repository,
            @Lazy OrderLineService orderLineService,
            @Lazy GameArticleService gameArticleService,
            @Lazy PromotionService promotionService,
            @Lazy AgenceService agenceService,
            @Lazy GSUserService gsUserService,
            @Lazy MailService mailService){
        super(repository);
        this.orderLineService = orderLineService;
        this.gameArticleService = gameArticleService;
        this.promotionService = promotionService;
        this.agenceService = agenceService;
        this.gsUserService = gsUserService;
        this.mailService = mailService;
    }

    public Order register(OrderDTO orderDTO) throws Throwable {
        if(Objects.isNull(orderDTO) || !orderDTO.isValid()) return null;

        GSUser user = this.gsUserService.findById(Long.valueOf(1L));
        if(Objects.isNull(user)) return null;

        Agence agence = this.agenceService.findByUuid(orderDTO.getAgence());
        if(Objects.isNull(agence)) return null;

        List<GameArticle> gameArticles = this.gameArticleService.getGameArticles(orderDTO.getUuids());
        if(CollectionUtils.isNullOrEmpty(gameArticles)) return null;

        List<String> uuidsList = gameArticles.stream()
                .filter(gameArticle -> gameArticle.getStock().intValue() <= 0)
                .map(GameArticle::getUuid)
                .collect(Collectors.toList());
        if(!CollectionUtils.isNullOrEmpty(uuidsList))  return null;

        List<Promotion> promotions = this.promotionService.getPromotions(gameArticles);
        Map<Long, Promotion> promotionMap = new HashMap<>();
        if(!CollectionUtils.isNullOrEmpty(promotions)) {
            promotions.forEach(promotion -> promotionMap.put(promotion.getGameArticle().getId(), promotion));
        }

        final Order order = new Order();
        order.setUser(user);
        order.setAgence(agence);
        order.setPickupDate(orderDTO.getDate());
        order.setStatus(OrderStatus.VALIDATED);
        this.save(order);

        List<OrderLine> orderLines = gameArticles.stream()
                .map(gameArticle -> {
                    OrderLine orderLine = new OrderLine();
                    orderLine.setUuid(UUID.randomUUID().toString());
                    orderLine.setCreatedAt(LocalDateTime.now());
                    orderLine.setOrder(order);
                    orderLine.setGameArticle(gameArticle);
                    orderLine.setQuantity(orderDTO.getArticles().get(gameArticle.getUuid()));
                    if(promotionMap.containsKey(gameArticle.getId())){
                        orderLine.setPromotion(promotionMap.get(gameArticle.getId()));
                    }
                    return orderLine;
                })
                .collect(Collectors.toList());
        order.setOrderLines(orderLines);
        Order newOrder = this.save(order);

        this.updateStock(newOrder);
        this.mailService.sendOrderConfirmationEmail(newOrder);

        return newOrder;
    }

    private void updateStock(Order order) {
        if(Objects.isNull(order)) return;
        List<OrderLine> orderLines = order.getOrderLines();
        if(CollectionUtils.isNullOrEmpty(orderLines)) return;

        for(OrderLine orderLine: orderLines) {
            try {
                GameArticle gameArticle = orderLine.getGameArticle();
                int stock = gameArticle.getStock().intValue() - orderLine.getQuantity();
                gameArticle.setStock(Integer.valueOf(stock));
                this.gameArticleService.save(gameArticle);
            } catch (Exception e) {
                LOG.error(e.toString());
            }
        }
    }
}
