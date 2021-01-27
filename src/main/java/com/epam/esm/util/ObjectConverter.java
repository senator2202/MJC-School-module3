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

/**
 * Utility class with methods, converting objects from DTO to Entities and back.
 */
public class ObjectConverter {

    private ObjectConverter() {
    }

    /**
     * To dto tag dto.
     *
     * @param tag the tag
     * @return the tag dto
     */
    public static TagDTO toTagDTO(Tag tag) {
        return new TagDTO(tag.getId(), tag.getName());
    }

    /**
     * To entity tag.
     *
     * @param tagDTO the tag dto
     * @return the tag
     */
    public static Tag toTagEntity(TagDTO tagDTO) {
        return new Tag(tagDTO.getId(), tagDTO.getName());
    }

    /**
     * To dto gift certificate dto.
     *
     * @param giftCertificate the gift certificate
     * @return the gift certificate dto
     */
    public static GiftCertificateDTO toGiftCertificateDTO(GiftCertificate giftCertificate) {
        return new GiftCertificateDTO(
                giftCertificate.getId(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(),
                giftCertificate.getTags()
                        .stream()
                        .map(ObjectConverter::toTagDTO)
                        .collect(Collectors.toList())
        );
    }

    /**
     * To entity gift certificate.
     *
     * @param giftCertificate the gift certificate
     * @return the gift certificate
     */
    public static GiftCertificate toGiftCertificateEntity(GiftCertificateDTO giftCertificate) {
        return new GiftCertificate(
                giftCertificate.getId(),
                giftCertificate.getName(),
                giftCertificate.getDescription(),
                giftCertificate.getPrice(),
                giftCertificate.getDuration(),
                giftCertificate.getCreateDate(),
                giftCertificate.getLastUpdateDate(),
                giftCertificate.getTags()
                        .stream()
                        .map(ObjectConverter::toTagEntity).collect(Collectors.toList())
        );
    }

    /**
     * To gift certificate dt os list.
     *
     * @param giftCertificates the gift certificates
     * @return the list
     */
    public static List<GiftCertificateDTO> toGiftCertificateDTOs(List<GiftCertificate> giftCertificates) {
        return giftCertificates.stream().map(ObjectConverter::toGiftCertificateDTO).collect(Collectors.toList());
    }

    /**
     * To dto user dto.
     *
     * @param user the user
     * @return the user dto
     */
    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName());
    }

    /**
     * To user dt os list.
     *
     * @param users the users
     * @return the list
     */
    public static List<UserDTO> toUserDTOs(List<User> users) {
        return users.stream().map(ObjectConverter::toUserDTO).collect(Collectors.toList());
    }

    /**
     * To dto order dto.
     *
     * @param order the order
     * @return the order dto
     */
    public static OrderDTO toOrderDTO(Order order) {
        return new OrderDTO(
                order.getId(),
                toUserDTO(order.getUser()),
                toGiftCertificateDTO(order.getGiftCertificate()),
                order.getOrderDate(),
                order.getCost());
    }

    /**
     * To order dt os list.
     *
     * @param orders the orders
     * @return the list
     */
    public static List<OrderDTO> toOrderDTOs(List<Order> orders) {
        return orders.stream().map(ObjectConverter::toOrderDTO).collect(Collectors.toList());
    }
}
