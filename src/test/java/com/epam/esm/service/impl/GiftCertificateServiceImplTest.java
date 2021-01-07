package com.epam.esm.service.impl;

import com.epam.esm.controller.UpdatingField;
import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.GiftCertificateTagDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.GiftCertificate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class GiftCertificateServiceImplTest {

    private GiftCertificateServiceImpl service;
    private GiftCertificateDao giftCertificateDao;
    private TransactionTemplate transactionTemplate;

    @BeforeEach
    private void setUp() {
        giftCertificateDao = Mockito.mock(GiftCertificateDao.class);
        TagDao tagDao = Mockito.mock(TagDao.class);
        GiftCertificateTagDao giftCertificateTagDao = Mockito.mock(GiftCertificateTagDao.class);
        transactionTemplate = Mockito.mock(TransactionTemplate.class);
        service =
                new GiftCertificateServiceImpl(giftCertificateDao, tagDao, giftCertificateTagDao, transactionTemplate);
    }

    @Test
    void findById() {
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.ofNullable(StaticDataProvider.GIFT_CERTIFICATE));
        Optional<GiftCertificate> actual = service.findById(1L);
        Optional<GiftCertificate> expected = Optional.of(StaticDataProvider.GIFT_CERTIFICATE);
        assertEquals(actual, expected);
    }

    @Test
    void findAllNoLimit() {
        when(giftCertificateDao.findAll()).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_LIST);
        List<GiftCertificate> actual = service.findAll(null, null);
        List<GiftCertificate> expected = StaticDataProvider.GIFT_CERTIFICATE_LIST;
        assertEquals(actual, expected);
    }

    @Test
    void findAllLimit() {
        when(giftCertificateDao.findAll(anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(StaticDataProvider.GIFT_CERTIFICATE));
        List<GiftCertificate> actual = service.findAll(1, 0);
        List<GiftCertificate> expected = Collections.singletonList(StaticDataProvider.GIFT_CERTIFICATE);
        assertEquals(actual, expected);
    }

    @Test
    void add() {
        when(transactionTemplate.execute(any())).thenReturn(StaticDataProvider.GIFT_CERTIFICATE);
        GiftCertificate actual = service.add(StaticDataProvider.ADDING_CERTIFICATE);
        GiftCertificate expected = StaticDataProvider.GIFT_CERTIFICATE;
        assertEquals(actual, expected);
    }

    @Test
    void updateIfExist() {
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.of(StaticDataProvider.GIFT_CERTIFICATE));
        when(transactionTemplate.execute(any())).thenReturn(StaticDataProvider.UPDATED_GIFT_CERTIFICATE);
        Optional<GiftCertificate> actual = service.update(StaticDataProvider.UPDATED_GIFT_CERTIFICATE);
        Optional<GiftCertificate> expected = Optional.of(StaticDataProvider.UPDATED_GIFT_CERTIFICATE);
        assertEquals(actual, expected);
    }

    @Test
    void updateIfNotExist() {
        when(giftCertificateDao.findById(anyLong())).thenReturn(Optional.empty());
        Optional<GiftCertificate> actual = service.update(StaticDataProvider.UPDATED_GIFT_CERTIFICATE);
        Optional<GiftCertificate> expected = Optional.empty();
        assertEquals(actual, expected);
    }

    @Test
    void delete() {
        when(transactionTemplate.execute(any())).thenReturn(true);
        boolean actual = service.delete(1L);
        assertTrue(actual);

    }

    @Test
    void findByTagName() {
        when(giftCertificateDao.findByTagName(anyString())).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_LIST);
        List<GiftCertificate> actual = service.findByTagName("Вышивание", "price", "desc");
        List<GiftCertificate> expected = StaticDataProvider.GIFT_CERTIFICATE_LIST;
        assertEquals(actual, expected);
    }

    @Test
    void findByName() {
        when(giftCertificateDao.findByName(anyString())).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_LIST);
        List<GiftCertificate> actual = service.findByName("Курсы", null, null);
        List<GiftCertificate> expected = StaticDataProvider.GIFT_CERTIFICATE_LIST;
        assertEquals(actual, expected);
    }

    @Test
    void findByDescription() {
        when(giftCertificateDao.findByDescription(anyString())).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_LIST);
        List<GiftCertificate> actual = service.findByDescription("Лучшие", null, null);
        List<GiftCertificate> expected = StaticDataProvider.GIFT_CERTIFICATE_LIST;
        assertEquals(actual, expected);
    }

    @Test
    void updateFieldNameExist() {
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.GIFT_CERTIFICATE));
        when(giftCertificateDao.updateName(1L, "new name")).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_UPDATED_NAME);
        Optional<GiftCertificate> actual =
                service.updateField(1L, new UpdatingField(UpdatingField.FieldName.NAME, "new name"));
        Optional<GiftCertificate> expected = Optional.of(StaticDataProvider.GIFT_CERTIFICATE_UPDATED_NAME);
        assertEquals(actual, expected);
    }

    @Test
    void updateFieldNameNotExist() {
        when(giftCertificateDao.findById(111L)).thenReturn(Optional.empty());
        Optional<GiftCertificate> actual =
                service.updateField(1111L, new UpdatingField(UpdatingField.FieldName.NAME, "new name"));
        Optional<GiftCertificate> expected = Optional.empty();
        assertEquals(actual, expected);
    }

    @Test
    void updateFieldDescriptionExist() {
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.GIFT_CERTIFICATE));
        when(giftCertificateDao.updateDescription(1L, "new description"))
                .thenReturn(StaticDataProvider.GIFT_CERTIFICATE_UPDATED_DESCRIPTION);
        Optional<GiftCertificate> actual =
                service.updateField(1L, new UpdatingField(UpdatingField.FieldName.DESCRIPTION, "new description"));
        Optional<GiftCertificate> expected = Optional.of(StaticDataProvider.GIFT_CERTIFICATE_UPDATED_DESCRIPTION);
        assertEquals(actual, expected);
    }

    @Test
    void updateFieldDescriptionNotExist() {
        when(giftCertificateDao.findById(2222L)).thenReturn(Optional.empty());
        Optional<GiftCertificate> actual =
                service.updateField(2222L, new UpdatingField(UpdatingField.FieldName.DESCRIPTION, "new description"));
        Optional<GiftCertificate> expected = Optional.empty();
        assertEquals(actual, expected);
    }

    @Test
    void updateFieldPriceExist() {
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.GIFT_CERTIFICATE));
        when(giftCertificateDao.updatePrice(1L, 999)).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_UPDATED_PRICE);
        Optional<GiftCertificate> actual =
                service.updateField(1L, new UpdatingField(UpdatingField.FieldName.PRICE, "999"));
        Optional<GiftCertificate> expected = Optional.of(StaticDataProvider.GIFT_CERTIFICATE_UPDATED_PRICE);
        assertEquals(actual, expected);
    }

    @Test
    void updateFieldPriceNotExist() {
        when(giftCertificateDao.findById(22222L)).thenReturn(Optional.empty());
        Optional<GiftCertificate> actual =
                service.updateField(22222L, new UpdatingField(UpdatingField.FieldName.PRICE, "999"));
        Optional<GiftCertificate> expected = Optional.empty();
        assertEquals(actual, expected);
    }

    @Test
    void updateFieldDurationExist() {
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.GIFT_CERTIFICATE));
        when(giftCertificateDao.updateDuration(1L, 365)).thenReturn(StaticDataProvider.GIFT_CERTIFICATE_UPDATED_DURATION);
        Optional<GiftCertificate> actual =
                service.updateField(1L, new UpdatingField(UpdatingField.FieldName.DURATION, "365"));
        Optional<GiftCertificate> expected = Optional.of(StaticDataProvider.GIFT_CERTIFICATE_UPDATED_DURATION);
        assertEquals(actual, expected);
    }

    @Test
    void updateFieldDurationNotExist() {
        when(giftCertificateDao.findById(11111L)).thenReturn(Optional.empty());
        Optional<GiftCertificate> actual =
                service.updateField(11111L, new UpdatingField(UpdatingField.FieldName.DURATION, "365"));
        Optional<GiftCertificate> expected = Optional.empty();
        assertEquals(actual, expected);
    }
}