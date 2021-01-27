package com.epam.esm.model.dao;

import com.epam.esm.model.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides DB operations for User entity
 */
public interface UserDao {

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<User> findById(long id);

    /**
     * Find all list.
     *
     * @param limit  the limit
     * @param offset the offset
     * @return the list
     */
    List<User> findAll(Integer limit, Integer offset);

    /**
     * User id with highest order sum long.
     *
     * @return the long
     */
    Long userIdWithHighestOrderSum();
}
