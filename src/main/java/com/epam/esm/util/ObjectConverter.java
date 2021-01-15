package com.epam.esm.util;

import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class ObjectConverter {

    private ObjectConverter() {
    }

    public static TagDTO toDTO(Tag tag) {
        return new TagDTO(tag.getId(), tag.getName());
    }

    public static Tag toEntity(TagDTO tagDTO) {
        return new Tag(tagDTO.getId(), tagDTO.getName());
    }

    public static GiftCertificateDTO toDTO(GiftCertificate giftCertificate) {
        return new GiftCertificateDTO(
                giftCertificate.getId(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(),
                giftCertificate.getTags().stream().map(ObjectConverter::toDTO).collect(Collectors.toList())
        );
    }

    public static GiftCertificate toEntity(GiftCertificateDTO giftCertificate) {
        return new GiftCertificate(
                giftCertificate.getId(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(),
                giftCertificate.getTags().stream().map(ObjectConverter::toEntity).collect(Collectors.toList())
        );
    }

    public static List<GiftCertificateDTO> toGiftCertificateDTOs(List<GiftCertificate> giftCertificates) {
        return giftCertificates.stream().map(ObjectConverter::toDTO).collect(Collectors.toList());
    }

    public static UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName());
    }

    public static List<UserDTO> toUserDTOs(List<User> users) {
        return users.stream().map(ObjectConverter::toDTO).collect(Collectors.toList());
    }

    public static OrderDTO toDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                toDTO(order.getUser()),
                toDTO(order.getGiftCertificate()),
                order.getOrderDate(),
                order.getCost());
    }

    public static List<OrderDTO> toOrderDTOs(List<Order> orders) {
        return orders.stream().map(ObjectConverter::toDTO).collect(Collectors.toList());
    }
}
