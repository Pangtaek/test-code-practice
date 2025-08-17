package com.pangtaek.cafekiosk.domain.api.service.order;

import com.pangtaek.cafekiosk.domain.api.controller.order.request.OrderCreateRequest;
import com.pangtaek.cafekiosk.domain.api.service.order.response.OrderResponse;
import com.pangtaek.cafekiosk.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;

    public OrderResponse createOrder(OrderCreateRequest request) {
        List<String> productNumberList = request.getProductNumberList();

//        productRepository.

        return null;
    }
}
