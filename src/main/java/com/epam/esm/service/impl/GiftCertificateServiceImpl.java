package com.epam.esm.service.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.GiftCertificateTagDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.util.DateTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;
    private GiftCertificateTagDao giftCertificateTagDao;
    private TransactionTemplate transactionTemplate;

    @Autowired
    public void setGiftCertificateDao(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Autowired
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
    public List<GiftCertificate> findAll() {
        return giftCertificateDao.findAll();
    }

    @Override
    public GiftCertificate add(GiftCertificate certificate) {
        String currentDate = DateTimeUtility.getCurrentDateIso();
        certificate.setCreateDate(currentDate);
        certificate.setLastUpdateDate(currentDate);
        return transactionTemplate.execute(transactionStatus -> {
            GiftCertificate added = giftCertificateDao.add(certificate);
            addTags(added, certificate.getTags());
            return added;
        });
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
            found.setTags(source.getTags());
        }
    }

    @Override
    public void delete(long id) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                giftCertificateTagDao.deleteAllTags(id);
                giftCertificateDao.delete(id);
            }
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

    private void sortIfNecessary(List<GiftCertificate> certificates, String sortType, String direction) {
        Optional<Comparator<GiftCertificate>> optional = GiftCertificateComparatorProvider.provide(sortType, direction);
        optional.ifPresent(certificates::sort);
    }

    @Override
    public Optional<GiftCertificate> updateName(long id, String newName) {
        Optional<GiftCertificate> optional;
        if (giftCertificateDao.findById(id).isPresent()) {
            optional = Optional.of(giftCertificateDao.updateName(id, newName));
        } else {
            optional = Optional.empty();
        }
        return optional;
    }

    @Override
    public Optional<GiftCertificate> updateDescription(long id, String newDescription) {
        Optional<GiftCertificate> optional;
        if (giftCertificateDao.findById(id).isPresent()) {
            optional = Optional.of(giftCertificateDao.updateDescription(id, newDescription));
        } else {
            optional = Optional.empty();
        }
        return optional;
    }

    @Override
    public Optional<GiftCertificate> updatePrice(long id, String newPrice) {
        Optional<GiftCertificate> optional;
        if (giftCertificateDao.findById(id).isPresent()) {
            optional = Optional.of(giftCertificateDao.updatePrice(id, Integer.parseInt(newPrice)));
        } else {
            optional = Optional.empty();
        }
        return optional;
    }

    @Override
    public Optional<GiftCertificate> updateDuration(long id, String newDuration) {
        Optional<GiftCertificate> optional;
        if (giftCertificateDao.findById(id).isPresent()) {
            optional = Optional.of(giftCertificateDao.updateDuration(id, Integer.parseInt(newDuration)));
        } else {
            optional = Optional.empty();
        }
        return optional;
    }
}
