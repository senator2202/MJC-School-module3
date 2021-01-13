package com.epam.esm.service;

import com.epam.esm.controller.UpdatingField;
import com.epam.esm.model.dto.GiftCertificateDTO;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService extends BaseService<GiftCertificateDTO> {
    List<GiftCertificateDTO> findByTagName(String tagName, String sortType, String direction);

    List<GiftCertificateDTO> findByName(String name, String sortType, String direction);

    List<GiftCertificateDTO> findByDescription(String description, String sortType, String direction);

    Optional<GiftCertificateDTO> updateField(long id, UpdatingField updatingField);

    List<GiftCertificateDTO> findByTagNames(String tagNames, String sortType, String direction);
}
