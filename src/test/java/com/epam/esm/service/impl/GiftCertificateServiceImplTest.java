package com.epam.esm.service.impl;

import com.epam.esm.controller.UpdatingField;
import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.service.GiftCertificateService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class GiftCertificateServiceImplTest {

    private GiftCertificateService service;
    private GiftCertificateDao giftCertificateDao;
    private TagDao tagDao;

    static Stream<Arguments> args() {
        return Stream.of(
                Arguments.of(StaticDataProvider.UPDATING_NAME),
                Arguments.of(StaticDataProvider.UPDATING_DESCRIPTION),
                Arguments.of(StaticDataProvider.UPDATING_PRICE),
                Arguments.of(StaticDataProvider.UPDATING_DURATION)
        );
    }

    @BeforeEach
    void setUp() {
        giftCertificateDao = Mockito.mock(GiftCertificateDao.class);
        tagDao = Mockito.mock(TagDao.class);
        service = new GiftCertificateServiceImpl(giftCertificateDao, tagDao);
    }

    @ParameterizedTest
    @MethodSource("args")
    void updateFieldExisting(UpdatingField field) {
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.GIFT_CERTIFICATE));
        when(giftCertificateDao.update(StaticDataProvider.GIFT_CERTIFICATE))
                .thenReturn(StaticDataProvider.GIFT_CERTIFICATE);
        Optional<GiftCertificateDTO> actual = service.updateField(1L, field);
        Optional<GiftCertificateDTO> expected = Optional.of(StaticDataProvider.GIFT_CERTIFICATE_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void updateFieldNotExisting() {
        when(giftCertificateDao.findById(11111L)).thenReturn(Optional.empty());
        Optional<GiftCertificateDTO> actual = service.updateField(11111L, StaticDataProvider.UPDATING_NAME);
        Optional<GiftCertificateDTO> expected = Optional.empty();
        assertEquals(actual, expected);
    }

    @Test
    void findByIdExisting() {
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.GIFT_CERTIFICATE));
        Optional<GiftCertificateDTO> actual = service.findById(1L);
        Optional<GiftCertificateDTO> expected = Optional.of(StaticDataProvider.GIFT_CERTIFICATE_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void findByIdNotExisting() {
        when(giftCertificateDao.findById(11111L)).thenReturn(Optional.empty());
        Optional<GiftCertificateDTO> actual = service.findById(11111L);
        Optional<GiftCertificateDTO> expected = Optional.empty();
        assertEquals(actual, expected);
    }

    /*@Test
    void findAll() {
        when(giftCertificateDao.findAll()).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_LIST);
        List<GiftCertificateDTO> actual = service.findAll(name, description, tagName, sortType, direction, null, null);
        List<GiftCertificateDTO> expected = StaticDataProvider.GIFT_CERTIFICATE_DTO_LIST;
        assertEquals(actual, expected);
    }

    @Test
    void findAllLimit() {
        when(giftCertificateDao.findAll(2, 0)).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_LIST_LIMIT);
        List<GiftCertificateDTO> actual = service.findAll(name, description, tagName, sortType, direction, 2, null);
        List<GiftCertificateDTO> expected = StaticDataProvider.GIFT_CERTIFICATE_DTO_LIST_LIMIT;
        assertEquals(actual, expected);
    }

    @Test
    void findAllLimitOffset() {
        when(giftCertificateDao.findAll(2, 10)).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_LIST_LIMIT);
        List<GiftCertificateDTO> actual = service.findAll(name, description, tagName, sortType, direction, 2, 10);
        List<GiftCertificateDTO> expected = StaticDataProvider.GIFT_CERTIFICATE_DTO_LIST_LIMIT;
        assertEquals(actual, expected);
    }*/

    @Test
    void add() {
        when(tagDao.findByName("Вязание")).thenReturn(Optional.of(StaticDataProvider.TAG));
        when(giftCertificateDao.add(StaticDataProvider.GIFT_CERTIFICATE))
                .thenReturn(StaticDataProvider.GIFT_CERTIFICATE);
        GiftCertificateDTO actual = service.add(StaticDataProvider.GIFT_CERTIFICATE_DTO);
        GiftCertificateDTO expected = StaticDataProvider.GIFT_CERTIFICATE_DTO;
        assertEquals(actual, expected);
    }

    @Test
    void updateExisting() {
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.GIFT_CERTIFICATE));
        when(giftCertificateDao.update(StaticDataProvider.GIFT_CERTIFICATE))
                .thenReturn(StaticDataProvider.GIFT_CERTIFICATE);
        Optional<GiftCertificateDTO> actual = service.update(StaticDataProvider.GIFT_CERTIFICATE_DTO);
        Optional<GiftCertificateDTO> expected = Optional.of(StaticDataProvider.GIFT_CERTIFICATE_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void deleteTrue() {
        when(giftCertificateDao.delete(1L)).thenReturn(true);
        assertTrue(service.delete(1L));
    }

    @Test
    void deleteFalse() {
        when(giftCertificateDao.delete(11111L)).thenReturn(false);
        assertFalse(service.delete(11111L));
    }
}