package com.epam.esm.model.dao;

import com.epam.esm.model.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateDao extends BaseDao<GiftCertificate> {

    List<GiftCertificate> findAll(String name, String description, String[] tagNames,
                                  String sortType, String direction, Integer limit, Integer offset);
}
