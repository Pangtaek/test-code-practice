package com.pangtaek.cafekiosk.api.service.order.response;

import com.pangtaek.cafekiosk.api.service.product.response.ProductResponse;
import com.pangtaek.cafekiosk.domain.order.Order;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponse {

    private Long id;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<ProductResponse> productResponseList;

    @Builder
    public OrderResponse(Long id, int totalPrice, LocalDateTime registeredDateTime, List<ProductResponse> productResponseList) {
        this.id = id;
        this.totalPrice = totalPrice;
        this.registeredDateTime = registeredDateTime;
        this.productResponseList = productResponseList;
    }

    public static OrderResponse of(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .totalPrice(order.getTotalPrice())
                .registeredDateTime(order.getRegisteredDateTime())
                .productResponseList(
                        order.getOrderProductList().stream()
                                .map(product -> ProductResponse.of(product.getProduct()))
                                .collect(Collectors.toList())
                )
                .build();
    }
}
