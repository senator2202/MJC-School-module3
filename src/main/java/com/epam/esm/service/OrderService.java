package com.epam.esm.service;

import com.epam.esm.model.dto.OrderDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides operation on Order entity.
 */
public interface OrderService {

    /**
     * Add order dto.
     *
     * @param userId        the user id
     * @param certificateId the certificate id
     * @return the order dto
     */
    OrderDTO add(long userId, long certificateId);

    /**
     * Find user order by id optional.
     *
     * @param userId  the user id
     * @param orderId the order id
     * @return the optional
     */
    Optional<OrderDTO> findUserOrderById(long userId, long orderId);

    /**
     * Find orders by user id list.
     *
     * @param userId the user id
     * @param limit  the limit
     * @param offset the offset
     * @return the list
     */
    List<OrderDTO> findOrdersByUserId(long userId, Integer limit, Integer offset);

    /**
     * Order belongs to user boolean.
     *
     * @param userId  the user id
     * @param orderId the order id
     * @return the boolean
     */
    boolean orderBelongsToUser(long userId, long orderId);
}
