package com.epam.esm.model.entity;

import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class Order extends RepresentationModel<GiftCertificate> implements GiftEntity {
    private Long id;
    private User user;
    private GiftCertificate giftCertificate;
    private String orderDate;
    private int cost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GiftCertificate getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(GiftCertificate giftCertificate) {
        this.giftCertificate = giftCertificate;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Order order = (Order) o;

        if (cost != order.cost) {
            return false;
        }
        if (!Objects.equals(id, order.id)) {
            return false;
        }
        if (!Objects.equals(user, order.user)) {
            return false;
        }
        if (!Objects.equals(giftCertificate, order.giftCertificate)) {
            return false;
        }
        return Objects.equals(orderDate, order.orderDate);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (giftCertificate != null ? giftCertificate.hashCode() : 0);
        result = 31 * result + (orderDate != null ? orderDate.hashCode() : 0);
        result = 31 * result + cost;
        return result;
    }
}
