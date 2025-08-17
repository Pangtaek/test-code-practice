package com.pangtaek.cafekiosk.domain.api.service.order.response;

import com.pangtaek.cafekiosk.domain.api.service.product.response.ProductResponse;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderResponse {

    private Long id;
    private int totalPrice;
    private LocalDateTime registeredDateTime;
    private List<ProductResponse> productResponseList;
}
