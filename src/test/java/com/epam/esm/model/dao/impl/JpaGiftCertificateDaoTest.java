package com.epam.esm.model.dao.impl;

import com.epam.esm.app.SpringBootRestApplication;
import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.entity.GiftCertificate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SpringBootRestApplication.class)
class JpaGiftCertificateDaoTest {

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    static Stream<Arguments> argsFindById() {
        return Stream.of(
                Arguments.of(1L, true),
                Arguments.of(999L, false)
        );
    }

    static Stream<Arguments> argsFindAll() {
        return Stream.of(
                Arguments.of(null, null, null, null, null, 13),
                Arguments.of(null, null, null, 10, null, 10),
                Arguments.of(null, null, null, 10, 10, 3),
                Arguments.of(null, null, null, 10, 20, 0),
                Arguments.of("Тату", null, null, null, null, 2),
                Arguments.of(null, "people", null, null, null, 2),
                Arguments.of(null, null, new String[]{"Активность", "Отдых"}, null, null, 2)
        );
    }

    static Stream<Arguments> argsFindAllSort() {
        return Stream.of(
                Arguments.of("price", "asc", Comparator.comparing(GiftCertificate::getPrice)),
                Arguments.of("price", "desc", Comparator.comparing(GiftCertificate::getPrice).reversed()),
                Arguments.of("duration", "asc", Comparator.comparing(GiftCertificate::getDuration)),
                Arguments.of("duration", "desc", Comparator.comparing(GiftCertificate::getDuration).reversed()),
                Arguments.of("create_date", "asc", Comparator.comparing(GiftCertificate::getCreateDate)),
                Arguments.of("create_date", "desc", Comparator.comparing(GiftCertificate::getCreateDate).reversed()),
                Arguments.of("last_update_date", "asc", Comparator.comparing(GiftCertificate::getLastUpdateDate)),
                Arguments.of("last_update_date", "desc", Comparator.comparing(GiftCertificate::getLastUpdateDate).reversed())
        );
    }

    @ParameterizedTest
    @MethodSource("argsFindById")
    void findById(Long id, boolean result) {
        Optional<GiftCertificate> optional = giftCertificateDao.findById(id);
        assertEquals(result, optional.isPresent());
    }

    @ParameterizedTest
    @MethodSource("argsFindAll")
    void findAll(
            String name,
            String description,
            String[] tagNames,
            Integer limit,
            Integer offset,
            int actualSize
    ) {
        List<GiftCertificate> allTags = giftCertificateDao.findAll(name, description, tagNames,
                null, null, limit, offset);
        assertEquals(actualSize, allTags.size());
    }

    @ParameterizedTest
    @MethodSource("argsFindAllSort")
    void findAllSort(String sortType, String direction, Comparator<GiftCertificate> comparator) {
        List<GiftCertificate> allTags = giftCertificateDao.findAll(null, null, null,
                sortType, direction, null, null);
        List<GiftCertificate> sorted = new ArrayList<>(allTags);
        sorted.sort(comparator);
        assertEquals(allTags, sorted);
    }

    @ParameterizedTest
    @MethodSource("argsFindById")
    @DirtiesContext
    void delete(Long id, boolean result) {
        assertEquals(result, giftCertificateDao.delete(id));
    }

    @Test
    @DirtiesContext
    void add() {
        GiftCertificate created = giftCertificateDao.add(StaticDataProvider.ADDING_GIFT_CERTIFICATE);
        Optional<GiftCertificate> optional = giftCertificateDao.findById(created.getId());
        assertTrue(optional.isPresent() && optional.get().equals(created));
    }

    @Test
    @DirtiesContext
    void update() {
        GiftCertificate updated = giftCertificateDao.update(StaticDataProvider.UPDATING_GIFT_CERTIFICATE);
        Optional<GiftCertificate> optional = giftCertificateDao.findById(1L);
        assertTrue(optional.isPresent() && optional.get().equals(updated));
    }
}