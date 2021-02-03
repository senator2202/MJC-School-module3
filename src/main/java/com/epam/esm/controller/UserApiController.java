package com.epam.esm.controller;

import com.epam.esm.controller.error_handler.ProjectError;
import com.epam.esm.controller.exception.ExceptionProvider;
import com.epam.esm.model.dto.OrderDTO;
import com.epam.esm.model.dto.TagDTO;
import com.epam.esm.model.dto.UserDTO;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.validator.GiftEntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * Rest Controller for User entities and their orders.
 */
@RestController
@RequestMapping("api/users")
public class UserApiController {

    /**
     * String constant for hateoas rel value.
     */
    private static final String ORDERS = "orders";

    private UserService userService;
    private OrderService orderService;
    private ExceptionProvider exceptionProvider;

    /**
     * Sets user service.
     *
     * @param userService the user service
     */
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Sets order service.
     *
     * @param orderService the order service
     */
    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    /**
     * Sets exception provider.
     *
     * @param exceptionProvider the exception provider
     */
    @Autowired
    public void setExceptionProvider(ExceptionProvider exceptionProvider) {
        this.exceptionProvider = exceptionProvider;
    }

    /**
     * Find all users, optionally with limit and offset.
     *
     * @param limit  the limit
     * @param offset the offset
     * @return the list of user dto
     */
    @GetMapping
    public List<UserDTO> findAll(@RequestParam(required = false) Integer limit,
                                 @RequestParam(required = false) Integer offset) {
        List<UserDTO> users = userService.findAll(limit, offset);
        return users.stream()
                .map(this::addUserLinks)
                .collect(Collectors.toList());
    }

    /**
     * Find user by id, return user dto.
     *
     * @param id the id
     * @return the user dto
     */
    @GetMapping("/{id:^[1-9]\\d{0,18}$}")
    public UserDTO findById(@PathVariable long id) {
        UserDTO user = userService.findById(id).orElseThrow(
                () -> exceptionProvider.giftEntityNotFoundException(ProjectError.USER_NOT_FOUND)
        );
        return addUserLinks(user);
    }

    /**
     * Buy certificate, return order dto.
     *
     * @param userId        the user id
     * @param certificateId the certificate id
     * @return the order dto
     */
    @PostMapping("/{userId:^[1-9]\\d{0,18}$}/orders")
    public OrderDTO buyCertificate(@PathVariable long userId, @RequestBody long certificateId) {
        if (!GiftEntityValidator.correctId(certificateId)) {
            throw exceptionProvider.wrongParameterFormatException(ProjectError.BUY_PARAMETERS_WRONG_FORMAT);
        }
        OrderDTO added = orderService.add(userId, certificateId);
        return addOrderLinks(added);
    }

    /**
     * Find user orders, optionally with limit and offset.
     *
     * @param userId the user id
     * @param limit  the limit
     * @param offset the offset
     * @return the list
     */
    @GetMapping("/{userId:^[1-9]\\d{0,18}$}/orders")
    public List<OrderDTO> findUserOrders(@PathVariable long userId,
                                         @RequestParam(required = false) Integer limit,
                                         @RequestParam(required = false) Integer offset) {
        List<OrderDTO> orders = orderService.findOrdersByUserId(userId, limit, offset);
        return orders.stream().map(this::addOrderLinks).collect(Collectors.toList());
    }

    /**
     * Find user order, return its dto.
     *
     * @param userId  the user id
     * @param orderId the order id
     * @return the order dto
     */
    @GetMapping("/{userId:^[1-9]\\d{0,18}$}/orders/{orderId:^[1-9]\\d{0,18}$}")
    public OrderDTO findUserOrder(@PathVariable long userId,
                                  @PathVariable long orderId) {
        Optional<OrderDTO> optional = orderService.findUserOrderById(userId, orderId);
        return optional.map(this::addOrderLinks).orElseThrow(
                () -> exceptionProvider.giftEntityNotFoundException(ProjectError.ORDER_NOT_FOUND)
        );
    }

    /**
     * Find most widely used tag of user with highest total orders sum, return its dto.
     *
     * @return the tag dto
     */
    @GetMapping("/widely-used-tag")
    public TagDTO widelyUsedTag() {
        TagDTO tag = userService.mostWidelyUsedTagOfUserWithHighestOrdersSum().orElseThrow(
                () -> exceptionProvider.giftEntityNotFoundException(ProjectError.TAG_NOT_FOUND)
        );
        return TagApiController.addSelfLink(tag);
    }

    /**
     * Method add HATEOAS links to UserDTO entity
     */
    private UserDTO addUserLinks(UserDTO user) {
        return user
                .add(linkTo(UserApiController.class).slash(user.getId()).withSelfRel())
                .add(linkTo(methodOn(UserApiController.class).findUserOrders(user.getId(), null, null))
                        .withRel(ORDERS));
    }

    /**
     * Method add HATEOAS links to OrderDTO entity
     */
    private OrderDTO addOrderLinks(OrderDTO order) {
        Long userId = order.getUser().getId();
        Long orderId = order.getId();
        GiftCertificateApiController.addSelfLink(order.getGiftCertificate());
        addUserLinks(order.getUser());
        return order.add(linkTo(methodOn(UserApiController.class).findUserOrder(userId, orderId)).withSelfRel());
    }
}
