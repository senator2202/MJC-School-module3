package com.epam.esm.data_provider;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StaticDataProvider {

    public static final String ISO_DATE = "2020-12-16T14:48Z";
    public static final Tag TAG;
    public static final Tag ADDING_TAG;
    public static final Tag UPDATED_TAG;
    public static final GiftCertificate ADDING_CERTIFICATE;
    public static final GiftCertificate GIFT_CERTIFICATE;
    public static final GiftCertificate UPDATED_GIFT_CERTIFICATE;
    public static final List<GiftCertificate> GIFT_CERTIFICATE_LIST;
    public static final List<Tag> TAG_LIST;
    public static final User USER;
    public static final List<User> USER_LIST_LIMIT;
    public static final List<User> USER_LIST_NO_LIMIT;
    public static final Order ORDER;
    public static final List<Order> ORDER_LIST;
    public static final GiftCertificate GIFT_CERTIFICATE_UPDATED_NAME;
    public static final GiftCertificate GIFT_CERTIFICATE_UPDATED_DESCRIPTION;
    public static final GiftCertificate GIFT_CERTIFICATE_UPDATED_PRICE;
    public static final GiftCertificate GIFT_CERTIFICATE_UPDATED_DURATION;

    static {
        TAG = new Tag(1, "Вязание");
        ADDING_TAG = new Tag();
        ADDING_TAG.setName("Вязание");
        UPDATED_TAG = new Tag(TAG.getId(), "Вышивание");
        ADDING_CERTIFICATE = new GiftCertificate();
        ADDING_CERTIFICATE.setName("Курсы вышивания");
        ADDING_CERTIFICATE.setDescription("Лучшие курсы вышивания от компании Vyazhem Vmeste");
        ADDING_CERTIFICATE.setDuration(30);
        ADDING_CERTIFICATE.setPrice(100);
        ADDING_CERTIFICATE.setTags(Collections.singletonList(TAG));
        GIFT_CERTIFICATE = new GiftCertificate(ADDING_CERTIFICATE);
        GIFT_CERTIFICATE.setId(1L);
        GIFT_CERTIFICATE.setCreateDate(ISO_DATE);
        GIFT_CERTIFICATE.setLastUpdateDate(ISO_DATE);
        UPDATED_GIFT_CERTIFICATE = new GiftCertificate(GIFT_CERTIFICATE);
        UPDATED_GIFT_CERTIFICATE.setPrice(60);
        GIFT_CERTIFICATE_LIST = Arrays.asList(GIFT_CERTIFICATE, GIFT_CERTIFICATE);
        TAG_LIST = Arrays.asList(TAG, TAG);
        USER = new User(1L, "Alexey");
        USER_LIST_NO_LIMIT = Arrays.asList(USER, USER);
        USER_LIST_LIMIT = Collections.singletonList(USER);
        ORDER = new Order(1L, USER, GIFT_CERTIFICATE, ISO_DATE, 120);
        ORDER_LIST = Arrays.asList(ORDER, ORDER);
        GIFT_CERTIFICATE_UPDATED_NAME = new GiftCertificate(GIFT_CERTIFICATE);
        GIFT_CERTIFICATE.setName("new name");
        GIFT_CERTIFICATE_UPDATED_DESCRIPTION = new GiftCertificate(GIFT_CERTIFICATE);
        GIFT_CERTIFICATE.setDescription("new description");
        GIFT_CERTIFICATE_UPDATED_PRICE = new GiftCertificate(GIFT_CERTIFICATE);
        GIFT_CERTIFICATE.setPrice(999);
        GIFT_CERTIFICATE_UPDATED_DURATION = new GiftCertificate(GIFT_CERTIFICATE);
        GIFT_CERTIFICATE.setDuration(365);
    }
}
