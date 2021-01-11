package com.epam.esm.service.impl;

import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private OrderDao orderDao;
    private TagDao tagDao;

    public UserServiceImpl() {
    }

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Autowired
    public void setTagDao(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Optional<User> findById(long id) {
        return userDao.findById(id);
    }

    @Override
    public List<User> findAll(Integer limit, Integer offset) {
        if (limit != null) {
            return userDao.findAll(limit, offset != null ? offset : 0);
        } else {
            return userDao.findAll();
        }
    }

    @Override
    public User add(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> update(User entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional
    public Optional<Tag> mostWidelyUsedTagOfUserWithHighestOrdersSum() {
        Long userId = userDao.userIdWithHighestOrderSum();
        Long tagId = orderDao.selectMostPopularTagIdOfUser(userId);
        return tagDao.findById(tagId);
    }
}
