package com.epam.esm.model.dao;

import com.epam.esm.model.entity.GiftEntity;

import java.util.Optional;

/**
 * Interface provides CRUD operations with DB for project entities.
 *
 * @param <T> the type parameter
 */
public interface BaseDao<T extends GiftEntity> {

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<T> findById(long id);

    /**
     * Add t.
     *
     * @param entity the entity
     * @return the t
     */
    T add(T entity);

    /**
     * Update t.
     *
     * @param entity the entity
     * @return the t
     */
    T update(T entity);

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean delete(long id);
}