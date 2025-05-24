package com.kailaslabs.bookstore.orders.web.controllers;

import com.kailaslabs.bookstore.orders.domain.OrderNotFoundException;
import com.kailaslabs.bookstore.orders.domain.OrderService;
import com.kailaslabs.bookstore.orders.domain.SecurityService;
import com.kailaslabs.bookstore.orders.domain.models.CreateOrderRequest;
import com.kailaslabs.bookstore.orders.domain.models.CreateOrderResponse;
import com.kailaslabs.bookstore.orders.domain.models.OrderDTO;
import com.kailaslabs.bookstore.orders.domain.models.OrderSummary;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;
    private final SecurityService securityService;

    public OrderController(OrderService orderService, SecurityService securityService) {
        this.orderService = orderService;
        this.securityService = securityService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    CreateOrderResponse createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        String userName = securityService.getLoginUserName();
        log.info(" Creating order for user {}", userName);
        return orderService.createOrder(userName, createOrderRequest);
    }

    @GetMapping
    List<OrderSummary> getOrders() {
        String userName = securityService.getLoginUserName();
        log.info("Retrieving orders for user {}", userName);
        return orderService.findOrders(userName);
    }

    @GetMapping(value = "/{orderNumber}")
    OrderDTO findOrder(@PathVariable String orderNumber) {
        log.info("Retrieving order by id {}", orderNumber);
        String userName = securityService.getLoginUserName();
        return orderService
                .findUserOrder(userName, orderNumber)
                .orElseThrow(() -> new OrderNotFoundException(orderNumber));
    }
}
