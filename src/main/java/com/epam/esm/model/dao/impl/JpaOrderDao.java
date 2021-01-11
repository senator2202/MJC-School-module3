package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.entity.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaOrderDao implements OrderDao {

    private static final String JPQL_FIND_USERS_ORDERS = "select o from Order o where o.user.id = ?1";
    private static final String SQL_SELECT_MOST_POPULAR_TAG_OF_USER =
            "SELECT tag_id FROM (SELECT tag_id, COUNT(tag_id) AS most_popular \n" +
                    "FROM `order` JOIN certificate_tag ON `order`.certificate_id=certificate_tag.gift_certificate_id \n" +
                    "WHERE user_id = ?\n" +
                    "GROUP BY tag_id\n" +
                    "order BY most_popular DESC\n" +
                    "LIMIT 1) AS temp";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Order add(Order order) {
        entityManager.persist(order);
        return order;
    }

    @Override
    public Optional<Order> findById(long id) {
        Order order = entityManager.find(Order.class, id);
        return Optional.ofNullable(order);
    }

    @Override
    public List<Order> findOrdersByUserId(long userId) {
        return entityManager.createQuery(JPQL_FIND_USERS_ORDERS, Order.class)
                .setParameter(1, userId)
                .getResultList();
    }

    @Override
    public List<Order> findOrdersByUserId(long userId, int limit, int offset) {
        return entityManager.createQuery(JPQL_FIND_USERS_ORDERS, Order.class)
                .setParameter(1, userId)
                .setMaxResults(limit)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public Long selectMostPopularTagIdOfUser(long userId) {
        BigInteger bigInteger = (BigInteger) entityManager
                .createNativeQuery(SQL_SELECT_MOST_POPULAR_TAG_OF_USER)
                .setParameter(1, userId)
                .getSingleResult();
        return bigInteger.longValue();
    }
}
