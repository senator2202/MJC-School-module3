package com.epam.esm.service;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;

import java.util.Optional;

public interface UserService extends BaseService<User> {
    Optional<Tag> mostWidelyUsedTagOfUserWithHighestOrdersSum();
}
