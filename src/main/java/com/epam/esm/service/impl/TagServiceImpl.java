package com.epam.esm.service.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private TagDao tagDao;

    @Autowired
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Optional<Tag> findById(long id) {
        return tagDao.findById(id);
    }

    @Override
    public List<Tag> findAll(Integer limit, Integer offset) {
        if (limit != null) {
            return tagDao.findAll(limit, offset != null ? offset : 0);
        } else {
            return tagDao.findAll();
        }
    }

    @Override
    public Tag add(Tag entity) {
        Optional<Tag> optional = tagDao.findByName(entity.getName());
        return optional.orElseGet(() -> tagDao.add(entity));
    }

    @Override
    @Transactional
    public Optional<Tag> update(Tag entity) {
        Optional<Tag> optional = tagDao.findById(entity.getId());
        if (optional.isPresent()) {
            optional = Optional.of(tagDao.update(entity));
        }
        return optional;
    }

    @Override
    public boolean delete(long id) {
        return tagDao.delete(id);
    }
}
