package com.epam.esm.service;

import com.epam.esm.model.dto.GiftCertificateDTO;

import java.util.List;

/**
 * Interface provides some additional operations on GiftCertificate entity
 */
public interface GiftCertificateService extends BaseService<GiftCertificateDTO> {

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
    List<GiftCertificateDTO> findAll(String name, String description, String tagNames, String sortType,
                                     String direction, Integer limit, Integer offset);

}
