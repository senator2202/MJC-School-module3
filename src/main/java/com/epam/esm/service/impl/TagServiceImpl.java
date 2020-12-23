package com.epam.esm.service.impl;

import com.epam.esm.model.dao.GiftCertificateTagDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private TagDao dao;
    private GiftCertificateTagDao giftCertificateTagDao;
    private TransactionTemplate transactionTemplate;

    @Autowired
    public void setDao(TagDao dao) {
        this.dao = dao;
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
    public Optional<Tag> findById(long id) {
        return dao.findById(id);
    }

    @Override
    public List<Tag> findAll() {
        return dao.findAll();
    }

    @Override
    public Tag add(Tag entity) {
        return dao.add(entity);
    }

    @Override
    public Optional<Tag> update(Tag entity) {
        Optional<Tag> optional = dao.findById(entity.getId());
        if (optional.isPresent()) {
            optional = Optional.of(dao.update(entity));
        }
        return optional;
    }

    @Override
    public void delete(long id) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                giftCertificateTagDao.deleteByTagId(id);
                dao.delete(id);
            }
        });

    }
}
