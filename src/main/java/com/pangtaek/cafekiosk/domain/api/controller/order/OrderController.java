package com.pangtaek.cafekiosk.domain.api.controller.order;

import com.pangtaek.cafekiosk.domain.api.controller.order.request.OrderCreateRequest;
import com.pangtaek.cafekiosk.domain.api.service.order.OrderService;
import com.pangtaek.cafekiosk.domain.api.service.order.response.OrderResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping(value = "/api/v1/orders/new")
    public OrderResponse createOrder(@RequestBody OrderCreateRequest request) {
        return orderService.createOrder(request, LocalDateTime.now());
    }
}
