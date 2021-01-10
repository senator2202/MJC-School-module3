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

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class JpaGiftCertificateService implements GiftCertificateService {

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
        return null;
    }

    @Override
    public List<GiftCertificate> findByName(String name, String sortType, String direction) {
        return null;
    }

    @Override
    public List<GiftCertificate> findByDescription(String description, String sortType, String direction) {
        return null;
    }

    @Override
    public Optional<GiftCertificate> updateField(long id, UpdatingField updatingField) {
        return Optional.empty();
    }

    @Override
    public List<GiftCertificate> findByTagNames(String tagNames, String sortType, String direction) {
        return null;
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
        String currentDate = DateTimeUtility.getCurrentDateIso();
        certificate.setCreateDate(currentDate);
        certificate.setLastUpdateDate(currentDate);
        List<Tag> tags = certificate.getTags();
        if (tags != null) {
            Iterator<Tag> iterator = certificate.getTags().iterator();
            while (iterator.hasNext()) {
                Tag tag = iterator.next();
                Optional<Tag> optionalTag = tagDao.findByName(tag.getName());
                if (optionalTag.isPresent()) {
                    tags.remove(tag);
                    tags.add(optionalTag.get());
                }
            }
        }
        return giftCertificateDao.add(certificate);
    }

    private void createTagIfNecessary(Tag tag) {
        Optional<Tag> optionalTag = tagDao.findByName(tag.getName());
        if (optionalTag.isEmpty()) {
            tagDao.add(tag);
        }
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
            /*GiftCertificate updated = transactionTemplate.execute(transactionStatus -> {
                GiftCertificate updating = giftCertificateDao.update(found);
                if (certificate.getTags() != null) {
                    giftCertificateTagDao.deleteAllTags(updating.getId());
                    updating.clearAllTags();
                    addTags(updating, certificate.getTags());
                }
                return updating;
            });*/
            optional = Optional.of(updated);
        }
        return optional;
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        return giftCertificateDao.delete(id);
    }
}
