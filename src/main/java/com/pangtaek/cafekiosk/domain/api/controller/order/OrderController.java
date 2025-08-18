package com.pangtaek.cafekiosk.domain.api.controller.order;

import com.pangtaek.cafekiosk.domain.api.ApiResponse;
import com.pangtaek.cafekiosk.domain.api.controller.order.request.OrderCreateRequest;
import com.pangtaek.cafekiosk.domain.api.service.order.OrderService;
import com.pangtaek.cafekiosk.domain.api.service.order.response.OrderResponse;
import jakarta.validation.Valid;
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
    public ApiResponse<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        return ApiResponse.ok(orderService.createOrder(request.toServiceRequest(), LocalDateTime.now()));
    }
}
