package com.epam.esm.controller;

import com.epam.esm.controller.exception.GiftEntityNotFoundException;
import com.epam.esm.controller.exception.WrongParameterFormatException;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.GiftEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {

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
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    public User findById(@PathVariable long id) {
        return userService.findById(id).orElseThrow(
                () -> new GiftEntityNotFoundException("User not found", ErrorCode.USER_NOT_FOUND)
        );
    }

    @PostMapping("/buy")
    public Order buyCertificate(@RequestBody Order order) {
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
        return orderService.add(order);
    }

    @GetMapping("/{userId:^[1-9]\\d{0,18}$}/orders")
    public List<Order> findUserOrders(@PathVariable long userId) {
        return orderService.findOrdersByUserId(userId);
    }

    @GetMapping("/{userId:^[1-9]\\d{0,18}$}/{orderId:^[1-9]\\d{0,18}$}")
    public Order findUserOrder(@PathVariable long userId,
                               @PathVariable long orderId) {
        if (orderService.orderBelongsToUser(userId, orderId)) {
            return orderService.findById(orderId).orElseThrow(
                    () -> new GiftEntityNotFoundException("Order not found", ErrorCode.ORDER_NOT_FOUND)
            );
        } else {
            throw new GiftEntityNotFoundException("Order does not belong to user",
                    ErrorCode.ORDER_DOES_NOT_BELONG_TO_USER);
        }
    }
}
