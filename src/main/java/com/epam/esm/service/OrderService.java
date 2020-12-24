package com.epam.esm.service;

import com.epam.esm.model.entity.Order;

import java.util.List;

public interface OrderService {

    Order add(Order order);

    List<Order> findOrdersByUserId(long userId);
}
