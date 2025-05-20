package com.kailaslabs.bookstore.orders.domain;

import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {}
