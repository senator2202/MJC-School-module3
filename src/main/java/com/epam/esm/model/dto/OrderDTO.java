package com.epam.esm.model.dto;

import com.epam.esm.model.entity.GiftEntity;
import org.springframework.hateoas.RepresentationModel;

import java.util.Objects;

public class OrderDTO extends RepresentationModel<GiftCertificateDTO> implements GiftEntity {

    private Long id;
    private UserDTO user;
    private GiftCertificateDTO giftCertificateDTO;
    private String orderDate;
    private Integer cost;

    public OrderDTO() {
    }

    public OrderDTO(Long id, UserDTO user, GiftCertificateDTO giftCertificateDTO, String orderDate, Integer cost) {
        this.id = id;
        this.user = user;
        this.giftCertificateDTO = giftCertificateDTO;
        this.orderDate = orderDate;
        this.cost = cost;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public GiftCertificateDTO getGiftCertificate() {
        return giftCertificateDTO;
    }

    public void setGiftCertificate(GiftCertificateDTO giftCertificateDTO) {
        this.giftCertificateDTO = giftCertificateDTO;
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
        if (!Objects.equals(giftCertificateDTO, orderDTO.giftCertificateDTO)) {
            return false;
        }
        return Objects.equals(cost, orderDTO.cost);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (giftCertificateDTO != null ? giftCertificateDTO.hashCode() : 0);
        result = 31 * result + (cost != null ? cost.hashCode() : 0);
        return result;
    }
}
