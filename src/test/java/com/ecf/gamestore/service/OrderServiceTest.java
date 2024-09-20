package com.ecf.gamestore.service;

import com.ecf.gamestore.dto.OrderRequest;
import com.ecf.gamestore.dto.OrderResponse;
import com.ecf.gamestore.models.*;
import com.ecf.gamestore.models.enumerations.OrderStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class OrderServiceTest {
    private static final Logger LOG = LoggerFactory.getLogger(OrderServiceTest.class);

    private OrderService orderService;
    private PromotionService promotionService;
    private GSUserService gsUserService;
    private AgenceService agenceService;
    private GameArticleService gameArticleService;

    private Agence agenceTest;
    private GSUser userTest;


    @BeforeEach
    public void setUp() {
        try {
            if(Objects.isNull(this.agenceTest)) this.agenceTest = this.agenceService.findById(Long.valueOf(1));
            if(Objects.isNull(this.userTest)) this.userTest = this.gsUserService.findById(Long.valueOf(1));

        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public OrderServiceTest(
            OrderService orderService,
            PromotionService promotionService,
            GSUserService gsUserService,
            AgenceService agenceService,
            GameArticleService gameArticleService) {
        this.orderService = orderService;
        this.promotionService = promotionService;
        this.gsUserService = gsUserService;
        this.agenceService = agenceService;
        this.gameArticleService = gameArticleService;
    }

    @Test
    public void test_register_NullResponse_ShouldThrowIllegalArgumentException() {
        OrderRequest request = new OrderRequest();
        OrderResponse response = null;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            this.orderService.register(request, response);
        });

        assertEquals("OrderResponse ne doit pas être null", exception.getMessage());
    }

    @Test
    public void test_register_NullRequest_ShouldThrowIllegalArgumentException() {
        OrderRequest request = null;
        OrderResponse response = new OrderResponse();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            this.orderService.register(request, response);
        });

        assertEquals("OrderRequest ne doit pas être null", exception.getMessage());
    }

    @Test
    public void test_register_ConstraintViolation() {
        OrderRequest request = this.createOrderRequest();
        List<String> tests = new ArrayList<>();
        Map<String,Integer> map = new HashMap<>();

        request.setArticles(map);
        tests.add("Les clés de la map doivent être des UUIDs valides et les valeurs doivent être des supérieurs à zéro");
        this.orderResponseTest(this.orderService.register(request, new OrderResponse()), tests);
        tests.clear();
        map.clear();


        map.put(UUID.randomUUID().toString(), 0);
        request.setArticles(map);
        tests.add("Les clés de la map doivent être des UUIDs valides et les valeurs doivent être des supérieurs à zéro");
        this.orderResponseTest(this.orderService.register(request, new OrderResponse()), tests);
        tests.clear();
        map.clear();

        map.put("qsdfjfqmfjlqf", 1);
        request.setArticles(map);
        tests.add("Les clés de la map doivent être des UUIDs valides et les valeurs doivent être des supérieurs à zéro");
        this.orderResponseTest(this.orderService.register(request, new OrderResponse()), tests);
        tests.clear();
        map.clear();

        request = this.createOrderRequest();
        request.setUser("");
        tests.add("L'user est obligatoire");
        tests.add("L'user doit être un UUID valide");
        this.orderResponseTest(this.orderService.register(request, new OrderResponse()), tests);
        tests.clear();

        request = this.createOrderRequest();
        request.setAgence("");
        tests.add("L'agence est obligatoire");
        tests.add("L'agence doit être un UUID valide");
        this.orderResponseTest(this.orderService.register(request, new OrderResponse()), tests);
        tests.clear();

        request.setAgence("qfsqff lff-ffqsfqoeff");
        tests.add("L'agence doit être un UUID valide");
        this.orderResponseTest(this.orderService.register(request, new OrderResponse()), tests);
        tests.clear();

        request = this.createOrderRequest();
        request.setDate(null);
        tests.add("La date de retrait est obligatoire");
        tests.add("La date doit être comprise entre aujourd'hui et 7 jours, et ne doit pas tomber un lundi ou un dimanche");
        this.orderResponseTest(this.orderService.register(request, new OrderResponse()), tests);
        tests.clear();

        request = this.createOrderRequest();
        request.setDate(this.nextDayOfWeek(DayOfWeek.MONDAY));
        tests.add("La date doit être comprise entre aujourd'hui et 7 jours, et ne doit pas tomber un lundi ou un dimanche");
        this.orderResponseTest(this.orderService.register(request, new OrderResponse()), tests);
        tests.clear();

        request = this.createOrderRequest();
        request.setDate(this.nextDayOfWeek(DayOfWeek.SUNDAY));
        tests.add("La date doit être comprise entre aujourd'hui et 7 jours, et ne doit pas tomber un lundi ou un dimanche");
        this.orderResponseTest(this.orderService.register(request, new OrderResponse()), tests);
        tests.clear();

        request = this.createOrderRequest();
        request.setDate(LocalDate.now().plusDays(8));
        tests.add("La date doit être comprise entre aujourd'hui et 7 jours, et ne doit pas tomber un lundi ou un dimanche");
        this.orderResponseTest(this.orderService.register(request, new OrderResponse()), tests);
        tests.clear();
    }

    @Test
    public void test_register_UserNotFound() {
        OrderRequest request = this.createOrderRequest();
        List<String> tests = new ArrayList<>();

        request.setUser(UUID.randomUUID().toString());
        tests.add("l'utilisateur est introuvable");
        this.orderResponseTest(this.orderService.register(request, new OrderResponse()), tests);
    }

    @Test
    public void test_register_AgenceNotFound() {
        OrderRequest request = this.createOrderRequest();
        List<String> tests = new ArrayList<>();

        request.setAgence(UUID.randomUUID().toString());
        tests.add("l'agence est introuvable");
        this.orderResponseTest(this.orderService.register(request, new OrderResponse()), tests);
    }

    @Test
    public void test_register_ArticlesNotFound() {
        OrderRequest request = this.createOrderRequest();
        List<String> tests = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        map.put(UUID.randomUUID().toString(), Integer.valueOf(2));
        tests.add("les articles sont introuvables");
        this.orderResponseTest(this.orderService.register(request, new OrderResponse()), tests);
    }

    @Test
    public void test_register_articlesOutOfStock() {
        OrderRequest request = this.createOrderRequest();
        List<String> tests = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();

        map.put("6b1f5614-b8a8-42ed-936b-7808aa92b931", Integer.valueOf(2));
        request.setArticles(map);
        tests.add("un des articles est en rupture de stock, veuillez rafraîchir la page pour afficher le stock à jour");
        this.orderResponseTest(this.orderService.register(request, new OrderResponse()), tests);
        tests.clear();
        map.clear();


        map.put("35afa23b-dc79-4a26-b86e-7748c1c3be2c", Integer.valueOf(20));
        request.setArticles(map);
        tests.add("La quantité d'un des articles est supérieur au stock, veuillez rafraîchir la page pour afficher le stock à jour");
        this.orderResponseTest(this.orderService.register(request, new OrderResponse()), tests);
        tests.clear();
        map.clear();
    }

    @Test
    public void test_register_success() {
        OrderRequest request = this.createOrderRequest();
        List<String> tests = new ArrayList<>();

        Map<String, Integer> map = new HashMap<>();
        map.put("3cf8f6cd-9d3c-4a81-8c92-f298985c07a7", Integer.valueOf(1));
        request.setArticles(map);

        List<GameArticle> gameArticles = this.gameArticleService.getGameArticles(map.keySet().stream().toList());
        Map<String,Integer> mapStock = new HashMap<>();
        for(GameArticle article : gameArticles) {
            mapStock.put(article.getUuid(), article.getStock());
        }

        OrderResponse response = this.orderResponseTest(this.orderService.register(request, new OrderResponse()), tests);
        assertNotNull(response.getOrder());
        assertTrue(response.isOk());
        assertTrue(response.isEmailSent());
        assertNotNull(response.getOrder());

        Order order = this.orderService.findByUuid(response.getOrder());
        assertNotNull(order);
        assertNotNull(order.getId());
        assertNotNull(order.getUuid());
        assertNotNull(order.getCreatedAt());

        assertEquals(order.getPickupDate(), request.getDate());
        assertEquals(order.getUser().getId(), this.userTest.getId());
        assertEquals(order.getAgence().getId(), this.agenceTest.getId());
        assertEquals(order.getStatus(), OrderStatus.VALIDATED);
        assertNotNull(order.getOrderLines());
        assertTrue(order.getOrderLines().size() == request.getArticles().size());

        for(OrderLine orderLine : order.getOrderLines()) {
            GameArticle article = orderLine.getGameArticle();

            String articleUuid = article.getUuid();
            assertTrue(request.getArticles().containsKey(articleUuid));

            int orderLineQuantity = orderLine.getQuantity();
            int requestArticleQuantity = request.getArticles().get(articleUuid).intValue();
            int stockBeforeOrder = mapStock.get(articleUuid);
            assertTrue(orderLineQuantity == requestArticleQuantity);
            assertTrue(stockBeforeOrder - orderLineQuantity == article.getStock().intValue());
        }
    }

    @Test
    public void test_getOrdersByCreatedAt() {
        List<Order> orders = this.orderService.getOrdersByCreatedAt(LocalDateTime.of(2024, Month.SEPTEMBER, 17, 0, 0));
        orders.forEach(System.out::println);
    }

    private OrderRequest createOrderRequest() {
        OrderRequest request = new OrderRequest();
        request.setUser(this.userTest.getUuid());
        request.setAgence(this.agenceTest.getUuid());
        request.setDate(LocalDate.now().plusDays(2));
        Map<String, Integer> map = new HashMap<>();
        map.put(UUID.randomUUID().toString(), Integer.valueOf(1));
        request.setArticles(map);
        return request;
    }

    private OrderResponse orderResponseTest(OrderResponse response, List<String> tests) {
        assertNotNull(response);
        List<String> messages = response.getMessages();
        System.out.println();
        messages.forEach(System.out::println);
        assertTrue(messages.isEmpty() == tests.isEmpty());

        for(String test: tests) {
            assertTrue(messages.contains(test));
        }
        return response;
    }

    private LocalDate nextDayOfWeek(DayOfWeek dayOfWeek) {
        LocalDate now = LocalDate.now();
        int max = 7;
        for(int day = 0 ; day < max; day++) {
            LocalDate date = LocalDate.now().plusDays(day);
            DayOfWeek dw = date.getDayOfWeek();
            if(dw == dayOfWeek) return date;
        }
        return null;
    }
}
