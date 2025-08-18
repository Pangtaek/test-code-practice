package com.pangtaek.cafekiosk.api.service.order;

import com.pangtaek.cafekiosk.api.service.order.request.OrderCreateServiceRequest;
import com.pangtaek.cafekiosk.api.service.order.response.OrderResponse;
import com.pangtaek.cafekiosk.domain.order.Order;
import com.pangtaek.cafekiosk.domain.order.OrderRepository;
import com.pangtaek.cafekiosk.domain.product.Product;
import com.pangtaek.cafekiosk.domain.product.ProductRepository;
import com.pangtaek.cafekiosk.domain.product.ProductType;
import com.pangtaek.cafekiosk.domain.stock.Stock;
import com.pangtaek.cafekiosk.domain.stock.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;

    public OrderResponse createOrder(OrderCreateServiceRequest request, LocalDateTime registeredDateTime) {
        List<String> productNumberList = request.getProductNumberList();
        List<Product> duplicateProduct = findProductListBy(productNumberList);

        deductStockQuantities(duplicateProduct);

        Order order = Order.create(duplicateProduct, registeredDateTime);
        Order savedOrder = orderRepository.save(order);

        return OrderResponse.of(savedOrder);
    }

    private void deductStockQuantities(List<Product> duplicateProduct) {
        List<String> stockProductNumberList = extractStockProductNumberList(duplicateProduct);
        List<Stock> stockList = stockRepository.findAllByProductNumberIn(stockProductNumberList);

        Map<String, Stock> stockMap = createStockMapBy(stockList);
        Map<String, Long> productCountingMap = createCountingMapBy(stockProductNumberList);

        for (String stockProductNumber : new HashSet<>(stockProductNumberList)) {
            Stock stock = stockMap.get(stockProductNumber);
            int quantity = productCountingMap.get(stockProductNumber).intValue();

            if (stock.isQuantityLessThan(quantity)) {
                throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
            }

            stock.deductQuantity(quantity);
        }
    }

    private static Map<String, Long> createCountingMapBy(List<String> stockProductNumberList) {
        return stockProductNumberList.stream()
                .collect(Collectors.groupingBy(p -> p, Collectors.counting()));
    }

    private static Map<String, Stock> createStockMapBy(List<Stock> stockList) {
        Map<String, Stock> stockMap = stockList.stream()
                .collect(Collectors.toMap(Stock::getProductNumber, s -> s));
        return stockMap;
    }

    private static List<String> extractStockProductNumberList(List<Product> duplicateProduct) {
        List<String> stockProductNumberList = duplicateProduct.stream()
                .filter(product -> ProductType.containsStockType(product.getType()))
                .map(Product::getProductNumber)
                .collect(Collectors.toList());
        return stockProductNumberList;
    }

    private List<Product> findProductListBy(List<String> productNumberList) {
        List<Product> productList = productRepository.findAllByProductNumberIn(productNumberList);
        Map<String, Product> productMap = productList.stream()
                .collect(Collectors.toMap(Product::getProductNumber, p -> p));

        return productNumberList.stream()
                .map(productMap::get)
                .collect(Collectors.toList());
    }
}
