package com.pangtaek.cafekiosk.domain.api.order.service.order;

import com.pangtaek.cafekiosk.domain.api.controller.order.request.OrderCreateRequest;
import com.pangtaek.cafekiosk.domain.api.service.order.OrderService;
import com.pangtaek.cafekiosk.domain.api.service.order.response.OrderResponse;
import com.pangtaek.cafekiosk.domain.product.Product;
import com.pangtaek.cafekiosk.domain.product.ProductRepository;
import com.pangtaek.cafekiosk.domain.product.ProductSellingStatus;
import com.pangtaek.cafekiosk.domain.product.ProductType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductRepository productRepository;

    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
                .type(type)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("메뉴 이름")
                .build();
    }

    @Test
    @DisplayName("주문번호 리스트를 받아 주문을 생성한다.")
    void test() {
        // given
        Product product1 = createProduct(ProductType.HANDMADE, "001", 1000);
        Product product2 = createProduct(ProductType.HANDMADE, "002", 3000);
        Product product3 = createProduct(ProductType.HANDMADE, "003", 5000);

        productRepository.saveAll(List.of(product1, product2, product3));

        OrderCreateRequest request = OrderCreateRequest.builder()
                .productNumberList(List.of("001", "002"))
                .build();

        // when
        OrderResponse orderResponse = orderService.createOrder(request);

        // then
        assertThat(orderResponse.getId()).isNotNull();
        assertThat(orderResponse.getId())
                .extracting("registeredDateTime", "totalPrice")
                .contains(LocalDateTime.now(), "4000");
        assertThat(orderResponse.getProductResponseList())
                .hasSize(2)
                .extracting("productNumber", "price")
                .containsExactlyInAnyOrder(
                        tuple("001", 1000),
                        tuple("002", 3000)
                );
    }
}