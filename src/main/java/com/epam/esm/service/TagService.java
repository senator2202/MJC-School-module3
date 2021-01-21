package com.epam.esm.service;

import com.epam.esm.model.dto.TagDTO;

import java.util.List;

public interface TagService extends BaseService<TagDTO> {

    List<TagDTO> findAll(Integer limit, Integer offset);
}
