package com.kailaslabs.bookstore.catalog.web.controller;

import org.springframework.web.bind.annotation.*;

import com.kailaslabs.bookstore.catalog.domain.PagedResult;
import com.kailaslabs.bookstore.catalog.domain.Product;
import com.kailaslabs.bookstore.catalog.domain.ProductService;

@RestController
@RequestMapping("/api/products")
class ProductController {

  private final ProductService productService;

  ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  PagedResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo) {
    return productService.getProducts(pageNo);
  }

  @GetMapping("/{code}")
  Product getProductByCode(@PathVariable String code) {
    return productService.getProductByCode(code);
  }
}
