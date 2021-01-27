package com.epam.esm.service;

import java.util.Optional;

/**
 * Interface provides service for CRUD operations for project entities.
 *
 * @param <T> the type parameter
 */
public interface BaseService<T> {

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
     * Update optional.
     *
     * @param entity the entity
     * @return the optional
     */
    Optional<T> update(T entity);

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the boolean
     */
    boolean delete(long id);
}
