package com.epam.esm.model.dao.impl;

import com.epam.esm.app.SpringBootRestApplication;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = SpringBootRestApplication.class)
class JpaTagDaoTest {

    @Autowired
    private TagDao dao;

    @ParameterizedTest
    @MethodSource("argsFindById")
    void findById(Long id, boolean result) {
        Optional<Tag> optional = dao.findById(id);
        assertEquals(result, optional.isPresent());
    }

    static Stream<Arguments> argsFindById() {
        return Stream.of(
                Arguments.of(1L, true),
                Arguments.of(999L, false)
        );
    }

    @ParameterizedTest
    @MethodSource("argsFindAll")
    void findAll(Integer limit, Integer offset, int actualSize) {
        List<Tag> allTags = dao.findAll(limit, offset);
        assertEquals(actualSize, allTags.size());
    }

    static Stream<Arguments> argsFindAll() {
        return Stream.of(
                Arguments.of(null, null, 16),
                Arguments.of(10, null, 10),
                Arguments.of(10, 10, 6),
                Arguments.of(10, 20, 0)
        );
    }

    @Test
    @DirtiesContext
    void add() {
        dao.add(new Tag("NewTag"));
        List<Tag> allTags = dao.findAll(null, null);
        assertEquals(17, allTags.size());
    }

    @Test
    @DirtiesContext
    void update() {
        Tag updated = dao.update(new Tag(1L, "Пассивность"));
        Optional<Tag> optional = dao.findById(1L);
        assertTrue(optional.isPresent() && optional.get().equals(updated));
    }

    @ParameterizedTest
    @MethodSource("argsFindById")
    @DirtiesContext
    void delete(Long id, boolean result) {
        assertEquals(result, dao.delete(id));
    }

    @ParameterizedTest
    @MethodSource("argsFindByName")
    void findByName(String name, boolean result) {
        Optional<Tag> optional = dao.findByName(name);
        assertEquals(result, optional.isPresent());
    }

    static Stream<Arguments> argsFindByName() {
        return Stream.of(
                Arguments.of("Активность", true),
                Arguments.of("SomethingElse", false)
        );
    }
}