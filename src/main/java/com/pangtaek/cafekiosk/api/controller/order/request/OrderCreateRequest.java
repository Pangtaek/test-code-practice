package com.pangtaek.cafekiosk.api.controller.order.request;

import com.pangtaek.cafekiosk.api.service.order.request.OrderCreateServiceRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderCreateRequest {

    @NotEmpty(message = "상품 번호 리스트는 필수입니다.")
    private List<String> productNumberList;

    @Builder
    public OrderCreateRequest(List<String> productNumberList) {
        this.productNumberList = productNumberList;
    }

    public OrderCreateServiceRequest toServiceRequest() {
        return OrderCreateServiceRequest.builder()
                .productNumberList(this.productNumberList)
                .build();
    }
}
