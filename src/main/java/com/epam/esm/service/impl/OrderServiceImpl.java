package com.epam.esm.service.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.DateTimeUtility;
import com.epam.esm.util.ObjectConverter;
import org.springframework.beans.factory.annotation.Autowired;
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
    public OrderServiceImpl(OrderDao orderDao, UserDao userDao, GiftCertificateDao giftCertificateDao) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    @Transactional
    public OrderDTO add(OrderDTO order) {
        User user = userDao.findById(order.getUser().getId()).get();
        GiftCertificate giftCertificate = giftCertificateDao.findById(order.getGiftCertificate().getId()).get();
        Order orderEntity = new Order();
        orderEntity.setUser(user);
        orderEntity.setGiftCertificate(giftCertificate);
        orderEntity.setCost(giftCertificate.getPrice());
        orderEntity.setOrderDate(DateTimeUtility.getCurrentDateIso());
        return ObjectConverter.toDTO(orderDao.add(orderEntity));
    }

    @Override
    public Optional<OrderDTO> findById(long id) {
        return orderDao.findById(id).map(ObjectConverter::toDTO);
    }

    @Override
    public List<OrderDTO> findOrdersByUserId(long userId, Integer limit, Integer offset) {
        if (limit != null) {
            return ObjectConverter.toOrderDTOs(orderDao.findOrdersByUserId(userId, limit, offset != null ? offset : 0));
        } else {
            return ObjectConverter.toOrderDTOs(orderDao.findOrdersByUserId(userId));
        }
    }

    @Override
    public boolean orderBelongsToUser(long userId, long orderId) {
        Optional<Order> optional = orderDao.findById(orderId);
        return optional.filter(order -> order.getUser().getId() == userId).isPresent();
    }
}
