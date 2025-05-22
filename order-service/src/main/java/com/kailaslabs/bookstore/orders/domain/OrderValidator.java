package com.kailaslabs.bookstore.orders.domain;

import com.kailaslabs.bookstore.orders.client.catalog.Product;
import com.kailaslabs.bookstore.orders.client.catalog.ProductServiceClient;
import com.kailaslabs.bookstore.orders.domain.models.CreateOrderRequest;
import com.kailaslabs.bookstore.orders.domain.models.OrderItem;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderValidator {

    private static final Logger log = LoggerFactory.getLogger(OrderValidator.class);
    private final ProductServiceClient productServiceClient;

    public OrderValidator(ProductServiceClient productServiceClient) {
        this.productServiceClient = productServiceClient;
    }

    void validate(CreateOrderRequest request) {
        Set<OrderItem> items = request.items();
        for (OrderItem item : items) {
            Product product = productServiceClient
                    .getProductByCode(item.code())
                    .orElseThrow(() -> new InvalidOrderException("Invalid product code: " + item.code()));
            if (item.price().compareTo(product.price()) != 0) {
                log.error("Product price not matching. Actual price: {}, received: {}", product.price(), item.price());
                throw new InvalidOrderException("Product price not matching. Actual price: " + product.price());
            }
        }
    }
}
