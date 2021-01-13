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
    public List<GiftCertificateDTO> findByTagName(String tagName, String sortType, String direction) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByTagName(tagName);
        sortIfNecessary(giftCertificates, sortType, direction);
        return ObjectConverter.toDTOs(giftCertificates);
    }

    @Override
    public List<GiftCertificateDTO> findByName(String name, String sortType, String direction) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByName(name);
        sortIfNecessary(giftCertificates, sortType, direction);
        return ObjectConverter.toDTOs(giftCertificates);
    }

    @Override
    public List<GiftCertificateDTO> findByDescription(String description, String sortType, String direction) {
        List<GiftCertificate> giftCertificates = giftCertificateDao.findByDescription(description);
        sortIfNecessary(giftCertificates, sortType, direction);
        return ObjectConverter.toDTOs(giftCertificates);
    }

    @Override
    @Transactional
    public Optional<GiftCertificateDTO> updateField(long id, UpdatingField updatingField) {
        UpdatingField.FieldName fieldName = updatingField.getFieldName();
        String fieldValue = updatingField.getFieldValue();
        switch (fieldName) {
            case NAME:
                return updateName(id, fieldValue).map(ObjectConverter::toDTO);
            case DESCRIPTION:
                return updateDescription(id, fieldValue).map(ObjectConverter::toDTO);
            case PRICE:
                return updatePrice(id, fieldValue).map(ObjectConverter::toDTO);
            case DURATION:
                return updateDuration(id, fieldValue).map(ObjectConverter::toDTO);
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
    public List<GiftCertificateDTO> findByTagNames(String tagNames, String sortType, String direction) {
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
                .map(ObjectConverter::toDTO)
                .collect(Collectors.toList()))
                .orElseGet(() -> certificates.stream()
                        .filter(cert -> getTagNames(cert).containsAll(tags))
                        .distinct()
                        .map(ObjectConverter::toDTO)
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
    public Optional<GiftCertificateDTO> findById(long id) {
        return giftCertificateDao.findById(id).map(ObjectConverter::toDTO);
    }

    @Override
    public List<GiftCertificateDTO> findAll(Integer limit, Integer offset) {
        if (limit != null) {
            return ObjectConverter.toDTOs(giftCertificateDao.findAll(limit, offset != null ? offset : 0));
        } else {
            return ObjectConverter.toDTOs(giftCertificateDao.findAll());
        }
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

    private void sortIfNecessary(List<GiftCertificate> certificates, String sortType, String direction) {
        Optional<Comparator<GiftCertificate>> optional = GiftCertificateComparatorProvider.provide(sortType, direction);
        optional.ifPresent(certificates::sort);
    }
}
