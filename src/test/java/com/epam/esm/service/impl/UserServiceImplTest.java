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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @Mock
    private OrderDao orderDao;

    @Mock
    private TagDao tagDao;

    @InjectMocks
    private final UserService service = new UserServiceImpl(userDao, orderDao, tagDao);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
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

    @Test
    void findAll() {
        when(userDao.findAll(null, null)).thenReturn(StaticDataProvider.USER_LIST);
        List<UserDTO> actual = service.findAll(null, null);
        List<UserDTO> expected = StaticDataProvider.USER_DTO_LIST;
        assertEquals(actual, expected);
    }

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