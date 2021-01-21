package com.epam.esm.service;

import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<UserDTO> findById(long id);

    List<UserDTO> findAll(Integer limit, Integer offset);

    Optional<TagDTO> mostWidelyUsedTagOfUserWithHighestOrdersSum();
}
