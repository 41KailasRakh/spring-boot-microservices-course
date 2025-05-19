package com.kailaslabs.bookstore.catalog.web.controller.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
    public static ProductNotFoundException notFoundForCode(String code) {
        return new ProductNotFoundException("Product with code " + code + " not found");
    }
}
