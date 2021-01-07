package com.epam.esm.service;

import com.epam.esm.model.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order add(Order order);

    Optional<Order> findById(long id);

    List<Order> findOrdersByUserId(long userId, Integer limit, Integer offset);

    boolean orderBelongsToUser(long userId, long orderId);
}
