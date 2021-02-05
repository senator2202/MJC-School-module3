package com.epam.esm.service.impl;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public Optional<GiftCertificateDTO> findById(long id) {
        return giftCertificateDao.findById(id).map(ObjectConverter::toGiftCertificateDTO);
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
    @Transactional(propagation = Propagation.REQUIRED)
    public GiftCertificateDTO add(GiftCertificateDTO certificate) {
        String currentDate = DateTimeUtility.getCurrentDateIso();
        certificate.setCreateDate(currentDate);
        certificate.setLastUpdateDate(currentDate);
        GiftCertificate entity = ObjectConverter.toGiftCertificateEntity(certificate);
        findTagsInDB(entity);
        return ObjectConverter.toGiftCertificateDTO(giftCertificateDao.add(entity));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<GiftCertificateDTO> update(GiftCertificateDTO certificate) {
        Optional<GiftCertificate> optional = giftCertificateDao.findById(certificate.getId());
        if (optional.isPresent()) {
            GiftCertificate found = optional.get();
            updateNotEmptyFields(certificate, found);
            found.setLastUpdateDate(DateTimeUtility.getCurrentDateIso());
            GiftCertificate updated = giftCertificateDao.update(found);
            optional = Optional.of(updated);
        }
        return optional.map(ObjectConverter::toGiftCertificateDTO);
    }

    @Override
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
            GiftCertificate entity = ObjectConverter.toGiftCertificateEntity(source);
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
