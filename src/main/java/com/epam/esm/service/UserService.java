package com.epam.esm.service;

import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;

import java.util.List;
import java.util.Optional;

/**
 * Interface provides operation on Order entity.
 */
public interface UserService {

    /**
     * Find by id optional.
     *
     * @param id the id
     * @return the optional
     */
    Optional<UserDTO> findById(long id);

    /**
     * Find all list.
     *
     * @param limit  the limit
     * @param offset the offset
     * @return the list
     */
    List<UserDTO> findAll(Integer limit, Integer offset);

    /**
     * Most widely used tag of user with highest orders sum optional.
     *
     * @return the optional
     */
    Optional<TagDTO> mostWidelyUsedTagOfUserWithHighestOrdersSum();
}
