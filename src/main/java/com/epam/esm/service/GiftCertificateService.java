package com.epam.esm.service;

import com.epam.esm.model.entity.GiftCertificate;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService extends BaseService<GiftCertificate> {
    List<GiftCertificate> findByTagName(String tagName, String sortType, String direction);

    List<GiftCertificate> findByName(String name, String sortType, String direction);

    List<GiftCertificate> findByDescription(String description, String sortType, String direction);
}
