package com.epam.esm.model.dto;

import com.epam.esm.model.entity.GiftEntity;
import org.springframework.hateoas.EntityModel;

import java.math.BigDecimal;
import java.util.Objects;

public class OrderDTO implements GiftEntity {

    private Long id;
    private EntityModel<UserDTO> user;
    private EntityModel<GiftCertificateDTO> giftCertificate;
    private String orderDate;
    private BigDecimal cost;

    public OrderDTO(Long id, EntityModel<UserDTO> user,
                    EntityModel<GiftCertificateDTO> giftCertificate,
                    String orderDate, BigDecimal cost) {
        this.id = id;
        this.user = user;
        this.giftCertificate = giftCertificate;
        this.orderDate = orderDate;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EntityModel<UserDTO> getUser() {
        return user;
    }

    public void setUser(EntityModel<UserDTO> user) {
        this.user = user;
    }

    public EntityModel<GiftCertificateDTO> getGiftCertificate() {
        return giftCertificate;
    }

    public void setGiftCertificate(EntityModel<GiftCertificateDTO> giftCertificateDTO) {
        this.giftCertificate = giftCertificateDTO;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
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
        if (!super.equals(o)) {
            return false;
        }

        OrderDTO orderDTO = (OrderDTO) o;

        if (!Objects.equals(id, orderDTO.id)) {
            return false;
        }
        if (!Objects.equals(user, orderDTO.user)) {
            return false;
        }
        if (!Objects.equals(giftCertificate, orderDTO.giftCertificate)) {
            return false;
        }
        return Objects.equals(cost, orderDTO.cost);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (giftCertificate != null ? giftCertificate.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        return result;
    }
}
