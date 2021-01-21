package com.epam.esm.model.dao;

import com.epam.esm.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserDao {

    Optional<User> findById(long id);

    List<User> findAll(Integer limit, Integer offset);

    Long userIdWithHighestOrderSum();
}
