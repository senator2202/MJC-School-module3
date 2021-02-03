package com.epam.esm.service.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ObjectConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * The type Tag service.
 */
@Service
public class TagServiceImpl implements TagService {

    private TagDao tagDao;

    /**
     * Instantiates a new Tag service.
     *
     * @param tagDao the tag dao
     */
    @Autowired
    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Optional<TagDTO> findById(long id) {
        return tagDao.findById(id).map(ObjectConverter::toTagDTO);
    }

    @Override
    public List<TagDTO> findAll(Integer limit, Integer offset) {
        return tagDao.findAll(limit, offset)
                .stream()
                .map(ObjectConverter::toTagDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TagDTO add(TagDTO entity) {
        Optional<Tag> optional = tagDao.findByName(entity.getName());
        return optional
                //if optional is present, convert from Optional<Tag> to Optional<TagDTO>
                .map(ObjectConverter::toTagDTO)
                //if optional is present, return optional.get, else add new tag and return its DTO
                .orElseGet(() -> ObjectConverter.toTagDTO(tagDao.add(ObjectConverter.toTagEntity(entity))));
    }

    @Override
    @Transactional
    public Optional<TagDTO> update(TagDTO entity) {
        Optional<Tag> optional = tagDao.findById(entity.getId());
        return optional.map(t -> ObjectConverter.toTagDTO(tagDao.update(ObjectConverter.toTagEntity(entity))));
    }

    @Override
    public boolean delete(long id) {
        return tagDao.delete(id);
    }
}
