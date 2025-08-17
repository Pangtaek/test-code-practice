package com.pangtaek.cafekiosk.domain.api.controller.order.request;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderCreateRequest {

    private List<String> productNumberList;

    @Builder
    public OrderCreateRequest(List<String> productNumberList) {
        this.productNumberList = productNumberList;
    }
}
