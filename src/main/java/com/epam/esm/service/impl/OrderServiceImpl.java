package com.epam.esm.service.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.GiftCertificateTagDao;
import com.epam.esm.model.dao.OrderDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.service.OrderService;
import com.epam.esm.util.DateTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

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
}
