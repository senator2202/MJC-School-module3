package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Tag;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides additional DB operations for Tag entity
 */
public interface TagDao extends BaseDao<Tag> {

    /**
     * Find by name optional.
     *
     * @param name the name
     * @return the optional
     */
    Optional<Tag> findByName(String name);

    /**
     * Find all list.
     *
     * @param limit  the limit
     * @param offset the offset
     * @return the list
     */
    List<Tag> findAll(Integer limit, Integer offset);
}
