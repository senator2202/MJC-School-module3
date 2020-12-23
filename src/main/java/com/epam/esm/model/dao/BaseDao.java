package com.epam.esm.model.dao;

import com.epam.esm.model.entity.GiftEntity;

import java.util.List;
import java.util.Optional;

public interface BaseDao<T extends GiftEntity> {

    Optional<T> findById(long id);

    List<T> findAll();

    T add(T entity);

    T update(T entity);

    void delete(long id);
}