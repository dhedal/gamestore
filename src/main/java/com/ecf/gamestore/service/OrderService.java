package com.ecf.gamestore.service;

import com.ecf.gamestore.dto.OrderDTO;
import com.ecf.gamestore.dto.ValidationOrderResponse;
import com.ecf.gamestore.models.*;
import com.ecf.gamestore.models.enumerations.OrderStatus;
import com.ecf.gamestore.repository.OrderRepository;
import com.ecf.gamestore.utils.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public ValidationOrderResponse register(ValidationOrderResponse response, OrderDTO orderDTO) throws Throwable {
        LOG.debug("## register(ValidationOrderResponse response, OrderDTO orderDTO)");
        if(Objects.isNull(orderDTO) || !orderDTO.isValid()) {
            response.addMessage("orderDto non valide");
            return response;
        };

        GSUser user = this.gsUserService.findById(Long.valueOf(1L));
        if(Objects.isNull(user)){
            response.addMessage("l'utilisateur est introuvable");
            return response;
        }

        Agence agence = this.agenceService.findByUuid(orderDTO.getAgence());
        if(Objects.isNull(agence)){
            response.addMessage("l'agence est introuvable");
            return response;
        }

        List<GameArticle> gameArticles = this.gameArticleService.getGameArticles(orderDTO.getUuids());
        if(CollectionUtils.isNullOrEmpty(gameArticles)){
            response.addMessage("les articles sont introuvables");
            return response;
        }

        List<String> uuidsList = gameArticles.stream()
                .filter(gameArticle -> gameArticle.getStock().intValue() <= 0)
                .map(GameArticle::getUuid)
                .collect(Collectors.toList());
        if(!CollectionUtils.isNullOrEmpty(uuidsList)){
            response.addMessage("un des articles est en rupture de stock, veuillez rafraîchir la page pour afficher le stock à jour");
            return response;
        }

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
        response.setOrder(order.getUuid());

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


        response.setOk(true);
        response.setEmailSent(this.mailService.sendOrderConfirmationEmail(newOrder));
        return response;
    }

    private void updateStock(Order order) {
        LOG.debug("## updateStock(Order order)");
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

    public Order findByUuid(String uuid) {
        LOG.debug("## findByUuid(String uuid)");
        if(!StringUtils.hasText(uuid)) return null;
        return this.repository.findByUuid(uuid);
    }
}
