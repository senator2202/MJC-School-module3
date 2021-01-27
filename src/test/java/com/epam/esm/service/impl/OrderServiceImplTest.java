package com.epam.esm.service.impl;

import com.epam.esm.controller.exception.GiftEntityNotFoundException;
import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.entity.Order;
import com.epam.esm.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {

    @Mock
    private OrderDao orderDao;

    @Mock
    private UserDao userDao;

    @Mock
    private GiftCertificateDao giftCertificateDao;

    @InjectMocks
    private final OrderService service = new OrderServiceImpl(orderDao, userDao, giftCertificateDao);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addExistingUserExistingCertificate() {
        when(userDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.USER));
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.GIFT_CERTIFICATE));
        when(orderDao.add(any(Order.class))).thenReturn(StaticDataProvider.ORDER);
        OrderDTO actual = service.add(1L, 1L);
        OrderDTO expected = StaticDataProvider.ORDER_DTO;
        assertEquals(actual, expected);
    }

    @Test
    void addUserNotExisting() {
        when(userDao.findById(11111L)).thenReturn(Optional.empty());
        assertThrows(GiftEntityNotFoundException.class, () -> service.add(11111L, 1L));
    }

    @Test
    void addCertificateNotExisting() {
        when(userDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.USER));
        when(giftCertificateDao.findById(11111L)).thenReturn(Optional.empty());
        assertThrows(GiftEntityNotFoundException.class, () -> service.add(1L, 11111L));
    }

    @Test
    void findByIdExisting() {
        when(userDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.USER));
        when(orderDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.ORDER));
        Optional<OrderDTO> actual = service.findUserOrderById(1L, 1L);
        Optional<OrderDTO> expected = Optional.of(StaticDataProvider.ORDER_DTO);
        assertEquals(actual, expected);
    }

    @Test
    void findByIdUserNotExisting() {
        when(userDao.findById(11111L)).thenReturn(Optional.empty());
        assertThrows(GiftEntityNotFoundException.class, () -> service.findUserOrderById(11111L, 1L));
        /*Optional<OrderDTO> actual = service.findUserOrderById(11111L, 1L);
        Optional<OrderDTO> expected = Optional.empty();
        assertEquals(actual, expected);*/
    }

    @Test
    void findOrdersByUserIdLimit() {
        when(orderDao.findOrdersByUserId(1L, 2, 0)).thenReturn(StaticDataProvider.ORDER_LIST_LIMIT);
        List<OrderDTO> actual = service.findOrdersByUserId(1L, 2, 0);
        List<OrderDTO> expected = StaticDataProvider.ORDER_DTO_LIST_LIMIT;
        assertEquals(actual, expected);
    }

    @Test
    void findOrdersByUserIdLimitOffset() {
        when(orderDao.findOrdersByUserId(1L, 2, 10)).thenReturn(StaticDataProvider.ORDER_LIST_LIMIT);
        List<OrderDTO> actual = service.findOrdersByUserId(1L, 2, 10);
        List<OrderDTO> expected = StaticDataProvider.ORDER_DTO_LIST_LIMIT;
        assertEquals(actual, expected);
    }

    @Test
    void orderBelongsToUser() {
        when(orderDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.ORDER));
        assertTrue(service.orderBelongsToUser(1L, 1L));
    }
}