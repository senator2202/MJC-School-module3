package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
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


class JdbcTagDaoTest {

    private TagDao tagDao;
    private EmbeddedDatabase embeddedDatabase;

    @BeforeEach
    void setUp() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addDefaultScripts()
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        tagDao = new JdbcTagDao();
        ((JdbcTagDao) tagDao).setJdbcTemplate(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    void findByIdExist() {
        Optional<Tag> optional = tagDao.findById(1L);
        assertTrue(optional.isPresent() && optional.get().getName().equals("Активность"));
    }

    @Test
    void findByIdNotExist() {
        Optional<Tag> optional = tagDao.findById(999L);
        assertFalse(optional.isPresent());
    }

    @Test
    void findAll() {
        List<Tag> allTags = tagDao.findAll();
        assertEquals(10, allTags.size());
    }

    @Test
    void add() {
        tagDao.add(new Tag(255L, "NewTag"));
        List<Tag> allTags = tagDao.findAll();
        assertEquals(11, allTags.size());
    }

    @Test
    void update() {
        tagDao.update(new Tag(1L, "Пассивность"));
        Optional<Tag> optional = tagDao.findById(1L);
        assertTrue(optional.isPresent() && optional.get().getName().equals("Пассивность"));
    }

    @Test
    void delete() {
        tagDao.delete(1L);
        assertFalse(tagDao.findById(1L).isPresent());
    }

    @Test
    void findByNameExist() {
        Optional<Tag> optional = tagDao.findByName("Активность");
        assertTrue(optional.isPresent());
    }

    @Test
    void findByNameNotExist() {
        Optional<Tag> optional = tagDao.findByName("SomethingElse");
        assertFalse(optional.isPresent());
    }
}