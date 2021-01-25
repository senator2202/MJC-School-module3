package com.epam.esm.model.dao.impl;

import com.epam.esm.model.dao.GiftCertificateDao;
import com.epam.esm.model.dao.TagDao;
import com.epam.esm.model.dao.UserDao;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;
import com.epam.esm.util.DateTimeUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Repository
@Transactional
public class FillDao {

    private String[] userNames = {"Alex", "Petr", "Ivan", "Darya", "Alina", "Sergei", "Nikita", "Olga",
            "Valerii", "Stepan", "Andrei", "Irina", "Maria", "John", "Adam"};

    private String[] tagNames = {"Party", "Ski", "Education", "Mountain", "Literature", "Book", "Tickets",
            "Football", "Basketball", "Volleyball", "Hockey", "Training", "Gym", "Piano", "Guitar", "Songs"};

    private String[] certificateNames = {"Pilling", "Super", "Puper", "Voyage", "Egypt", "Flowers", "Berlin",
            "Restaurant", "Bar", "Minsk", "Russia", "USA", "Ireland", "China", "Canada"};

    private String[] descriptions = {"lalala", "tratata", "gogogo", "fefefe", "dododo", "mememe", "tututu",
            "sososo", "lowlowlow", "sesese", "gavgavgav", "seekseekseek", "doordoordoor"};

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private GiftCertificateDao giftCertificateDao;

    @Transactional
    public void fill() {
        fillTags();
        fillUsers();
        fillCertificates();
        fillOrders();
    }

    private void fillUsers() {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            String name = userNames[random.nextInt(userNames.length)];
            name += random.nextInt(1000);
            User user = new User();
            user.setName(name);
            entityManager.persist(user);
        }
    }

    private void fillCertificates() {
        Random random = new Random();
        List<Tag> tags = tagDao.findAll(null, null);
        for (int i = 0; i < 10000; i++) {
            int tagsNumber = random.nextInt(5);
            GiftCertificate newCertificate = new GiftCertificate();
            List<Tag> newTags = new ArrayList<>();
            for (int j = 0; j <= tagsNumber; j++) {
                Tag tag = tags.get(random.nextInt(tags.size()));
                newTags.add(tag);
            }
            newCertificate.setName(certificateNames[random.nextInt(certificateNames.length)] + random.nextInt(1000));
            newCertificate.setDescription(descriptions[random.nextInt(descriptions.length)] + random.nextInt(1000));
            newCertificate.setPrice(BigDecimal.valueOf(random.nextInt(5000) + 25));
            newCertificate.setDuration(random.nextInt(300) + 30);
            String nowIso = DateTimeUtility.getCurrentDateIso();
            newCertificate.setCreateDate(nowIso);
            newCertificate.setLastUpdateDate(nowIso);
            newCertificate.setTags(newTags);
            entityManager.persist(newCertificate);
        }
    }

    private void fillTags() {
        Random random = new Random();
        for (int i = 0; i < 1000; i++) {
            String name = tagNames[random.nextInt(tagNames.length)];
            name += random.nextInt(100000);
            Tag tag = new Tag();
            tag.setName(name);
            entityManager.persist(tag);
        }
    }

    private void fillOrders() {
        Random random = new Random();
        List<GiftCertificate> giftCertificates = giftCertificateDao.findAll(null, null, null, null, null, null, null);
        List<User> users = userDao.findAll(null, null);
        for (int i = 0; i < 20000; i++) {
            Order order = new Order();
            GiftCertificate giftCertificate = giftCertificates.get(random.nextInt(giftCertificates.size()));
            User user = users.get(random.nextInt(users.size()));
            order.setUser(user);
            order.setGiftCertificate(giftCertificate);
            order.setCost(giftCertificate.getPrice());
            order.setOrderDate(DateTimeUtility.getCurrentDateIso());
            entityManager.persist(order);
        }
    }
}
