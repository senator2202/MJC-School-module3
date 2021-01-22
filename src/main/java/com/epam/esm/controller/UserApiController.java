package com.epam.esm.controller;

import com.epam.esm.controller.error_handler.ErrorCode;
import com.epam.esm.controller.exception.GiftEntityNotFoundException;
import com.epam.esm.controller.exception.WrongParameterFormatException;
import com.epam.esm.model.dto.GiftCertificateDTO;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.GiftEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("api/users")
public class UserApiController {

    private UserService userService;
    private GiftCertificateService giftCertificateService;
    private OrderService orderService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setGiftCertificateService(GiftCertificateService giftCertificateService) {
        this.giftCertificateService = giftCertificateService;
    }

    @Autowired
    @Qualifier("orderServiceImpl")
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<EntityModel<UserDTO>> findAll(@RequestParam(required = false) Integer limit,
                                              @RequestParam(required = false) Integer offset) {
        List<UserDTO> users = userService.findAll(limit, offset);
        return users.stream()
                .map(this::addUserLinks)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    public EntityModel<UserDTO> findById(@PathVariable long id) {
        UserDTO user = userService.findById(id).orElseThrow(
                () -> new GiftEntityNotFoundException("User not found", ErrorCode.USER_NOT_FOUND)
        );
        return addUserLinks(user);
    }

    @PostMapping("/{userId:^[1-9]\\d{0,18}$}/orders")
    public EntityModel<OrderDTO> buyCertificate(@PathVariable long userId, @RequestBody GiftCertificateDTO certificate) {
        long certificateId = certificate.getId();
        if (!GiftEntityValidator.correctId(userId, certificateId)) {
            throw new WrongParameterFormatException("Wrong buy certificate parameters",
                    ErrorCode.BUY_PARAMETERS_WRONG_FORMAT);
        }
        OrderDTO added = orderService.add(userId, certificateId);
        return addOrderLinks(added);
    }

    @GetMapping("/{userId:^[1-9]\\d{0,18}$}/orders")
    public List<EntityModel<OrderDTO>> findUserOrders(@PathVariable long userId,
                                                      @RequestParam(required = false) Integer limit,
                                                      @RequestParam(required = false) Integer offset) {
        List<OrderDTO> orders = orderService.findOrdersByUserId(userId, limit, offset);
        return orders.stream().map(this::addOrderLinks).collect(Collectors.toList());
    }

    @GetMapping("/{userId:^[1-9]\\d{0,18}$}/orders/{orderId:^[1-9]\\d{0,18}$}")
    public EntityModel<OrderDTO> findUserOrder(@PathVariable long userId,
                                               @PathVariable long orderId) {
        Optional<OrderDTO> optional = orderService.findUserOrderById(userId, orderId);
        if (optional.isPresent()) {
            OrderDTO order = optional.get();
            return addOrderLinks(order);
        } else {
            throw new GiftEntityNotFoundException("Order not found", ErrorCode.ORDER_NOT_FOUND);
        }
    }

    @GetMapping("/widely-used-tag")
    public EntityModel<TagDTO> widelyUsedTag() {
        TagDTO tag = userService.mostWidelyUsedTagOfUserWithHighestOrdersSum().orElseThrow(() ->
                new GiftEntityNotFoundException("Tag not found", ErrorCode.TAG_NOT_FOUND));
        return TagApiController.addSelfLink(EntityModel.of(tag));
    }

    private EntityModel<UserDTO> addUserLinks(UserDTO user) {
        EntityModel<UserDTO> entityModel = EntityModel.of(user);
        addUserLinks(entityModel);
        return entityModel;
    }

    private void addUserLinks(EntityModel<UserDTO> entityModel) {
        UserDTO user = entityModel.getContent();
        entityModel.add(linkTo(UserApiController.class).slash(user.getId()).withSelfRel());
        entityModel.add(linkTo(methodOn(UserApiController.class).findUserOrders(user.getId(), null, null))
                .withRel(HateoasRel.ORDERS));
    }

    private EntityModel<OrderDTO> addOrderLinks(OrderDTO order) {
        Long userId = order.getUser().getContent().getId();
        Long orderId = order.getId();
        GiftCertificateApiController.addSelfLink(order.getGiftCertificate());
        GiftCertificateDTO certificate = order.getGiftCertificate().getContent();
        if (certificate.getTags() != null) {
            certificate.getTags().forEach(TagApiController::addSelfLink);
        }
        addUserLinks(order.getUser());
        return EntityModel
                .of(order)
                .add(linkTo(methodOn(UserApiController.class).findUserOrder(userId, orderId)).withSelfRel());
    }
}
