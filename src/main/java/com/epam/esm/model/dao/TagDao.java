package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends BaseDao<Tag> {

    Optional<Tag> findByName(String name);

    List<Tag> findAll(int limit, int offset);
}
