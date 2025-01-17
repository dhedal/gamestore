package com.ecf.gamestore.service;

import com.ecf.gamestore.dto.*;
import com.ecf.gamestore.mapper.OrderMapper;
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

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.LocalDate;
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
    private Validator validator;

    @Autowired
    public OrderService(
            OrderRepository repository,
            @Lazy OrderLineService orderLineService,
            @Lazy GameArticleService gameArticleService,
            @Lazy PromotionService promotionService,
            @Lazy AgenceService agenceService,
            @Lazy GSUserService gsUserService,
            @Lazy MailService mailService,
            @Lazy Validator validator){
        super(repository);
        this.orderLineService = orderLineService;
        this.gameArticleService = gameArticleService;
        this.promotionService = promotionService;
        this.agenceService = agenceService;
        this.gsUserService = gsUserService;
        this.mailService = mailService;
        this.validator = validator;
    }

    public OrderResponse register(OrderRequest request, OrderResponse response){
        LOG.debug("## register(OrderRequest request, OrderResponse response)");

        if(Objects.isNull(request)) {
            throw new IllegalArgumentException("OrderRequest ne doit pas être null");
        }

        if(Objects.isNull(response)) {
            throw new IllegalArgumentException("OrderResponse ne doit pas être null");
        }

        Set<ConstraintViolation<OrderRequest>> violations = this.validator.validate(request);
        if(!violations.isEmpty()) {
            for(ConstraintViolation<OrderRequest> violation : violations) {
                response.addMessage(violation.getMessage());
            }
            return response;
        }

        GSUser user = this.gsUserService.getByUuid(request.getUser());
        if(Objects.isNull(user)){
            response.addMessage("l'utilisateur est introuvable");
            return response;
        }

        Agence agence = this.agenceService.findByUuid(request.getAgence());
        if(Objects.isNull(agence)){
            response.addMessage("l'agence est introuvable");
            return response;
        }

        List<GameArticle> gameArticles = this.gameArticleService.getGameArticles(request.getUuids());
        if(CollectionUtils.isNullOrEmpty(gameArticles)){
            response.addMessage("les articles sont introuvables");
            return response;
        }

        List<String> uuidsList = new ArrayList<>();
        for(GameArticle article : gameArticles) {
            if(article.getStock().intValue() <= 0) {
                response.addMessage("un des articles est en rupture de stock, veuillez rafraîchir la page pour afficher le stock à jour");
                return response;
            }

            int quantity = request.getArticles().get(article.getUuid());
            if(article.getStock().intValue() < quantity) {
                response.addMessage("La quantité d'un des articles est supérieur au stock, veuillez rafraîchir la page pour afficher le stock à jour");
                return response;
            }
            uuidsList.add(article.getUuid());
        }



        List<Promotion> promotions = this.promotionService.getPromotions(gameArticles);
        Map<Long, Promotion> promotionMap = new HashMap<>();
        if(!CollectionUtils.isNullOrEmpty(promotions)) {
            promotions.forEach(promotion -> promotionMap.put(promotion.getGameArticle().getId(), promotion));
        }

        final Order order = new Order();
        order.setUser(user);
        order.setAgence(agence);
        order.setPickupDate(request.getDate());
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
                    orderLine.setQuantity(request.getArticles().get(gameArticle.getUuid()));
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

    public List<Order> getOrdersByCreatedAt(LocalDateTime createdAt) {
        LOG.debug("## getOrdersByCreatedAt(LocalDate createdAt)");
        return this.repository.findOrdersByCreatedAtGreaterThanEqual(createdAt);
    }

    public List<Order> getOrdersByUserUuid(String userUuid) {
        LOG.debug("## getOrdersByUserUuid(String userUuid)");
        if(!StringUtils.hasText(userUuid)) return null;
        GSUser user = this.gsUserService.getByUuid(userUuid);
        if(Objects.isNull(user)) return null;

        return this.repository.findOrdersByUser(user);
    }

    public OrderSearchResponse searchOrders(OrderSearchRequest request, OrderSearchResponse response) {
        LOG.debug("## searchOrders(OrderSearchRequest request, OrderSearchResponse response)");

        if(Objects.isNull(request)) {
            throw new IllegalArgumentException("OrderSearchRequest ne doit pas être null");
        }

        if(Objects.isNull(response)) {
            throw new IllegalArgumentException("OrderSearchResponse ne doit pas être null");
        }

        Set<ConstraintViolation<OrderSearchRequest>> violations = this.validator.validate(request);
        if(!violations.isEmpty()) {
            for(ConstraintViolation<OrderSearchRequest> violation : violations) {
                response.addMessage(violation.getMessage());
            }
            return response;
        }

        GSUser user = this.gsUserService.getByEmail(request.getEmail());
        if(Objects.isNull(user)) {
            response.addMessage("Cet email n'existe pas");
            return response;
        }

        List<Order> orders = this.repository.findOrdersByUserAndStatusOrderByPickupDateDesc(user, request.getStatus());
        if(CollectionUtils.isNullOrEmpty(orders)) response.setOrders(List.of());
        else response.setOrders(OrderMapper.toDTOs(orders));

        response.setOk(true);
        return response;
    }

    public OrderSearchResponse changeStatusToDelivered(String orderUuid, OrderSearchResponse response) {
        LOG.debug("## searchOrders(OrderSearchRequest request, OrderSearchResponse response)");

        if(!StringUtils.hasText(orderUuid)) {
            throw new IllegalArgumentException("orderUuid ne doit pas être null ou empty");
        }

        if(Objects.isNull(response)) {
            throw new IllegalArgumentException("OrderSearchResponse ne doit pas être null");
        }

        Order order = this.findByUuid(orderUuid);
        if(Objects.isNull(order)) {
            response.addMessage("La commande est introuvable");
            return response;
        }

        order.setStatus(OrderStatus.DELIVERED);
        this.save(order);

        OrderSearchRequest request = new OrderSearchRequest();
        request.setStatus(OrderStatus.VALIDATED);
        request.setEmail(order.getUser().getEmail());

        return this.searchOrders(request, response);
    }
}
