package com.pangtaek.cafekiosk.domain.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllBySellingStatusIn(List<ProductSellingStatus> sellingStatusList);

    List<Product> findAllByProductNumberIn(List<String> productNumberList);

    @Query(value = "select p.product_number from product as p order by id desc limit 1", nativeQuery = true)
    String findLatestProduct();
}
