package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderDao {

    Order add(Order order);

    Optional<Order> findById(long id);

    List<Order> findOrdersByUserId(long userId);

    List<Order> findOrdersByUserId(long userId, int limit, int offset);

    Long selectMostPopularTagIdOfUser(long userId);
}
