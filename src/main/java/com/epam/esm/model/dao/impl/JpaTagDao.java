package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JpaTagDao implements TagDao {

    private static final String JPQL_FIND_ALL = "select distinct t from Tag t";
    private static final String JPQL_FIND_BY_NAME = JPQL_FIND_ALL + " where t.name = ?1";
    private static final String SQL_DELETE_BY_TAG_ID = "DELETE FROM certificate_tag WHERE tag_id = ?";

    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Optional<Tag> optionalTag;
        try {
            Tag tag = entityManager.createQuery(JPQL_FIND_BY_NAME, Tag.class)
                    .setParameter(1, name)
                    .getSingleResult();
            optionalTag = Optional.of(tag);
        } catch (NoResultException e) {
            optionalTag = Optional.empty();
        }
        return optionalTag;
    }

    @Override
    public Optional<Tag> findById(long id) {
        return Optional.ofNullable(entityManager.find(Tag.class, id));
    }

    @Override
    public List<Tag> findAll() {
        return entityManager.createQuery(JPQL_FIND_ALL, Tag.class).getResultList();
    }

    @Override
    public List<Tag> findAll(int limit, int offset) {
        return entityManager.createQuery(JPQL_FIND_ALL, Tag.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Tag add(Tag entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    @Transactional
    public Tag update(Tag entity) {
        return entityManager.merge(entity);
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        Tag tag = entityManager.find(Tag.class, id);
        if (tag != null) {
            entityManager.createNativeQuery(SQL_DELETE_BY_TAG_ID).setParameter(1, id).executeUpdate();
            entityManager.remove(tag);
            return true;
        } else {
            return false;
        }
    }
}
