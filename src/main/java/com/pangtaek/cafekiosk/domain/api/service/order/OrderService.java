package com.pangtaek.cafekiosk.domain.api.service.order;

import com.pangtaek.cafekiosk.domain.api.controller.order.request.OrderCreateRequest;
import com.pangtaek.cafekiosk.domain.api.service.order.response.OrderResponse;
import com.pangtaek.cafekiosk.domain.order.Order;
import com.pangtaek.cafekiosk.domain.order.OrderRepository;
import com.pangtaek.cafekiosk.domain.product.Product;
import com.pangtaek.cafekiosk.domain.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumberList = request.getProductNumberList();

        List<Product> productList =  productRepository.findAllByProductNumberIn(productNumberList);

        Order order = Order.create(productList, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }
}
