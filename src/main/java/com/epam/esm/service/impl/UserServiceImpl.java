package com.epam.esm.service.impl;

import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserDao dao;

    @Autowired
    public void setDao(UserDao dao) {
        this.dao = dao;
    }

    @Override
    public Optional<User> findById(long id) {
        return dao.findById(id);
    }

    @Override
    public List<User> findAll() {
        return dao.findAll();
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
    public void delete(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<Tag> mostWidelyUsedTagOfUserWithHighestOrdersSum() {
        return dao.mostWidelyUsedTagOfUserWithHighestOrdersSum();
    }
}
