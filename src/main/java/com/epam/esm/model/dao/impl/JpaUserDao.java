package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.User;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaUserDao implements UserDao {

    private static final String JPQL_FIND_ALL = "select u from User u";
    private static final String SQL_SELECT_USER_ID_WITH_HIGHEST_ORDER_SUM =
            "SELECT user_id FROM \n" +
                    "(SELECT user_id, SUM(cost) AS total_cost FROM gift.`order`\n" +
                    "GROUP BY user_id order BY total_cost DESC LIMIT 1) AS tmp";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Long userIdWithHighestOrderSum() {
        BigInteger bigInteger = (BigInteger) entityManager.
                createNativeQuery(SQL_SELECT_USER_ID_WITH_HIGHEST_ORDER_SUM)
                .getSingleResult();
        return bigInteger.longValue();
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery(JPQL_FIND_ALL, User.class).getResultList();
    }

    @Override
    public List<User> findAll(int limit, int offset) {
        return entityManager.createQuery(JPQL_FIND_ALL, User.class)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
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
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }
}
