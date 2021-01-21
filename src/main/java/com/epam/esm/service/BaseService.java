package com.epam.esm.service;

import com.epam.esm.model.entity.GiftEntity;

import java.util.Optional;

public interface BaseService<T extends GiftEntity> {

    Optional<T> findById(long id);

    T add(T entity);

    Optional<T> update(T entity);

    boolean delete(long id);
}
