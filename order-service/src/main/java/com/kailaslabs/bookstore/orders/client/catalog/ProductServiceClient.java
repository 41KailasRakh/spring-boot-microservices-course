package com.kailaslabs.bookstore.orders.client.catalog;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.actuate.health.NamedContributors;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProductServiceClient {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceClient.class);

    private final RestClient restClient;
    private final NamedContributors namedContributors;

    ProductServiceClient(RestClient restClient, NamedContributors namedContributors) {
        this.restClient = restClient;
        this.namedContributors = namedContributors;
    }

    @CircuitBreaker(name = "catalog-service")
    @Retry(name = "catalog-service", fallbackMethod = "getProductByCodeFallback")
    public Optional<Product> getProductByCode(String code) {
        log.info("Fetching product for code {}", code);
        log.info("URI URI " + restClient.get().uri("/products/{code}", code));
        var products =
                restClient.get().uri("/api/products/{code}", code).retrieve().body(Product.class);
        return Optional.ofNullable(products);
    }

    /*   Optional<Product> getProductByCodeFallback(String code, Throwable throwable) {
        System.out.println("ProductServiceClient.getProductByCodeFallback, Falling back exception");
        return Optional.empty();
    }*/
    Optional<Product> getProductByCodeFallback(String code, Throwable throwable) {
        System.out.println("ProductServiceClient.getProductByCodeFallback - Fallback called for code: " + code);
        System.out.println("Exception message: " + throwable.getMessage());
        throwable.printStackTrace(); // prints the full stack trace to console

        return Optional.empty();
    }
}
