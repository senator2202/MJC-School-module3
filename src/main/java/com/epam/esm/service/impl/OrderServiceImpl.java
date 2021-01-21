package com.epam.esm.service.impl;

import com.epam.esm.controller.error_handler.ErrorCode;
import com.epam.esm.controller.exception.GiftEntityNotFoundException;
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
        User user = userDao.findById(order.getUser().getId()).orElseThrow(
                () -> new GiftEntityNotFoundException("User was not found!", ErrorCode.USER_NOT_FOUND)
        );
        GiftCertificate giftCertificate = giftCertificateDao.findById(order.getGiftCertificate().getId()).orElseThrow(
                () -> new GiftEntityNotFoundException("Certificate not found", ErrorCode.GIFT_CERTIFICATE_NOT_FOUND)
        );
        Order orderEntity = new Order();
        orderEntity.setUser(user);
        orderEntity.setGiftCertificate(giftCertificate);
        orderEntity.setCost(giftCertificate.getPrice());
        orderEntity.setOrderDate(DateTimeUtility.getCurrentDateIso());
        return ObjectConverter.toDTO(orderDao.add(orderEntity));
    }

    @Override
    @Transactional
    public Optional<OrderDTO> findUserOrderById(long userId, long orderId) {
        if (userDao.findById(userId).isPresent()) {
            return orderDao.findById(orderId).filter(o -> o.getUser().getId() == userId).map(ObjectConverter::toDTO);
        } else {
            throw new GiftEntityNotFoundException("User not found", ErrorCode.USER_NOT_FOUND);
        }
    }

    @Override
    public List<OrderDTO> findOrdersByUserId(long userId, Integer limit, Integer offset) {
        return ObjectConverter.toOrderDTOs(orderDao.findOrdersByUserId(userId, limit, offset));
    }

    @Override
    public boolean orderBelongsToUser(long userId, long orderId) {
        Optional<Order> optional = orderDao.findById(orderId);
        return optional.filter(order -> order.getUser().getId() == userId).isPresent();
    }
}
