package com.pangtaek.cafekiosk.domain.api.service.product;

import com.pangtaek.cafekiosk.api.controller.product.dto.request.ProductCreateRequest;
import com.pangtaek.cafekiosk.api.service.product.ProductService;
import com.pangtaek.cafekiosk.api.service.product.request.ProductCreateServiceRequest;
import com.pangtaek.cafekiosk.api.service.product.response.ProductResponse;
import com.pangtaek.cafekiosk.domain.product.Product;
import com.pangtaek.cafekiosk.domain.product.ProductRepository;
import com.pangtaek.cafekiosk.domain.product.ProductSellingStatus;
import com.pangtaek.cafekiosk.domain.product.ProductType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@ActiveProfiles(value = "test")
@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @AfterEach
    void tearDown() {
        productRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("신규 상품을 등록한다. 상품번호는 가장 최근 상품번호에서 1 증가한 값이다.")
    void createProduct() {
        // given
        Product product1 = createProduct("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000);
        productRepository.saveAll(List.of(product1));

        ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("카푸치노")
                .price(5000)
                .build();

        // when
        ProductResponse productResponse = productService.createProduct(request);

        // then
        assertThat(productResponse)
                .extracting(ProductResponse::getProductNumber, ProductResponse::getType, ProductResponse::getSellingStatus, ProductResponse::getName, ProductResponse::getPrice)
                .contains("002", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000);

        List<Product> productList = productRepository.findAll();
        assertThat(productList)
                .hasSize(2)
                .extracting(Product::getProductNumber, Product::getType, Product::getSellingStatus, Product::getName, Product::getPrice)
                .containsExactlyInAnyOrder(
                        tuple("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "아메리카노", 4000),
                        tuple("002", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000)
                )
        ;
    }

    @Test
    @DisplayName("상품이 하나도 없는 경우 신규 상품을 등록하면, 상품 번호는 001이다.")
    void createProductWhenProductsIsEmpty() {
        // given
        ProductCreateServiceRequest request = ProductCreateServiceRequest.builder()
                .type(ProductType.HANDMADE)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("카푸치노")
                .price(5000)
                .build();

        // when
        ProductResponse productResponse = productService.createProduct(request);

        // then
        assertThat(productResponse)
                .extracting(ProductResponse::getProductNumber, ProductResponse::getType, ProductResponse::getSellingStatus, ProductResponse::getName, ProductResponse::getPrice)
                .contains("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000);

        List<Product> productList = productRepository.findAll();
        assertThat(productList)
                .hasSize(1)
                .extracting(Product::getProductNumber, Product::getType, Product::getSellingStatus, Product::getName, Product::getPrice)
                .containsExactlyInAnyOrder(
                        tuple("001", ProductType.HANDMADE, ProductSellingStatus.SELLING, "카푸치노", 5000)
                )
        ;
    }

    private Product createProduct(String productNumber, ProductType type, ProductSellingStatus sellingStatus, String name, int price) {
        return Product.builder()
                .productNumber(productNumber)
                .type(type)
                .sellingStatus(sellingStatus)
                .name(name)
                .price(price)
                .build();
    }
}