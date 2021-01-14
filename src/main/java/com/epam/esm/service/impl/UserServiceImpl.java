package com.epam.esm.service.impl;

import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ObjectConverter;
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

    @Autowired
    public UserServiceImpl(UserDao userDao, OrderDao orderDao, TagDao tagDao) {
        this.userDao = userDao;
        this.orderDao = orderDao;
        this.tagDao = tagDao;
    }

    @Override
    public Optional<UserDTO> findById(long id) {
        return userDao.findById(id).map(ObjectConverter::toDTO);
    }

    @Override
    public List<UserDTO> findAll(Integer limit, Integer offset) {
        if (limit != null) {
            return ObjectConverter.toUserDTOs(userDao.findAll(limit, offset != null ? offset : 0));
        } else {
            return ObjectConverter.toUserDTOs(userDao.findAll());
        }
    }

    @Override
    public UserDTO add(UserDTO entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<UserDTO> update(UserDTO entity) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional
    public Optional<TagDTO> mostWidelyUsedTagOfUserWithHighestOrdersSum() {
        Long userId = userDao.userIdWithHighestOrderSum();
        Long tagId = orderDao.selectMostPopularTagIdOfUser(userId);
        return tagDao.findById(tagId).map(ObjectConverter::toDTO);
    }
}
