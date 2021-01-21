package com.epam.esm.service;

import com.epam.esm.controller.UpdatingField;
import com.epam.esm.model.dto.GiftCertificateDTO;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateService extends BaseService<GiftCertificateDTO> {

    List<GiftCertificateDTO> findAll(String name, String description, String tagNames, String sortType,
                                     String direction, Integer limit, Integer offset);

    Optional<GiftCertificateDTO> updateField(long id, UpdatingField updatingField);
}
