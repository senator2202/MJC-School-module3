package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JpaGiftCertificateDao implements GiftCertificateDao {
    private static final String JPQL_FIND_ALL = "select distinct g from GiftCertificate g join g.tags t";
    private static final String JPQL_FIND_BY_TAG_NAME = JPQL_FIND_ALL + " where t.name = ?1";
    private static final String JPQL_FIND_BY_NAME = JPQL_FIND_ALL + " where g.name like ?1";
    private static final String JPQL_FIND_BY_DESCRIPTION = JPQL_FIND_ALL + " where g.description like ?1";
    private static final String PERCENT = "%";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<GiftCertificate> findByTagName(String tagName) {
        return entityManager.createQuery(JPQL_FIND_BY_TAG_NAME, GiftCertificate.class)
                .setParameter(1, tagName)
                .getResultList();
    }

    @Override
    public List<GiftCertificate> findByName(String name) {
        return entityManager.createQuery(JPQL_FIND_BY_NAME, GiftCertificate.class)
                .setParameter(1, PERCENT + name + PERCENT)
                .getResultList();
    }

    @Override
    public List<GiftCertificate> findByDescription(String description) {
        return entityManager.createQuery(JPQL_FIND_BY_DESCRIPTION, GiftCertificate.class)
                .setParameter(1, PERCENT + description + PERCENT)
                .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<GiftCertificate> findById(long id) {
        return Optional.ofNullable(entityManager.find(GiftCertificate.class, id));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificate> findAll() {
        return entityManager.createQuery(JPQL_FIND_ALL, GiftCertificate.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificate> findAll(int limit, int offset) {
        return entityManager.createQuery(JPQL_FIND_ALL, GiftCertificate.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    @Transactional
    public GiftCertificate add(GiftCertificate entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public GiftCertificate update(GiftCertificate entity) {
        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        if (giftCertificate != null) {
            entityManager.remove(giftCertificate);
            return true;
        } else {
            return false;
        }
    }
}
