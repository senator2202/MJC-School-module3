package com.epam.esm.service.impl;

import com.epam.esm.controller.UpdatingField;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.DateTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String TAGS_SPLITERATOR = ",";
    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;

    @Autowired
    @Qualifier("jpaGiftCertificateDao")
    public void setGiftCertificateDao(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Autowired
    @Qualifier("jpaTagDao")
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public List<GiftCertificate> findByTagName(String tagName, String sortType, String direction) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByTagName(tagName);
        sortIfNecessary(giftCertificates, sortType, direction);
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findByName(String name, String sortType, String direction) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByName(name);
        sortIfNecessary(giftCertificates, sortType, direction);
        return giftCertificates;
    }

    @Override
    public List<GiftCertificate> findByDescription(String description, String sortType, String direction) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByDescription(description);
        sortIfNecessary(giftCertificates, sortType, direction);
        return giftCertificates;
    }

    @Override
    @Transactional
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
        Optional<GiftCertificate> optional = giftCertificateDao.findById(id);
        if (optional.isPresent()) {
            GiftCertificate giftCertificate = optional.get();
            giftCertificate.setName(newName);
            optional = Optional.of(giftCertificateDao.update(giftCertificate));
        } else {
            optional = Optional.empty();
        }
        return optional;
    }

    private Optional<GiftCertificate> updateDescription(long id, String newDescription) {
        Optional<GiftCertificate> optional = giftCertificateDao.findById(id);
        if (optional.isPresent()) {
            GiftCertificate giftCertificate = optional.get();
            giftCertificate.setDescription(newDescription);
            optional = Optional.of(giftCertificateDao.update(giftCertificate));
        } else {
            optional = Optional.empty();
        }
        return optional;
    }

    private Optional<GiftCertificate> updatePrice(long id, String newPrice) {
        Optional<GiftCertificate> optional = giftCertificateDao.findById(id);
        if (optional.isPresent()) {
            GiftCertificate giftCertificate = optional.get();
            giftCertificate.setPrice(Integer.parseInt(newPrice));
            optional = Optional.of(giftCertificateDao.update(giftCertificate));
        } else {
            optional = Optional.empty();
        }
        return optional;
    }

    private Optional<GiftCertificate> updateDuration(long id, String newDuration) {
        Optional<GiftCertificate> optional = giftCertificateDao.findById(id);
        if (optional.isPresent()) {
            GiftCertificate giftCertificate = optional.get();
            giftCertificate.setDuration(Integer.parseInt(newDuration));
            optional = Optional.of(giftCertificateDao.update(giftCertificate));
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
    @Transactional
    public GiftCertificate add(GiftCertificate certificate) {
        String currentDate = DateTimeUtility.getCurrentDateIso();
        certificate.setCreateDate(currentDate);
        certificate.setLastUpdateDate(currentDate);
        replaceTagsFromDB(certificate);
        return giftCertificateDao.add(certificate);
    }

    @Override
    @Transactional
    public Optional<GiftCertificate> update(GiftCertificate certificate) {
        Optional<GiftCertificate> optional = giftCertificateDao.findById(certificate.getId());
        if (optional.isPresent()) {
            GiftCertificate found = optional.get();
            updateNotEmptyFields(certificate, found);
            certificate.setLastUpdateDate(DateTimeUtility.getCurrentDateIso());
            GiftCertificate updated = giftCertificateDao.update(found);
            optional = Optional.of(updated);
        }
        return optional;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return giftCertificateDao.delete(id);
    }

    private void updateNotEmptyFields(GiftCertificate source, GiftCertificate found) {
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
            replaceTagsFromDB(source);
            found.setTags(source.getTags());
        }
    }

    private void replaceTagsFromDB(GiftCertificate source) {
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

    private void sortIfNecessary(List<GiftCertificate> certificates, String sortType, String direction) {
        Optional<Comparator<GiftCertificate>> optional = GiftCertificateComparatorProvider.provide(sortType, direction);
        optional.ifPresent(certificates::sort);
    }
}
