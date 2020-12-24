package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateTagDao;
import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcOrderDao implements OrderDao {
    private static final String SQL_INSERT = "INSERT INTO gift.`order` (user_id, certificate_id, order_date, cost) " +
            "VALUES(?, ?, ?, ?)";
    private static final String SQL_FIND_ALL =
            "SELECT order.id, order.user_id, user.name AS user_name, order.certificate_id, \n" +
                    "gift_certificate.name AS certificate_name, gift_certificate.description AS certificate_description,\n" +
                    "gift_certificate.price AS certificate_price, gift_certificate.duration AS certificate_duration, \n" +
                    "gift_certificate.create_date AS certificate_create_date, \n" +
                    "gift_certificate.last_update_date AS certificate_last_update_date, order.order_date, order.cost\n" +
                    "FROM gift.`order` JOIN user ON order.user_id=user.id " +
                    "JOIN gift_certificate ON order.certificate_id=gift_certificate.id";
    private static final String SQL_FIND_BY_ID = SQL_FIND_ALL + "\nWHERE order.id = ?";
    private static final String SQL_FIND_ORDERS_BY_USER_ID = SQL_FIND_ALL + "\nWHERE order.user_id = ?";

    private JdbcTemplate jdbcTemplate;
    private GiftCertificateTagDao giftCertificateTagDao;
    private TransactionTemplate transactionTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setGiftCertificateTagDao(GiftCertificateTagDao giftCertificateTagDao) {
        this.giftCertificateTagDao = giftCertificateTagDao;
    }

    @Autowired
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public Order add(Order order) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, order.getUser().getId());
            ps.setLong(2, order.getGiftCertificate().getId());
            ps.setString(3, order.getOrderDate());
            ps.setInt(4, order.getCost());
            return ps;
        }, keyHolder);
        return findById(keyHolder.getKey().longValue()).get();
    }

    @Override
    public Optional<Order> findById(long id) {
        Optional<Order> optional;
        try {
            Order order = jdbcTemplate.queryForObject(SQL_FIND_BY_ID, new OrderRowMapper(), id);
            optional = Optional.of(order);
        } catch (EmptyResultDataAccessException e) {
            optional = Optional.empty();
        }
        return optional;
    }

    @Override
    public List<Order> findOrdersByUserId(long userId) {
        return transactionTemplate.execute(transactionStatus -> {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_FIND_ORDERS_BY_USER_ID, userId);
            List<Order> orders = new ArrayList<>();
            for (Map<String, Object> row : rows) {
                Order order = new Order();
                User user = new User();
                GiftCertificate giftCertificate = new GiftCertificate();
                user.setId((Long) row.get(ColumnName.ORDER_USER_ID));
                user.setName((String) row.get(ColumnName.ORDER_USER_NAME));
                giftCertificate.setId((Long) row.get(ColumnName.ORDER_CERTIFICATE_ID));
                giftCertificate.setName((String) row.get(ColumnName.ORDER_CERTIFICATE_NAME));
                giftCertificate.setDescription((String) row.get(ColumnName.ORDER_CERTIFICATE_DESCRIPTION));
                giftCertificate.setPrice((Integer) row.get(ColumnName.ORDER_CERTIFICATE_PRICE));
                giftCertificate.setDuration((Integer) row.get(ColumnName.ORDER_CERTIFICATE_DURATION));
                giftCertificate.setCreateDate((String) row.get(ColumnName.ORDER_CERTIFICATE_CREATE_DATE));
                giftCertificate.setLastUpdateDate((String) row.get(ColumnName.ORDER_CERTIFICATE_LAST_UPDATE_DATE));
                List<Tag> tags = giftCertificateTagDao.findAllTags(giftCertificate.getId());
                giftCertificate.setTags(tags);
                order.setId((Long) row.get(ColumnName.ORDER_ID));
                order.setOrderDate((String) row.get(ColumnName.ORDER_DATE));
                order.setCost((Integer) row.get(ColumnName.ORDER_COST));
                order.setUser(user);
                order.setGiftCertificate(giftCertificate);
                orders.add(order);
            }
            return orders;
        });

    }

    private class OrderRowMapper implements RowMapper<Order> {

        @Override
        public Order mapRow(ResultSet resultSet, int i) throws SQLException {
            Order order = new Order();
            User user = new User();
            GiftCertificate giftCertificate = new GiftCertificate();
            user.setId(resultSet.getLong(ColumnName.ORDER_USER_ID));
            user.setName(resultSet.getString(ColumnName.ORDER_USER_NAME));
            giftCertificate.setId(resultSet.getLong(ColumnName.ORDER_CERTIFICATE_ID));
            giftCertificate.setName(resultSet.getString(ColumnName.ORDER_CERTIFICATE_NAME));
            giftCertificate.setDescription(resultSet.getString(ColumnName.ORDER_CERTIFICATE_DESCRIPTION));
            giftCertificate.setPrice(resultSet.getInt(ColumnName.ORDER_CERTIFICATE_PRICE));
            giftCertificate.setDuration(resultSet.getInt(ColumnName.ORDER_CERTIFICATE_DURATION));
            giftCertificate.setCreateDate(resultSet.getString(ColumnName.ORDER_CERTIFICATE_CREATE_DATE));
            giftCertificate.setLastUpdateDate(resultSet.getString(ColumnName.ORDER_CERTIFICATE_LAST_UPDATE_DATE));
            order.setId(resultSet.getLong(ColumnName.ORDER_ID));
            order.setOrderDate(resultSet.getString(ColumnName.ORDER_DATE));
            order.setCost(resultSet.getInt(ColumnName.ORDER_COST));
            order.setUser(user);
            order.setGiftCertificate(giftCertificate);
            return order;
        }
    }
}
