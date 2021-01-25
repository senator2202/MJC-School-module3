package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Order;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides DB operations for Order entity
 */
public interface OrderDao {

    /**
     * Add order.
     *
     * @param order the order
     * @return the order
     */
    Order add(Order order);

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<Order> findById(long id);

    /**
     * Find orders by user id list.
     *
     * @param userId the user id
     * @param limit  the limit
     * @param offset the offset
     * @return the list
     */
    List<Order> findOrdersByUserId(long userId, Integer limit, Integer offset);

    /**
     * Select most popular tag id of user long.
     *
     * @param userId the user id
     * @return the long
     */
    Long selectMostPopularTagIdOfUser(long userId);
}
