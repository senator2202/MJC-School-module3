package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcUserDao implements UserDao {

    private static final String SQL_SELECT_ALL = "SELECT id, name FROM user";
    private static final String SQL_SELECT_BY_ID = SQL_SELECT_ALL + " WHERE id = ?";
    private static final String SQL_SELECT_MOST_POPULAR_TAG_OF_USER_WITH_MAX_ORDERS_SUM =
            "SELECT tag.id, tag.name, COUNT(tag.id) AS frequency\n" +
                    "FROM gift.`order` JOIN certificate_tag ON gift.`order`.certificate_id=certificate_tag.gift_certificate_id JOIN tag ON certificate_tag.tag_id=tag.id\n" +
                    "WHERE user_id=(SELECT user_id FROM (SELECT user_id, SUM(cost) AS total_cost FROM gift.`order` GROUP BY user_id order BY total_cost DESC LIMIT 1) AS max_sum_row)\n" +
                    "GROUP BY id\n" +
                    "ORDER BY frequency DESC\n" +
                    "LIMIT 1";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<User> findById(long id) {
        Optional<User> optional;
        try {
            User user = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, new UserRowMapper(), id);
            optional = Optional.of(user);
        } catch (EmptyResultDataAccessException e) {
            optional = Optional.empty();
        }
        return optional;
    }

    @Override
    public List<User> findAll() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL);
        List<User> users = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            User user = new User();
            user.setId((Long) row.get(ColumnName.USER_ID));
            user.setName((String) row.get(ColumnName.USER_NAME));
            users.add(user);
        }
        return users;
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

    @Override
    public Optional<Tag> mostWidelyUsedTagOfUserWithHighestOrdersSum() {
        Optional<Tag> optional;
        try {
            Tag tag = jdbcTemplate.queryForObject(SQL_SELECT_MOST_POPULAR_TAG_OF_USER_WITH_MAX_ORDERS_SUM,
                    new JdbcTagDao.TagRowMapper());
            optional = Optional.of(tag);
        } catch (EmptyResultDataAccessException e) {
            optional = Optional.empty();
        }
        return optional;
    }

    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet resultSet, int i) throws SQLException {
            User user = new User();
            user.setId(resultSet.getLong(ColumnName.USER_ID));
            user.setName(resultSet.getString(ColumnName.USER_NAME));
            return user;
        }
    }
}
