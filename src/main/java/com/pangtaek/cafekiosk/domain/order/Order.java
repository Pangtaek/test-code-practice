package com.pangtaek.cafekiosk.domain.order;

import com.pangtaek.cafekiosk.domain.orderproduct.OrderProduct;
import com.pangtaek.cafekiosk.domain.product.Product;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private int totalPrice;

    private LocalDateTime registeredDateTime;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderProduct> orderProductList = new ArrayList<>();

    public Order(List<Product> productList, LocalDateTime registeredDateTime) {
        this.status = OrderStatus.INIT;
        this.totalPrice = calculateTotalPrice(productList);
        this.registeredDateTime = registeredDateTime;
        this.orderProductList = productList.stream()
                .map(product -> new OrderProduct(this, product))
                .collect(Collectors.toList());
    }

    public static Order create(List<Product> productList, LocalDateTime registeredDateTime) {
        return new Order(productList, registeredDateTime);
    }

    private static int calculateTotalPrice(List<Product> productList) {
        return productList.stream()
                .mapToInt(Product::getPrice)
                .sum();
    }
}
