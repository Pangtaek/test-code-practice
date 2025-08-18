package com.pangtaek.cafekiosk.domain.api.service.order.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OrderCreateServiceRequest {

    private List<String> productNumberList;

    @Builder
    public OrderCreateServiceRequest(List<String> productNumberList) {
        this.productNumberList = productNumberList;
    }
}
