package com.epam.esm.service.impl;

import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    private UserDao userDao;
    private UserService service;

    @BeforeEach
    void setUp() {
        userDao = Mockito.mock(UserDao.class);
        service = new UserServiceImpl(userDao);
    }

    @Test
    void findByIdExist() {
        when(userDao.findById(anyLong())).thenReturn(Optional.ofNullable(StaticDataProvider.USER));
        Optional<User> actual = service.findById(1L);
        Optional<User> expected = Optional.of(StaticDataProvider.USER);
        assertEquals(actual, expected);
    }

    @Test
    void findByIdNotExist() {
        when(userDao.findById(anyLong())).thenReturn(Optional.empty());
        Optional<User> actual = service.findById(11111L);
        Optional<User> expected = Optional.empty();
        assertEquals(actual, expected);
    }

    @Test
    void findAllNoLimit() {
        when(userDao.findAll()).thenReturn(StaticDataProvider.USER_LIST_NO_LIMIT);
        List<User> actual = service.findAll(null, null);
        List<User> expected = StaticDataProvider.USER_LIST_NO_LIMIT;
        assertEquals(actual, expected);
    }

    @Test
    void findAllLimit() {
        when(userDao.findAll(anyInt(), anyInt())).thenReturn(StaticDataProvider.USER_LIST_LIMIT);
        List<User> actual = service.findAll(2, 0);
        List<User> expected = StaticDataProvider.USER_LIST_LIMIT;
        assertEquals(actual, expected);
    }

    @Test
    void add() {
        assertThrows(UnsupportedOperationException.class, () -> service.add(StaticDataProvider.USER));
    }

    @Test
    void update() {
        assertThrows(UnsupportedOperationException.class, () -> service.update(StaticDataProvider.USER));
    }

    @Test
    void delete() {
        assertThrows(UnsupportedOperationException.class, () -> service.delete(1L));
    }

    @Test
    void mostWidelyUsedTagOfUserWithHighestOrdersSum() {
        when(service.mostWidelyUsedTagOfUserWithHighestOrdersSum()).thenReturn(Optional.of(StaticDataProvider.TAG));
        Optional<Tag> actual = service.mostWidelyUsedTagOfUserWithHighestOrdersSum();
        Optional<Tag> expected = Optional.of(StaticDataProvider.TAG);
        assertEquals(actual, expected);
    }
}