package com.epam.esm.service;

import com.epam.esm.controller.UpdatingField;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.service.impl.GiftCertificateComparatorProvider;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public interface GiftCertificateService extends BaseService<GiftCertificate> {
    List<GiftCertificate> findByTagName(String tagName, String sortType, String direction);

    List<GiftCertificate> findByName(String name, String sortType, String direction);

    List<GiftCertificate> findByDescription(String description, String sortType, String direction);

    Optional<GiftCertificate> updateField(long id, UpdatingField updatingField);

    List<GiftCertificate> findByTagNames(String tagNames, String sortType, String direction);

    default void updateNotEmptyFields(GiftCertificate source, GiftCertificate found) {
        if (source.getName() != null) {
            found.setName(source.getName());
        }
        if (source.getDescription() != null) {
            found.setDescription(source.getDescription());
        }
        if (source.getPrice() != null) {
            found.setPrice(source.getPrice());
        }
        if (source.getDuration() != null) {
            found.setDuration(source.getDuration());
        }
        if (source.getTags() != null) {
            found.setTags(source.getTags());
        }
    }

    default void sortIfNecessary(List<GiftCertificate> certificates, String sortType, String direction) {
        Optional<Comparator<GiftCertificate>> optional = GiftCertificateComparatorProvider.provide(sortType, direction);
        optional.ifPresent(certificates::sort);
    }
}
