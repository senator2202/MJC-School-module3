package com.epam.esm.service;

import com.epam.esm.model.dto.TagDTO;

import java.util.List;

/**
 * Interface provides additional operation on Tag entity
 */
public interface TagService extends BaseService<TagDTO> {

    /**
     * Find all list.
     *
     * @param limit  the limit
     * @param offset the offset
     * @return the list
     */
    List<TagDTO> findAll(Integer limit, Integer offset);
}
