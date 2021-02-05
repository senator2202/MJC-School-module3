package com.epam.esm.model.dao.impl;

import com.epam.esm.app.SpringBootRestApplication;
import com.epam.esm.data_provider.StaticDataProvider;
import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.entity.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(classes = SpringBootRestApplication.class)
class JpaOrderDaoTest {

    @Autowired
    private OrderDao dao;

    static Stream<Arguments> argsFindUserOrders() {
        return Stream.of(
                Arguments.of(null, null, 7),
                Arguments.of(5, null, 5),
                Arguments.of(10, 5, 2),
                Arguments.of(10, 20, 0)
        );
    }

    @Test
    @DirtiesContext
    void add() {
        Order created = dao.add(StaticDataProvider.ADDING_ORDER);
        List<Order> orders = dao.findOrdersByUserId(1L, null, null);
        assertTrue(created.getId() != null && orders.size() == 8);
    }

    @Test
    void findById() {
        assertTrue(dao.findById(1L).isPresent());
    }

    @ParameterizedTest
    @MethodSource("argsFindUserOrders")
    void findOrdersByUserId(Integer limit, Integer offset, int size) {
        assertEquals(size, dao.findOrdersByUserId(1L, limit, offset).size());
    }

    @Test
    void selectMostPopularTagIdOfUser() {
        assertEquals(3L, dao.selectMostPopularTagIdOfUser(3L));
    }
}