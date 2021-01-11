package com.epam.esm.service.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.DateTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private UserDao userDao;
    private GiftCertificateDao giftCertificateDao;

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Autowired
    @Qualifier("jpaUserDao")
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Autowired
    public void setGiftCertificateDao(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    @Transactional
    public Order add(Order order) {
        User user = userDao.findById(order.getUser().getId()).get();
        GiftCertificate giftCertificate = giftCertificateDao.findById(order.getGiftCertificate().getId()).get();
        order.setUser(user);
        order.setGiftCertificate(giftCertificate);
        order.setCost(giftCertificate.getPrice());
        order.setOrderDate(DateTimeUtility.getCurrentDateIso());
        return orderDao.add(order);
    }

    @Override
    public Optional<Order> findById(long id) {
        return orderDao.findById(id);
    }

    @Override
    public List<Order> findOrdersByUserId(long userId, Integer limit, Integer offset) {
        if (limit != null) {
            return orderDao.findOrdersByUserId(userId, limit, offset != null ? offset : 0);
        } else {
            return orderDao.findOrdersByUserId(userId);
        }
    }

    @Override
    public boolean orderBelongsToUser(long userId, long orderId) {
        Optional<Order> optional = orderDao.findById(orderId);
        return optional.filter(order -> order.getUser().getId() == userId).isPresent();
    }
}
