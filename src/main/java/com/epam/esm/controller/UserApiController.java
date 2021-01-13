package com.epam.esm.controller;

import com.epam.esm.controller.error_handler.ErrorCode;
import com.epam.esm.controller.exception.GiftEntityNotFoundException;
import com.epam.esm.controller.exception.WrongParameterFormatException;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.GiftEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

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
    public HttpEntity<List<UserDTO>> findAll(@RequestParam(required = false) Integer limit,
                                             @RequestParam(required = false) Integer offset) {
        List<UserDTO> users = userService.findAll(limit, offset);
        users.forEach(this::addLink);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    public HttpEntity<UserDTO> findById(@PathVariable long id) {
        UserDTO user = userService.findById(id).orElseThrow(
                () -> new GiftEntityNotFoundException("User not found", ErrorCode.USER_NOT_FOUND)
        );
        addLink(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/buy")
    public HttpEntity<OrderDTO> buyCertificate(@RequestBody OrderDTO order) {
        long userId = order.getUser().getId();
        long certificateId = order.getGiftCertificate().getId();
        if (!GiftEntityValidator.correctId(userId, certificateId)) {
            throw new WrongParameterFormatException("Wrong buy certificate parameters",
                    ErrorCode.BUY_PARAMETERS_WRONG_FORMAT);
        }
        if (userService.findById(userId).isEmpty()) {
            throw new GiftEntityNotFoundException("User was not found!", ErrorCode.USER_NOT_FOUND);
        }
        if (giftCertificateService.findById(certificateId).isEmpty()) {
            throw new GiftEntityNotFoundException("Certificate not found", ErrorCode.GIFT_CERTIFICATE_NOT_FOUND);
        }
        OrderDTO added = orderService.add(order);
        addLink(added);
        return new ResponseEntity<>(added, HttpStatus.OK);
    }

    @GetMapping("/{userId:^[1-9]\\d{0,18}$}/orders")
    public HttpEntity<List<OrderDTO>> findUserOrders(@PathVariable long userId,
                                                     @RequestParam(required = false) Integer limit,
                                                     @RequestParam(required = false) Integer offset) {
        List<OrderDTO> orders = orderService.findOrdersByUserId(userId, limit, offset);
        orders.forEach(this::addLink);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{userId:^[1-9]\\d{0,18}$}/orders/{orderId:^[1-9]\\d{0,18}$}")
    public HttpEntity<OrderDTO> findUserOrder(@PathVariable long userId,
                                              @PathVariable long orderId) {
        if (orderService.orderBelongsToUser(userId, orderId)) {
            OrderDTO order = orderService.findById(orderId).orElseThrow(
                    () -> new GiftEntityNotFoundException("Order not found", ErrorCode.ORDER_NOT_FOUND)
            );
            addLink(order);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } else {
            throw new GiftEntityNotFoundException("Order does not belong to user",
                    ErrorCode.ORDER_DOES_NOT_BELONG_TO_USER);
        }
    }

    @GetMapping("/widelyUsedTag")
    public HttpEntity<TagDTO> widelyUsedTag() {
        TagDTO tag = userService.mostWidelyUsedTagOfUserWithHighestOrdersSum().orElseThrow(() ->
                new GiftEntityNotFoundException("Tag not found", ErrorCode.TAG_NOT_FOUND));
        tag.add(linkTo(TagApiController.class).slash(tag.getId()).withSelfRel());
        return new ResponseEntity<>(tag, HttpStatus.OK);
    }

    private void addLink(OrderDTO o) {
        o.add(linkTo(UserApiController.class).slash(o.getUser().getId()).withSelfRel());
        o.getUser().add(linkTo(UserApiController.class).slash(o.getUser().getId()).withSelfRel());
        o.getGiftCertificate().add(linkTo(GiftCertificateApiController.class)
                .slash(o.getGiftCertificate().getId())
                .withSelfRel());
        if (o.getGiftCertificate().getTags() != null) {
            o.getGiftCertificate().getTags().forEach(t -> {
                t.add(linkTo(TagApiController.class).slash(o.getGiftCertificate().getId()).withSelfRel());
            });
        }
    }

    private void addLink(UserDTO user) {
        user.add(linkTo(UserApiController.class).slash(user.getId()).withSelfRel());
        user.add(linkTo(UserApiController.class).slash(user.getId())
                .slash(HateoasRel.ORDERS)
                .withRel(HateoasRel.USER_ORDERS));
    }
}
