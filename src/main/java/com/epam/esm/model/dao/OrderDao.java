package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Order;

import java.util.Optional;

public interface OrderDao {

    Order add(Order order);

    Optional<Order> findById(long id);
}
