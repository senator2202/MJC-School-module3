package com.epam.esm.service;

import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;

import java.util.Optional;

public interface UserService extends BaseService<UserDTO> {
    Optional<TagDTO> mostWidelyUsedTagOfUserWithHighestOrdersSum();
}
