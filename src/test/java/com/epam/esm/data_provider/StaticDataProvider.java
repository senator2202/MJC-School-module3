package com.epam.esm.data_provider;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;

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
        GIFT_CERTIFICATE_LIST = Collections.singletonList(GIFT_CERTIFICATE);
        TAG_LIST = Collections.singletonList(TAG);
    }
}
