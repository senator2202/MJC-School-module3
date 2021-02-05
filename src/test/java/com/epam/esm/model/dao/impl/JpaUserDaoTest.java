package com.epam.esm.model.dao.impl;

import com.epam.esm.app.SpringBootRestApplication;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SpringBootRestApplication.class)
class JpaUserDaoTest {

    @Autowired
    private UserDao userDao;

    static Stream<Arguments> argsFindById() {
        return Stream.of(
                Arguments.of(1L, true),
                Arguments.of(999L, false)
        );
    }

    static Stream<Arguments> argsFindAll() {
        return Stream.of(
                Arguments.of(null, null, 6),
                Arguments.of(3, null, 3),
                Arguments.of(3, 4, 2),
                Arguments.of(10, 20, 0)
        );
    }

    @Test
    void userIdWithHighestOrderSum() {
        assertEquals(3L, userDao.userIdWithHighestOrderSum());
    }

    @ParameterizedTest
    @MethodSource("argsFindById")
    void testFindById(Long id, boolean result) {
        Optional<User> optional = userDao.findById(id);
        assertEquals(result, optional.isPresent());
    }

    @ParameterizedTest
    @MethodSource("argsFindAll")
    void findAll(Integer limit, Integer offset, int actualSize) {
        List<User> allTags = userDao.findAll(limit, offset);
        assertEquals(actualSize, allTags.size());
    }
}