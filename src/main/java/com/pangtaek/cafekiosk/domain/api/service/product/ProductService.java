package com.pangtaek.cafekiosk.domain.api.service.product;

import com.pangtaek.cafekiosk.domain.api.service.product.response.ProductResponse;
import com.pangtaek.cafekiosk.domain.product.Product;
import com.pangtaek.cafekiosk.domain.product.ProductRepository;
import com.pangtaek.cafekiosk.domain.product.ProductSellingStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductResponse> getSellingProductList() {
        List<Product> productList =  productRepository.findAllBySellingStatusIn(ProductSellingStatus.forDisplay());

        return productList.stream()
                .map(product -> ProductResponse.of(product))
                .collect(Collectors.toList());
    }
}
