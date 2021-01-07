package com.epam.esm.service.impl;

import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.entity.Order;
import com.epam.esm.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class OrderServiceImplTest {

    private OrderService service;
    private TransactionTemplate transactionTemplate;
    private OrderDao orderDao;

    @BeforeEach
    void setUp() {
        transactionTemplate = Mockito.mock(TransactionTemplate.class);
        orderDao = Mockito.mock(OrderDao.class);
        service = new OrderServiceImpl();
        ((OrderServiceImpl) service).setOrderDao(orderDao);
        ((OrderServiceImpl) service).setTransactionTemplate(transactionTemplate);
    }

    @Test
    void add() {
        when(transactionTemplate.execute(any())).thenReturn(StaticDataProvider.ORDER);
        Order actual = service.add(new Order());
        Order expected = StaticDataProvider.ORDER;
        assertEquals(actual, expected);
    }

    @Test
    void findByIdExist() {
        when(orderDao.findById(anyLong())).thenReturn(Optional.of(StaticDataProvider.ORDER));
        Optional<Order> actual = service.findById(1L);
        Optional<Order> expected = Optional.of(StaticDataProvider.ORDER);
        assertEquals(actual, expected);
    }

    @Test
    void findByIdNotExist() {
        when(orderDao.findById(anyLong())).thenReturn(Optional.empty());
        Optional<Order> actual = service.findById(1111L);
        Optional<Order> expected = Optional.empty();
        assertEquals(actual, expected);
    }

    @Test
    void findOrdersByUserIdNoLimit() {
        when(orderDao.findOrdersByUserId(anyLong())).thenReturn(StaticDataProvider.ORDER_LIST);
        List<Order> actual = service.findOrdersByUserId(1L, null, null);
        List<Order> expected = StaticDataProvider.ORDER_LIST;
        assertEquals(actual, expected);
    }

    @Test
    void findOrdersByUserIdLimit() {
        when(orderDao.findOrdersByUserId(anyLong(), anyInt(), anyInt()))
                .thenReturn(Collections.singletonList(StaticDataProvider.ORDER));
        List<Order> actual = service.findOrdersByUserId(1L, 1, 0);
        List<Order> expected = Collections.singletonList(StaticDataProvider.ORDER);
        assertEquals(actual, expected);
    }

    @Test
    void orderBelongsToUserTrue() {
        when(orderDao.findById(anyLong())).thenReturn(Optional.of(StaticDataProvider.ORDER));
        boolean actual = service.orderBelongsToUser(1L, 1L);
        assertTrue(actual);
    }

    @Test
    void orderBelongsToUserFalse() {
        when(orderDao.findById(anyLong())).thenReturn(Optional.of(StaticDataProvider.ORDER));
        boolean actual = service.orderBelongsToUser(22L, 1L);
        assertFalse(actual);
    }
}