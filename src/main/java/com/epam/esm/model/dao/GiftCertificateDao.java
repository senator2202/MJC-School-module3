package com.epam.esm.model.dao;

import com.epam.esm.model.entity.GiftCertificate;

import java.util.List;

/**
 * Interface provides additional DB operation for GiftCertificate entity
 */
public interface GiftCertificateDao extends BaseDao<GiftCertificate> {

    /**
     * Find all list.
     *
     * @param name        the name
     * @param description the description
     * @param tagNames    the tag names
     * @param sortType    the sort type
     * @param direction   the direction
     * @param limit       the limit
     * @param offset      the offset
     * @return the list
     */
    List<GiftCertificate> findAll(String name, String description, String[] tagNames,
                                  String sortType, String direction, Integer limit, Integer offset);
}
