package com.epam.esm.service.impl;

import com.epam.esm.controller.UpdatingField;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.DateTimeUtility;
import com.epam.esm.util.ObjectConverter;
import com.epam.esm.validator.GiftEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;

/**
 * The type Gift certificate service.
 */
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String DASH = "-";
    private static final String UNDER_SCOPE = "_";
    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;

    /**
     * Instantiates a new Gift certificate service.
     *
     * @param giftCertificateDao the gift certificate dao
     * @param tagDao             the tag dao
     */
    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, TagDao tagDao) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
    }

    @Override
    @Transactional
    public Optional<GiftCertificateDTO> updateField(long id, UpdatingField updatingField) {
        Optional<GiftCertificate> optional = giftCertificateDao.findById(id);
        if (optional.isEmpty()) {
            return Optional.empty();
        }
        GiftCertificate giftCertificate = optional.get();
        UpdatingField.FieldName fieldName = updatingField.getFieldName();
        String fieldValue = updatingField.getFieldValue();
        switch (fieldName) {
            case NAME:
                giftCertificate.setName(fieldValue);
                break;
            case DESCRIPTION:
                giftCertificate.setDescription(fieldValue);
                break;
            case PRICE:
                giftCertificate.setPrice(BigDecimal.valueOf(Double.parseDouble(fieldValue)));
                break;
            case DURATION:
                giftCertificate.setDuration(Integer.parseInt(fieldValue));
        }

        return Optional.of(giftCertificateDao.update(giftCertificate)).map(ObjectConverter::toDTO);
    }

    @Override
    public Optional<GiftCertificateDTO> findById(long id) {
        return giftCertificateDao.findById(id).map(ObjectConverter::toDTO);
    }

    @Override
    public List<GiftCertificateDTO> findAll(String name, String description, String tagNames,
                                            String sortType, String direction, Integer limit, Integer offset) {
        if (sortType != null) {
            sortType = sortType.replace(DASH, UNDER_SCOPE);
        }
        return ObjectConverter
                .toGiftCertificateDTOs(giftCertificateDao.findAll(name, description,
                        tagNames != null ? tagNames.split(GiftEntityValidator.TAG_SPLITERATOR) : null, sortType,
                        direction, limit, offset));
    }

    @Override
    @Transactional
    public GiftCertificateDTO add(GiftCertificateDTO certificate) {
        String currentDate = DateTimeUtility.getCurrentDateIso();
        certificate.setCreateDate(currentDate);
        certificate.setLastUpdateDate(currentDate);
        GiftCertificate entity = ObjectConverter.toEntity(certificate);
        findTagsInDB(entity);
        return ObjectConverter.toDTO(giftCertificateDao.add(entity));
    }

    @Override
    @Transactional
    public Optional<GiftCertificateDTO> update(GiftCertificateDTO certificate) {
        Optional<GiftCertificate> optional = giftCertificateDao.findById(certificate.getId());
        if (optional.isPresent()) {
            GiftCertificate found = optional.get();
            updateNotEmptyFields(certificate, found);
            certificate.setLastUpdateDate(DateTimeUtility.getCurrentDateIso());
            GiftCertificate updated = giftCertificateDao.update(found);
            optional = Optional.of(updated);
        }
        return optional.map(ObjectConverter::toDTO);
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return giftCertificateDao.delete(id);
    }

    /**
     * Method copies non-empty fields from DTO object to entity object, found in DB
     */
    private void updateNotEmptyFields(GiftCertificateDTO source, GiftCertificate found) {
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
            GiftCertificate entity = ObjectConverter.toEntity(source);
            findTagsInDB(entity);
            found.setTags(entity.getTags());
        }
    }

    /**
     * Method updates GiftCertificate tags. It iterates on list of tags, looks for tag with the same name in db.
     * If tag with the same name found, replace tag in list on existing tag from DB.
     */
    private void findTagsInDB(GiftCertificate source) {
        List<Tag> tags = source.getTags();
        if (tags != null) {
            ListIterator<Tag> iterator = tags.listIterator();
            while (iterator.hasNext()) {
                Tag tag = iterator.next();
                Optional<Tag> optionalTag = tagDao.findByName(tag.getName());
                optionalTag.ifPresent(iterator::set);
            }
        }
    }
}
