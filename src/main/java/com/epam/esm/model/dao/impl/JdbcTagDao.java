package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.model.dao.impl.ColumnName.TAG_ID;
import static com.epam.esm.model.dao.impl.ColumnName.TAG_NAME;

@Repository
@Transactional
public class JdbcTagDao implements TagDao {

    private static final String SQL_SELECT_ALL_TAGS = "SELECT id, name FROM tag";
    private static final String SQL_SELECT_TAG = SQL_SELECT_ALL_TAGS + " WHERE id = ?";
    private static final String SQL_SELECT_BY_NAME = SQL_SELECT_ALL_TAGS + " WHERE name = ?";
    private static final String SQL_INSERT = "INSERT INTO tag (name) VALUES (?)";
    private static final String SQL_UPDATE = "UPDATE tag SET name = ? WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM tag WHERE id = ?";

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Tag> findById(long id) {
        Optional<Tag> optional;
        try {
            Tag tag = jdbcTemplate.queryForObject(SQL_SELECT_TAG, new TagRowMapper(), id);
            optional = Optional.of(tag);
        } catch (EmptyResultDataAccessException e) {
            optional = Optional.empty();
        }
        return optional;
    }

    @Override
    public List<Tag> findAll() {
        List<Tag> tags = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL_TAGS);
        for (Map<String, Object> row : rows) {
            Tag tag = new Tag();
            tag.setId((Long) row.get(TAG_ID));
            tag.setName((String) row.get(TAG_NAME));
            tags.add(tag);
        }
        return tags;
    }

    @Override
    public Tag add(Tag entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getName());
            return ps;
        }, keyHolder);
        return findById(keyHolder.getKey().longValue()).get();
    }

    @Override
    public Tag update(Tag entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getName(), entity.getId());
        return findById(entity.getId()).get();
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update(SQL_DELETE, id) > 0;
    }

    @Override
    public Optional<Tag> findByName(String name) {
        Optional<Tag> optional;
        try {
            Tag tag = jdbcTemplate.queryForObject(SQL_SELECT_BY_NAME, new TagRowMapper(), name);
            optional = Optional.of(tag);
        } catch (EmptyResultDataAccessException e) {
            optional = Optional.empty();
        }
        return optional;
    }

    static class TagRowMapper implements RowMapper<Tag> {

        @Override
        public Tag mapRow(ResultSet resultSet, int i) throws SQLException {
            Tag tag = new Tag();
            tag.setId(resultSet.getLong(TAG_ID));
            tag.setName(resultSet.getString(TAG_NAME));
            return tag;
        }
    }
}
