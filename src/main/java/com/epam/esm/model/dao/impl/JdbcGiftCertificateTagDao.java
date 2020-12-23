package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateTagDao;
import com.epam.esm.model.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.epam.esm.model.dao.impl.ColumnName.*;

@Repository
@Transactional
public class JdbcGiftCertificateTagDao implements GiftCertificateTagDao {
    private static final String SQL_INSERT = "INSERT INTO certificate_tag (gift_certificate_id, tag_id) VALUES (?, ?)";
    private static final String SQL_DELETE = "DELETE FROM certificate_tag WHERE gift_certificate_id = ? AND tag_id = ?";
    private static final String SQL_ALL_CERTIFICATE_TAGS_ID =
            "SELECT tag_id FROM certificate_tag WHERE gift_certificate_id = ?";
    private static final String SQL_ALL_CERTIFICATE_TAGS =
            "SELECT id, name FROM certificate_tag JOIN tag ON tag_id = id WHERE gift_certificate_id = ?";
    private static final String SQL_DELETE_BY_TAG_ID = "DELETE FROM certificate_tag WHERE tag_id = ?";


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void add(long certificateId, long tagId) {
        jdbcTemplate.update(SQL_INSERT, certificateId, tagId);
    }

    @Override
    public void delete(long certificateId, long tagId) {
        jdbcTemplate.update(SQL_DELETE, certificateId, tagId);
    }

    @Override
    public void deleteAllTags(long certificateId) {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_ALL_CERTIFICATE_TAGS_ID, certificateId);
        for (Map<String, Object> row : rows) {
            long tagId = (long) row.get(CERTIFICATE_TAG_TAG_ID);
            delete(certificateId, tagId);
        }
    }

    @Override
    public void deleteByTagId(long tagId) {
        jdbcTemplate.update(SQL_DELETE_BY_TAG_ID, tagId);
    }

    @Override
    public List<Tag> findAllTags(long certificateId) {
        List<Tag> tags = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(SQL_ALL_CERTIFICATE_TAGS, certificateId);
        for (Map<String, Object> row : rows) {
            long tagId = (long) row.get(TAG_ID);
            String tagName = (String) row.get(TAG_NAME);
            tags.add(new Tag(tagId, tagName));
        }
        return tags;
    }
}
