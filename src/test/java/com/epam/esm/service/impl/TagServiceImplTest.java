package com.epam.esm.service.impl;

import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dao.GiftCertificateTagDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class TagServiceImplTest {

    private TagService service;
    private TagDao tagDao;
    private TransactionTemplate transactionTemplate;

    @BeforeEach
    void setUp() {
        tagDao = Mockito.mock(TagDao.class);
        transactionTemplate = Mockito.mock(TransactionTemplate.class);
        GiftCertificateTagDao giftCertificateTagDao = Mockito.mock(GiftCertificateTagDao.class);
        service = new TagServiceImpl(tagDao, giftCertificateTagDao, transactionTemplate);
    }


    @Test
    void findById() {
        when(tagDao.findById(anyLong())).thenReturn(Optional.ofNullable(StaticDataProvider.TAG));
        Optional<Tag> actual = service.findById(1L);
        Optional<Tag> expected = Optional.of(StaticDataProvider.TAG);
        assertEquals(actual, expected);
    }

    @Test
    void findAll() {
        when(tagDao.findAll()).thenReturn(StaticDataProvider.TAG_LIST);
        List<Tag> actual = service.findAll(null, null);
        List<Tag> expected = StaticDataProvider.TAG_LIST;
        assertEquals(actual, expected);
    }

    @Test
    void add() {
        when(tagDao.add(StaticDataProvider.ADDING_TAG)).thenReturn(StaticDataProvider.TAG);
        Tag actual = service.add(StaticDataProvider.ADDING_TAG);
        Tag expected = StaticDataProvider.TAG;
        assertEquals(actual, expected);
    }

    @Test
    void updateIfExist() {
        when(tagDao.findById(anyLong())).thenReturn(Optional.of(StaticDataProvider.TAG));
        when(tagDao.update(StaticDataProvider.UPDATED_TAG)).thenReturn(StaticDataProvider.UPDATED_TAG);
        Optional<Tag> actual = service.update(StaticDataProvider.UPDATED_TAG);
        Optional<Tag> expected = Optional.of(StaticDataProvider.UPDATED_TAG);
        assertEquals(actual, expected);
    }

    @Test
    void updateIfNotExist() {
        when(tagDao.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Tag> actual = service.update(StaticDataProvider.UPDATED_TAG);
        Optional<Tag> expected = Optional.empty();
        assertEquals(actual, expected);
    }

    @Test
    void delete() {
        when(transactionTemplate.execute(any())).thenReturn(true);
        boolean actual = service.delete(1L);
        assertTrue(actual);
    }
}