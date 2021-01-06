package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateTagDao;
import com.epam.esm.model.entity.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcGiftCertificateTagDaoTest {

    private EmbeddedDatabase embeddedDatabase;
    private GiftCertificateTagDao giftCertificateTagDao;

    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        giftCertificateTagDao = new JdbcGiftCertificateTagDao();
        ((JdbcGiftCertificateTagDao) giftCertificateTagDao).setJdbcTemplate(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void add() {
        giftCertificateTagDao.add(1L, 15L);
        List<Tag> tags = giftCertificateTagDao.findAllTags(1L);
        assertTrue(tags.contains(new Tag(15L, "Программирование")));
    }

    @Test
    void delete() {
        giftCertificateTagDao.delete(1L, 8L);
        List<Tag> tags = giftCertificateTagDao.findAllTags(1L);
        assertFalse(tags.contains(new Tag(8L, "Отдых")));
    }

    @Test
    void deleteAllTags() {
        giftCertificateTagDao.deleteAllTags(1L);
        List<Tag> tags = giftCertificateTagDao.findAllTags(1L);
        assertTrue(tags.isEmpty());
    }

    @Test
    void deleteByTagId() {
        giftCertificateTagDao.deleteByTagId(1L);
        List<Tag> tags = giftCertificateTagDao.findAllTags(1L);
        assertEquals(1, tags.size());
    }

    @Test
    void findAllTags() {
        List<Tag> tags = giftCertificateTagDao.findAllTags(1L);
        assertEquals(2, tags.size());
    }
}