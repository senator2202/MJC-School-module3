package com.epam.esm.model.dao.impl;

import com.epam.esm.app.SpringBootRestApplication;
import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SpringBootRestApplication.class)
class JpaGiftCertificateDaoTest {

    @Autowired
    private GiftCertificateDao dao;

    static Stream<Arguments> argsFindById() {
        return Stream.of(
                Arguments.of(1L, true),
                Arguments.of(999L, false)
        );
    }

    static Stream<Arguments> argsFindAll() {
        return Stream.of(
                Arguments.of(null, null, 16),
                Arguments.of(10, null, 10),
                Arguments.of(10, 10, 6),
                Arguments.of(10, 20, 0)
        );
    }

    @ParameterizedTest
    @MethodSource("argsFindById")
    void findById(Long id, boolean result) {
        Optional<GiftCertificate> optional = dao.findById(id);
        assertEquals(result, optional.isPresent());
    }

    @ParameterizedTest
    @MethodSource("argsFindAll")
    void findAll(Integer limit, Integer offset, int actualSize) {
        List<GiftCertificate> allTags = dao.findAll(null, null, null, null, null, limit, offset);
        assertEquals(actualSize, allTags.size());
    }

    @ParameterizedTest
    @MethodSource("argsFindById")
    @DirtiesContext
    void delete(Long id, boolean result) {
        assertEquals(result, dao.delete(id));
    }

    @Test
    @DirtiesContext
    void add() {
        dao.add(StaticDataProvider.ADDING_GIFT_CERTIFICATE);
        List<GiftCertificate> allTags = dao.findAll(null, null, null, null, null, null, null);
        assertEquals(17, allTags.size());
    }

    @Test
    @DirtiesContext
    void update() {
        GiftCertificate updated = dao.update(StaticDataProvider.UPDATING_GIFT_CERTIFICATE);
        Optional<GiftCertificate> optional = dao.findById(1L);
        assertTrue(optional.isPresent() && optional.get().equals(updated));
    }
}