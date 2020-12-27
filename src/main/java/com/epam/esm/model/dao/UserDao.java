package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;

import java.util.Optional;

public interface UserDao extends BaseDao<User> {

    Optional<Tag> mostWidelyUsedTagOfUserWithHighestOrdersSum();
}
