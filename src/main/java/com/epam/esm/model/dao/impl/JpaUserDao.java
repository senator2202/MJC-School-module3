package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.math.BigInteger;
import java.util.List;

/**
 * The type Jpa user dao.
 */
@Repository
public class JpaUserDao extends AbstractJpaDao<User> implements UserDao {

    private static final String JPQL_FIND_ALL = "select u from User u";
    private static final String SQL_SELECT_USER_ID_WITH_HIGHEST_ORDER_SUM =
            "SELECT user_id FROM \n" +
                    "(SELECT user_id, SUM(cost) AS total_cost FROM `order`\n" +
                    "GROUP BY user_id order BY total_cost DESC LIMIT 1) AS tmp";

    @Override
    public Long userIdWithHighestOrderSum() {
        BigInteger bigInteger = (BigInteger) entityManager.
                createNativeQuery(SQL_SELECT_USER_ID_WITH_HIGHEST_ORDER_SUM)
                .getSingleResult();
        return bigInteger.longValue();
    }

    @Override
    public List<User> findAll(Integer limit, Integer offset) {
        TypedQuery<User> query = entityManager.createQuery(JPQL_FIND_ALL, User.class);
        if (limit != null) {
            query.setMaxResults(limit);
        }
        if (offset != null) {
            query.setFirstResult(offset);
        }
        return query.getResultList();
    }

    @Override
    public User add(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public User update(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected Class<User> getEntityClass() {
        return User.class;
    }
}
