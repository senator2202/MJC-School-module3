package com.epam.esm.service.impl;

import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class TagServiceImplTest {

    private TagService service;
    private TagDao tagDao;

    @BeforeEach
    void setUp() {
        tagDao = Mockito.mock(TagDao.class);
        service = new TagServiceImpl(tagDao);
    }

    @Test
    void findByIdExisting() {
        when(tagDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.TAG));
        Optional<TagDTO> actual = service.findById(1L);
        Optional<TagDTO> expected = Optional.of(StaticDataProvider.TAG_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void findByIdNotExisting() {
        when(tagDao.findById(11111L)).thenReturn(Optional.empty());
        Optional<TagDTO> actual = service.findById(11111L);
        Optional<TagDTO> expected = Optional.empty();
        assertEquals(actual, expected);
    }

    @Test
    void findAll() {
        when(tagDao.findAll()).thenReturn(StaticDataProvider.TAG_LIST);
        List<TagDTO> actual = service.findAll(null, null);
        List<TagDTO> expected = StaticDataProvider.TAG_DTO_LIST;
        assertEquals(actual, expected);
    }

    @Test
    void findAllLimit() {
        when(tagDao.findAll(2, 0)).thenReturn(StaticDataProvider.TAG_LIST_LIMIT);
        List<TagDTO> actual = service.findAll(2, null);
        List<TagDTO> expected = StaticDataProvider.TAG_DTO_LIST_LIMIT;
        assertEquals(actual, expected);
    }

    @Test
    void findAllLimitOffset() {
        when(tagDao.findAll(2, 10)).thenReturn(StaticDataProvider.TAG_LIST_LIMIT);
        List<TagDTO> actual = service.findAll(2, 10);
        List<TagDTO> expected = StaticDataProvider.TAG_DTO_LIST_LIMIT;
        assertEquals(actual, expected);
    }

    @Test
    void addExisting() {
        when(tagDao.findByName("Вязание")).thenReturn(Optional.ofNullable(StaticDataProvider.TAG));
        TagDTO actual = service.add(StaticDataProvider.TAG_DTO);
        TagDTO expected = StaticDataProvider.TAG_DTO;
        assertEquals(actual, expected);
    }

    @Test
    void addNotExisting() {
        when(tagDao.findByName("Baseball")).thenReturn(Optional.empty());
        when(tagDao.add(StaticDataProvider.ADDING_TAG)).thenReturn(StaticDataProvider.ADDED_TAG);
        TagDTO actual = service.add(StaticDataProvider.ADDING_TAG_DTO);
        TagDTO expected = StaticDataProvider.ADDED_TAG_DTO;
        assertEquals(actual, expected);
    }

    @Test
    void updateExisting() {
        when(tagDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.TAG));
        when(tagDao.update(StaticDataProvider.TAG)).thenReturn(StaticDataProvider.TAG);
        Optional<TagDTO> actual = service.update(StaticDataProvider.TAG_DTO);
        Optional<TagDTO> expected = Optional.of(StaticDataProvider.TAG_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void updateNotExisting() {
        when(tagDao.findById(anyLong())).thenReturn(Optional.empty());
        Optional<TagDTO> actual = service.update(StaticDataProvider.ADDED_TAG_DTO);
        Optional<TagDTO> expected = Optional.empty();
        assertEquals(actual, expected);
    }

    @Test
    void deleteTrue() {
        when(tagDao.delete(1L)).thenReturn(true);
        assertTrue(service.delete(1L));
    }

    @Test
    void deleteFalse() {
        when(tagDao.delete(11111L)).thenReturn(false);
        assertFalse(service.delete(11111L));
    }
}