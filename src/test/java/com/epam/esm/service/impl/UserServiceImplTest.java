package com.epam.esm.service.impl;

import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private UserService service;
    private UserDao userDao;
    private OrderDao orderDao;
    private TagDao tagDao;

    @BeforeEach
    void setUp() {
        userDao = Mockito.mock(UserDao.class);
        orderDao = Mockito.mock(OrderDao.class);
        tagDao = Mockito.mock(TagDao.class);
        service = new UserServiceImpl(userDao, orderDao, tagDao);
    }

    @Test
    void findByIdExisting() {
        when(userDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.USER));
        Optional<UserDTO> actual = service.findById(1L);
        Optional<UserDTO> expected = Optional.of(StaticDataProvider.USER_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void findByIdNotExisting() {
        when(userDao.findById(11111L)).thenReturn(Optional.empty());
        Optional<UserDTO> actual = service.findById(11111L);
        Optional<UserDTO> expected = Optional.empty();
        assertEquals(actual, expected);
    }

    /*@Test
    void findAll() {
        when(userDao.findAll()).thenReturn(StaticDataProvider.USER_LIST);
        List<UserDTO> actual = service.findAll(name, description, tagName, sortType, direction, null, null);
        List<UserDTO> expected = StaticDataProvider.USER_DTO_LIST;
        assertEquals(actual, expected);
    }

    @Test
    void findAllLimit() {
        when(userDao.findAll(2, 0)).thenReturn(StaticDataProvider.USER_LIST_LIMIT);
        List<UserDTO> actual = service.findAll(name, description, tagName, sortType, direction, 2, null);
        List<UserDTO> expected = StaticDataProvider.USER_DTO_LIST_LIMIT;
        assertEquals(actual, expected);
    }

    @Test
    void findAllLimitOffset() {
        when(userDao.findAll(2, 10)).thenReturn(StaticDataProvider.USER_LIST_LIMIT);
        List<UserDTO> actual = service.findAll(name, description, tagName, sortType, direction, 2, 10);
        List<UserDTO> expected = StaticDataProvider.USER_DTO_LIST_LIMIT;
        assertEquals(actual, expected);
    }*/

    /*@Test
    void add() {
        assertThrows(UnsupportedOperationException.class, () -> service.add(StaticDataProvider.USER_DTO));
    }

    @Test
    void update() {
        assertThrows(UnsupportedOperationException.class, () -> service.update(StaticDataProvider.USER_DTO));
    }

    @Test
    void delete() {
        assertThrows(UnsupportedOperationException.class, () -> service.delete(1L));
    }*/

    @Test
    void mostWidelyUsedTagOfUserWithHighestOrdersSum() {
        when(userDao.userIdWithHighestOrderSum()).thenReturn(1L);
        when(orderDao.selectMostPopularTagIdOfUser(1L)).thenReturn(1L);
        when(tagDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.TAG));
        Optional<TagDTO> actual = service.mostWidelyUsedTagOfUserWithHighestOrdersSum();
        Optional<TagDTO> expected = Optional.of(StaticDataProvider.TAG_DTO);
        assertEquals(actual, expected);
    }
}