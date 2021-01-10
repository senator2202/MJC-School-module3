package com.epam.esm.service.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.GiftCertificateTagDao;
import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.DateTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDao orderDao;
    private GiftCertificateDao giftCertificateDao;
    private GiftCertificateTagDao giftCertificateTagDao;
    private TransactionTemplate transactionTemplate;

    @Autowired
    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Autowired
    @Qualifier("jpaGiftCertificateDao")
    public void setGiftCertificateDao(GiftCertificateDao giftCertificateDao) {
        this.giftCertificateDao = giftCertificateDao;
    }

    @Autowired
    public void setGiftCertificateTagDao(GiftCertificateTagDao giftCertificateTagDao) {
        this.giftCertificateTagDao = giftCertificateTagDao;
    }


    @Autowired
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public Order add(Order order) {
        return transactionTemplate.execute(transactionStatus -> {
            GiftCertificate giftCertificate = giftCertificateDao.findById(order.getGiftCertificate().getId()).get();
            order.setCost(giftCertificate.getPrice());
            order.setOrderDate(DateTimeUtility.getCurrentDateIso());
            Order added = orderDao.add(order);
            added.getGiftCertificate().setTags(giftCertificateTagDao.findAllTags(added.getGiftCertificate().getId()));
            return added;
        });
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
