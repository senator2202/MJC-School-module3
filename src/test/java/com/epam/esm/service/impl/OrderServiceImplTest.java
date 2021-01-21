package com.epam.esm.service.impl;

import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.entity.Order;
import com.epam.esm.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {

    private OrderService service;
    private OrderDao orderDao;
    private UserDao userDao;
    private GiftCertificateDao giftCertificateDao;

    @BeforeEach
    void setUp() {
        orderDao = Mockito.mock(OrderDao.class);
        userDao = Mockito.mock(UserDao.class);
        giftCertificateDao = Mockito.mock(GiftCertificateDao.class);
        service = new OrderServiceImpl(orderDao, userDao, giftCertificateDao);
    }

    @Test
    void add() {
        when(userDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.USER));
        when(giftCertificateDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.GIFT_CERTIFICATE));
        when(orderDao.add(any(Order.class))).thenReturn(StaticDataProvider.ORDER);
        OrderDTO actual = service.add(StaticDataProvider.ORDER_DTO);
        OrderDTO expected = StaticDataProvider.ORDER_DTO;
        assertEquals(actual, expected);
    }

    @Test
    void findByIdExisting() {
        when(orderDao.findById(1L)).thenReturn(Optional.of(StaticDataProvider.ORDER));
        Optional<OrderDTO> actual = service.findUserOrderById(1L, 1L );
        Optional<OrderDTO> expected = Optional.of(StaticDataProvider.ORDER_DTO);
        assertEquals(actual, expected);
    }

    /*@Test
    void findByIdNotExisting() {
        when(orderDao.findById(11111L)).thenReturn(Optional.empty());
        Optional<OrderDTO> actual = service.findUserOrderById(, 11111L, );
        Optional<OrderDTO> expected = Optional.empty();
        assertEquals(actual, expected);
    }*/

    @Test
    void findOrdersByUserIdLimit() {
        when(orderDao.findOrdersByUserId(1L, 2, 0)).thenReturn(StaticDataProvider.ORDER_LIST_LIMIT);
        List<OrderDTO> actual = service.findOrdersByUserId(1L, 2, null);
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