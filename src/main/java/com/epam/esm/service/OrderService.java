package com.epam.esm.service;

import com.epam.esm.model.dto.OrderDTO;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    OrderDTO add(OrderDTO order);

    Optional<OrderDTO> findById(long id);

    List<OrderDTO> findOrdersByUserId(long userId, Integer limit, Integer offset);

    boolean orderBelongsToUser(long userId, long orderId);
}
