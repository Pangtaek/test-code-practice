package com.pangtaek.cafekiosk.domain.api.controller.product;

import com.pangtaek.cafekiosk.domain.api.ApiResponse;
import com.pangtaek.cafekiosk.domain.api.controller.product.dto.request.ProductCreateRequest;
import com.pangtaek.cafekiosk.domain.api.service.product.ProductService;
import com.pangtaek.cafekiosk.domain.api.service.product.response.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/api/v1/products/new")
    public ApiResponse<ProductResponse> createProduct(@Valid @RequestBody ProductCreateRequest request) {
        return ApiResponse.ok(productService.createProduct(request));
    }

    @GetMapping("/api/v1/products/selling")
    public ApiResponse<List<ProductResponse>> getSellingProductList() {
        return ApiResponse.of(HttpStatus.OK, productService.getSellingProductList());
    }

}
