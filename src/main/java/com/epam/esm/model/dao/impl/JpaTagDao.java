package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

/**
 * The type Jpa tag dao.
 */
@Repository
public class JpaTagDao extends AbstractJpaDao<Tag> implements TagDao {

    private static final String JPQL_FIND_ALL = "select distinct t from Tag t";
    private static final String JPQL_FIND_BY_NAME = JPQL_FIND_ALL + " where t.name = ?1";
    private static final String SQL_DELETE_BY_TAG_ID = "DELETE FROM certificate_tag WHERE tag_id = ?";

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
    @Transactional(readOnly = true)
    public List<Tag> findAll(Integer limit, Integer offset) {
        TypedQuery<Tag> query = entityManager.createQuery(JPQL_FIND_ALL, Tag.class);
        if (limit != null) {
            query.setMaxResults(limit);
        }
        if (offset != null) {
            query.setFirstResult(offset);
        }

        return query.getResultList();
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

    @Override
    protected Class<Tag> getEntityClass() {
        return Tag.class;
    }
}
