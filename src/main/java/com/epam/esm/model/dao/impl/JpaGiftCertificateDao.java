package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Jpa gift certificate dao.
 */
@Repository
public class JpaGiftCertificateDao extends AbstractJpaDao<GiftCertificate> implements GiftCertificateDao {

    private static final String JPQL_DELETE_ORDERS_BY_CERTIFICATE_ID =
            "delete from Order o where o.giftCertificate.id = ?1 ";
    private static final String JPQL_FIND_ALL = "select g from GiftCertificate g join g.tags t ";
    private static final String GROUP_BY_HAVING = "group by g.id having count(g.id) >= ?1";
    private static final String GIFT_CERTIFICATE_NAME = "name";
    private static final String GIFT_CERTIFICATE_DESCRIPTION = "description";
    private static final String PERCENT = "%";
    private static final String WHERE = "where ";
    private static final String NAME_LIKE = "g.name like :name ";
    private static final String DESCRIPTION_LIKE = "g.description like :description ";
    private static final String TAG_NAME_EQUALS = "t.name = :tagName";
    private static final String AND = "and ";
    private static final String ORDER_BY = "order by ";
    private static final String SPACE = " ";
    private static final String LEFT_BRACKET = "(";
    private static final String RIGHT_BRACKET = ")";
    private static final String TAG_NAME = "tagName";
    private static final String ZERO = "0";
    private static final String OR = "or";

    @Override
    public List<GiftCertificate> findAll(String name, String description, String[] tagNames,
                                         String sortType, String direction, Integer limit, Integer offset) {
        StringBuilder jpql = new StringBuilder(JPQL_FIND_ALL);
        Map<String, String> parameterMap = new HashMap<>();
        appendName(name, jpql, parameterMap);
        appendDescription(description, jpql, parameterMap);
        appendTagNames(tagNames, jpql, parameterMap);
        appendOrderBy(sortType, direction, jpql);
        TypedQuery<GiftCertificate> query = entityManager.createQuery(jpql.toString(), GiftCertificate.class);
        for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        query.setParameter(1, tagNames != null ? (long) tagNames.length : 1L);
        if (limit != null) {
            query.setMaxResults(limit);
        }
        if (offset != null) {
            query.setFirstResult(offset);
        }
        return query.getResultList();
    }

    private void appendOrderBy(String sortType, String direction, StringBuilder jpql) {
        if (sortType != null) {
            jpql.append(ORDER_BY).append(SPACE).append(SqlJpqlMap.getInstance().get(sortType)).append(SPACE);
            if (direction != null) {
                jpql.append(direction).append(SPACE);
            }
        }
    }

    private void appendTagNames(String[] tagNames, StringBuilder jpql, Map<String, String> parameterMap) {
        if (tagNames != null) {
            jpql.append(parameterMap.isEmpty() ? WHERE : AND);
            jpql.append(LEFT_BRACKET);
            jpql.append(TAG_NAME_EQUALS.concat(ZERO));
            parameterMap.put(TAG_NAME + ZERO, tagNames[0]);
            for (int i = 1; i < tagNames.length; i++) {
                jpql.append(SPACE).append(OR).append(SPACE);
                String number = String.valueOf(i);
                jpql.append(TAG_NAME_EQUALS.concat(number));
                parameterMap.put(TAG_NAME + number, tagNames[i]);
            }
            jpql.append(RIGHT_BRACKET).append(SPACE);
        }
        jpql.append(GROUP_BY_HAVING);
    }

    private void appendDescription(String description, StringBuilder jpql, Map<String, String> parameterMap) {
        if (description != null) {
            jpql.append(parameterMap.isEmpty() ? WHERE + DESCRIPTION_LIKE : AND + DESCRIPTION_LIKE);
            parameterMap.put(GIFT_CERTIFICATE_DESCRIPTION, PERCENT + description + PERCENT);
        }
    }

    private void appendName(String name, StringBuilder jpql, Map<String, String> parameterMap) {
        if (name != null) {
            jpql.append(WHERE).append(NAME_LIKE);
            parameterMap.put(GIFT_CERTIFICATE_NAME, PERCENT + name + PERCENT);
        }
    }

    @Override
    @Transactional
    public boolean delete(long id) {
        GiftCertificate giftCertificate = entityManager.find(GiftCertificate.class, id);
        if (giftCertificate != null) {
            entityManager.createQuery(JPQL_DELETE_ORDERS_BY_CERTIFICATE_ID)
                    .setParameter(1, id)
                    .executeUpdate();
            entityManager.remove(giftCertificate);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected Class<GiftCertificate> getEntityClass() {
        return GiftCertificate.class;
    }
}
