package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.GiftCertificateTagDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.model.dao.impl.ColumnName.*;

@Repository
public class JdbcGiftCertificateDao implements GiftCertificateDao {

    private static final String SQL_SELECT_ALL_CERTIFICATES =
            "SELECT id, name, description, price, duration, create_date, last_update_date\n" +
                    "FROM gift_certificate";
    private static final String SQL_SELECT_CERTIFICATE = SQL_SELECT_ALL_CERTIFICATES + "\nWHERE id = ?";
    private static final String SQL_INSERT =
            "INSERT INTO gift_certificate (name, description, price, duration, create_date, last_update_date) " +
                    "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE gift_certificate SET NAME = ?, description = ?, price = ?, duration = ?, last_update_date = ? \n" +
                    "WHERE id = ?";
    private static final String SQL_DELETE = "DELETE FROM gift_certificate WHERE id = ?";
    private static final String SQL_SELECT_CERTIFICATES_BY_TAG_NAME =
            "SELECT gift_certificate.id, gift_certificate.name, description, " +
                    "price, duration, create_date, last_update_date\n" +
                    "FROM gift_certificate JOIN certificate_tag ON gift_certificate.id=gift_certificate_id JOIN tag ON tag.id=tag_id\n" +
                    "WHERE tag.name=?";
    private static final String SQL_SELECT_BY_NAME =
            "SELECT id, name, description, price, duration, create_date, last_update_date\n" +
                    "FROM gift_certificate WHERE name LIKE ?";
    private static final String SQL_SELECT_BY_DESCRIPTION =
            "SELECT id, name, description, price, duration, create_date, last_update_date\n" +
                    "FROM gift_certificate WHERE description LIKE ?";
    private static final String SQL_UPDATE_NAME = "UPDATE gift_certificate SET name = ? WHERE id = ?";
    private static final String SQL_UPDATE_DESCRIPTION = "UPDATE gift_certificate SET description = ? WHERE id = ?";
    private static final String SQL_UPDATE_PRICE = "UPDATE gift_certificate SET price = ? WHERE id = ?";
    private static final String SQL_UPDATE_DURATION = "UPDATE gift_certificate SET duration = ? WHERE id = ?";
    private static final String PERCENT = "%";

    private JdbcTemplate jdbcTemplate;
    private GiftCertificateTagDao giftCertificateTagDao;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Autowired
    public void setGiftCertificateTagDao(GiftCertificateTagDao giftCertificateTagDao) {
        this.giftCertificateTagDao = giftCertificateTagDao;
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        Optional<GiftCertificate> optional;
        try {
            GiftCertificate giftCertificate =
                    jdbcTemplate.queryForObject(SQL_SELECT_CERTIFICATE, new GiftCertificateRowMapper(), id);
            List<Tag> tags = giftCertificateTagDao.findAllTags(id);
            giftCertificate.setTags(tags);
            optional = Optional.of(giftCertificate);
        } catch (EmptyResultDataAccessException e) {
            optional = Optional.empty();
        }
        return optional;
    }

    @Override
    public List<GiftCertificate> findAll() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_ALL_CERTIFICATES);
        return getGiftCertificates(rows);
    }

    @Override
    public GiftCertificate add(GiftCertificate entity) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, entity.getName());
            ps.setString(2, entity.getDescription());
            ps.setInt(3, entity.getPrice() != null ? entity.getPrice() : 0);
            ps.setInt(4, entity.getDuration() != null ? entity.getDuration() : 0);
            ps.setString(5, entity.getCreateDate());
            ps.setString(6, entity.getLastUpdateDate());
            return ps;
        }, keyHolder);
        return findById(keyHolder.getKey().longValue()).get();
    }

    @Override
    public GiftCertificate update(GiftCertificate entity) {
        jdbcTemplate.update(SQL_UPDATE, entity.getName(), entity.getDescription(),
                entity.getPrice(), entity.getDuration(), entity.getLastUpdateDate(), entity.getId());
        return findById(entity.getId()).get();
    }

    @Override
    public boolean delete(long id) {
        return jdbcTemplate.update(SQL_DELETE, id) > 0;
    }

    @Override
    public List<GiftCertificate> findByTagName(String tagName) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_CERTIFICATES_BY_TAG_NAME, tagName);
        return getGiftCertificates(rows);
    }

    @Override
    public List<GiftCertificate> findByName(String name) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_SELECT_BY_NAME, PERCENT + name + PERCENT);
        return getGiftCertificates(rows);
    }

    @Override
    public List<GiftCertificate> findByDescription(String description) {
        List<Map<String, Object>> rows =
                jdbcTemplate.queryForList(SQL_SELECT_BY_DESCRIPTION, PERCENT + description + PERCENT);
        return getGiftCertificates(rows);
    }

    private List<GiftCertificate> getGiftCertificates(List<Map<String, Object>> rows) {
        List<GiftCertificate> certificates = new ArrayList<>();
        for (Map<String, Object> row : rows) {
            GiftCertificate giftCertificate = new GiftCertificate();
            giftCertificate.setId((Long) row.get(GIFT_CERTIFICATE_ID));
            giftCertificate.setName((String) row.get(GIFT_CERTIFICATE_NAME));
            giftCertificate.setDescription((String) row.get(GIFT_CERTIFICATE_DESCRIPTION));
            giftCertificate.setPrice((Integer) row.get(GIFT_CERTIFICATE_PRICE));
            giftCertificate.setDuration((Integer) row.get(GIFT_CERTIFICATE_DURATION));
            giftCertificate.setCreateDate((String) row.get(GIFT_CERTIFICATE_CREATE_DATE));
            giftCertificate.setLastUpdateDate((String) row.get(GIFT_CERTIFICATE_LAST_UPDATE_DATE));
            List<Tag> tags = giftCertificateTagDao.findAllTags(giftCertificate.getId());
            giftCertificate.setTags(tags);
            certificates.add(giftCertificate);
        }
        return certificates;
    }

    @Override
    public GiftCertificate updateName(long id, String newName) {
        jdbcTemplate.update(SQL_UPDATE_NAME, newName, id);
        return findById(id).get();
    }

    @Override
    public GiftCertificate updateDescription(long id, String newDescription) {
        jdbcTemplate.update(SQL_UPDATE_DESCRIPTION, newDescription, id);
        return findById(id).get();
    }

    @Override
    public GiftCertificate updatePrice(long id, int newPrice) {
        jdbcTemplate.update(SQL_UPDATE_PRICE, newPrice, id);
        return findById(id).get();
    }

    @Override
    public GiftCertificate updateDuration(long id, int newDuration) {
        jdbcTemplate.update(SQL_UPDATE_DURATION, newDuration, id);
        return findById(id).get();
    }

    private class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {

        @Override
        public GiftCertificate mapRow(ResultSet resultSet, int i) throws SQLException {
            GiftCertificate certificate = new GiftCertificate();
            certificate.setId(resultSet.getLong(GIFT_CERTIFICATE_ID));
            certificate.setName(resultSet.getString(GIFT_CERTIFICATE_NAME));
            certificate.setDescription(resultSet.getString(GIFT_CERTIFICATE_DESCRIPTION));
            certificate.setPrice(resultSet.getInt(GIFT_CERTIFICATE_PRICE));
            certificate.setDuration(resultSet.getInt(GIFT_CERTIFICATE_DURATION));
            certificate.setCreateDate(resultSet.getString(GIFT_CERTIFICATE_CREATE_DATE));
            certificate.setLastUpdateDate(resultSet.getString(GIFT_CERTIFICATE_LAST_UPDATE_DATE));
            return certificate;
        }
    }
}
