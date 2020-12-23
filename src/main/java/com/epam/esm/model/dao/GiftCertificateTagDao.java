package com.epam.esm.model.dao;

import com.epam.esm.model.entity.Tag;

import java.util.List;

public interface GiftCertificateTagDao {
    void add(long certificateId, long tagId);

    void delete(long certificateId, long tagId);

    void deleteAllTags(long certificateId);

    void deleteByTagId(long tagId);

    List<Tag> findAllTags(long certificateId);
}
