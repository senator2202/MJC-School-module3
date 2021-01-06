package com.epam.esm.model.dao.impl;

import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class JdbcGiftCertificateDaoTest {

    private GiftCertificateDao giftCertificateDao;
    private EmbeddedDatabase embeddedDatabase;

    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        giftCertificateDao = new JdbcGiftCertificateDao();
        ((JdbcGiftCertificateDao) giftCertificateDao).setJdbcTemplate(jdbcTemplate);
        JdbcGiftCertificateTagDao giftCertificateTagDao = new JdbcGiftCertificateTagDao();
        giftCertificateTagDao.setJdbcTemplate(jdbcTemplate);
        ((JdbcGiftCertificateDao) giftCertificateDao).setGiftCertificateTagDao(giftCertificateTagDao);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void findByIdExist() {
        Optional<GiftCertificate> optional = giftCertificateDao.findById(1L);
        assertTrue(optional.isPresent() && optional.get().getName().equals("Сауна Тритон"));
    }

    @Test
    void findByIdNotExist() {
        Optional<GiftCertificate> optional = giftCertificateDao.findById(199L);
        assertFalse(optional.isPresent());
    }

    @Test
    void findAll() {
        List<GiftCertificate> allCertificates = giftCertificateDao.findAll();
        assertEquals(10, allCertificates.size());
    }

    @Test
    void add() {
        giftCertificateDao.add(StaticDataProvider.ADDING_CERTIFICATE);
        List<GiftCertificate> allCertificates = giftCertificateDao.findAll();
        assertEquals(11, allCertificates.size());
    }

    @Test
    void update() {
        GiftCertificate giftCertificate = giftCertificateDao.findById(1L).get();
        giftCertificate.setPrice(155);
        GiftCertificate updated = giftCertificateDao.update(giftCertificate);
        assertEquals(155, updated.getPrice().intValue());
    }

    @Test
    void delete() {
        giftCertificateDao.delete(1L);
        Optional<GiftCertificate> optional = giftCertificateDao.findById(1L);
        assertFalse(optional.isPresent());
    }

    @Test
    void findByTagNameExist() {
        List<GiftCertificate> certificates = giftCertificateDao.findByTagName("Активность");
        assertEquals(2, certificates.size());
    }

    @Test
    void findByName() {
        List<GiftCertificate> certificates = giftCertificateDao.findByName("Тату");
        assertEquals(2, certificates.size());
    }

    @Test
    void findByDescription() {
        List<GiftCertificate> certificates = giftCertificateDao.findByDescription("Бесплатная");
        assertEquals(2, certificates.size());
    }
}