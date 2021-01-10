package com.epam.esm.service.impl;

import com.epam.esm.controller.UpdatingField;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.GiftCertificateTagDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.DateTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String TAGS_SPLITERATOR = ",";
    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;
    private GiftCertificateTagDao giftCertificateTagDao;
    private TransactionTemplate transactionTemplate;

    public GiftCertificateServiceImpl() {
    }

    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao,
                                      TagDao tagDao,
                                      GiftCertificateTagDao giftCertificateTagDao,
                                      TransactionTemplate transactionTemplate) {
        this.giftCertificateDao = giftCertificateDao;
        this.tagDao = tagDao;
        this.giftCertificateTagDao = giftCertificateTagDao;
        this.transactionTemplate = transactionTemplate;
    }

    @Autowired
    @Qualifier("jdbcGiftCertificateDao")
    public void setGiftCertificateDao(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Autowired
    @Qualifier("jdbcTagDao")
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Autowired
    public void setGiftCertificateTagDao(GiftCertificateTagDao giftCertificateTagDao) {
        this.giftCertificateTagDao = giftCertificateTagDao;
    }

    @Autowired
    public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        transactionTemplate = new TransactionTemplate(platformTransactionManager);
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        return giftCertificateDao.findById(id);
    }

    @Override
    public List<GiftCertificate> findAll(Integer limit, Integer offset) {
        if (limit != null) {
            return giftCertificateDao.findAll(limit, offset != null ? offset : 0);
        } else {
            return giftCertificateDao.findAll();
        }
    }

    @Override
    public GiftCertificate add(GiftCertificate certificate) {
        /*String currentDate = DateTimeUtility.getCurrentDateIso();
        certificate.setCreateDate(currentDate);
        certificate.setLastUpdateDate(currentDate);
        return transactionTemplate.execute(transactionStatus -> {
            GiftCertificate added = giftCertificateDao.add(certificate);
            addTags(added, certificate.getTags());
            return added;
        });*/
        return null;
    }

    private void addTags(GiftCertificate added, List<Tag> tags) {
        if (tags != null && !tags.isEmpty()) {
            for (Tag tag : tags) {
                Tag addedTag = addTag(added.getId(), tag);
                added.addTag(addedTag);
            }
        }
    }

    private Tag addTag(long certificateId, Tag tag) {
        Optional<Tag> optionalTag = tagDao.findByName(tag.getName());
        Tag addedTag = optionalTag.orElseGet(() -> tagDao.add(tag));
        giftCertificateTagDao.add(certificateId, addedTag.getId());
        return addedTag;
    }

    @Override
    public Optional<GiftCertificate> update(GiftCertificate certificate) {
        Optional<GiftCertificate> optional = giftCertificateDao.findById(certificate.getId());
        if (optional.isPresent()) {
            GiftCertificate found = optional.get();
            updateNotEmptyFields(certificate, found);
            certificate.setLastUpdateDate(DateTimeUtility.getCurrentDateIso());
            GiftCertificate updated = transactionTemplate.execute(transactionStatus -> {
                GiftCertificate updating = giftCertificateDao.update(found);
                if (certificate.getTags() != null) {
                    giftCertificateTagDao.deleteAllTags(updating.getId());
                    updating.clearAllTags();
                    addTags(updating, certificate.getTags());
                }
                return updating;
            });
            optional = Optional.of(updated);
        }
        return optional;
    }

    @Override
    public boolean delete(long id) {
        return transactionTemplate.execute(transactionStatus -> {
            giftCertificateTagDao.deleteAllTags(id);
            return giftCertificateDao.delete(id);
        });
    }

    @Override
    public List<GiftCertificate> findByTagName(String tagName, String sortType, String direction) {
        List<GiftCertificate> certificates = giftCertificateDao.findByTagName(tagName);
        sortIfNecessary(certificates, sortType, direction);
        return certificates;
    }

    @Override
    public List<GiftCertificate> findByName(String name, String sortType, String direction) {
        List<GiftCertificate> certificates = giftCertificateDao.findByName(name);
        sortIfNecessary(certificates, sortType, direction);
        return certificates;
    }

    @Override
    public List<GiftCertificate> findByDescription(String description, String sortType, String direction) {
        List<GiftCertificate> certificates = giftCertificateDao.findByDescription(description);
        sortIfNecessary(certificates, sortType, direction);
        return certificates;
    }

    @Override
    public Optional<GiftCertificate> updateField(long id, UpdatingField updatingField) {
        UpdatingField.FieldName fieldName = updatingField.getFieldName();
        String fieldValue = updatingField.getFieldValue();
        switch (fieldName) {
            case NAME:
                return updateName(id, fieldValue);
            case DESCRIPTION:
                return updateDescription(id, fieldValue);
            case PRICE:
                return updatePrice(id, fieldValue);
            case DURATION:
                return updateDuration(id, fieldValue);
            default:
                return Optional.empty();
        }
    }

    private Optional<GiftCertificate> updateName(long id, String newName) {
        Optional<GiftCertificate> optional;
        if (giftCertificateDao.findById(id).isPresent()) {
            optional = Optional.of(giftCertificateDao.updateName(id, newName));
        } else {
            optional = Optional.empty();
        }
        return optional;
    }

    private Optional<GiftCertificate> updateDescription(long id, String newDescription) {
        Optional<GiftCertificate> optional;
        if (giftCertificateDao.findById(id).isPresent()) {
            optional = Optional.of(giftCertificateDao.updateDescription(id, newDescription));
        } else {
            optional = Optional.empty();
        }
        return optional;
    }

    private Optional<GiftCertificate> updatePrice(long id, String newPrice) {
        Optional<GiftCertificate> optional;
        if (giftCertificateDao.findById(id).isPresent()) {
            optional = Optional.of(giftCertificateDao.updatePrice(id, Integer.parseInt(newPrice)));
        } else {
            optional = Optional.empty();
        }
        return optional;
    }

    private Optional<GiftCertificate> updateDuration(long id, String newDuration) {
        Optional<GiftCertificate> optional;
        if (giftCertificateDao.findById(id).isPresent()) {
            optional = Optional.of(giftCertificateDao.updateDuration(id, Integer.parseInt(newDuration)));
        } else {
            optional = Optional.empty();
        }
        return optional;
    }

    @Override
    public List<GiftCertificate> findByTagNames(String tagNames, String sortType, String direction) {
        List<GiftCertificate> certificates = new ArrayList<>();
        List<String> tags = Arrays.asList(tagNames.split(TAGS_SPLITERATOR));

        //add all lists, generated by each tag, into certificates
        tags.forEach(t -> certificates.addAll(giftCertificateDao.findByTagName(t)));

        //get optional of comparator, according to sortType and direction parameters
        Optional<Comparator<GiftCertificate>> optional = GiftCertificateComparatorProvider.provide(sortType, direction);

        /*if optional is present, convert list to stream, filter certificates by containing all tags,
         *remove all non unique elements, sort with comparator, collect to list and return
         */
        return optional.map(c -> certificates.stream()
                .filter(cert -> getTagNames(cert).containsAll(tags))
                .distinct()
                .sorted(c)
                .collect(Collectors.toList()))
                .orElseGet(() -> certificates.stream()
                        .filter(cert -> getTagNames(cert).containsAll(tags))
                        .distinct()
                        .collect(Collectors.toList()));
        /*if optional is not present, do the same, but without sorting
         */
    }

    private List<String> getTagNames(GiftCertificate giftCertificate) {
        List<String> tagNames = null;
        if (giftCertificate.getTags() != null) {
            tagNames = new ArrayList<>();
            for (Tag tag : giftCertificate.getTags()) {
                tagNames.add(tag.getName());
            }
        }
        return tagNames;
    }
}
