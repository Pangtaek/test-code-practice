package com.pangtaek.cafekiosk.api.service.order;

import com.pangtaek.cafekiosk.client.mail.MailSendClient;
import com.pangtaek.cafekiosk.domain.history.mail.MailSendHistory;
import com.pangtaek.cafekiosk.domain.history.mail.MailSendHistoryRepository;
import com.pangtaek.cafekiosk.domain.order.Order;
import com.pangtaek.cafekiosk.domain.order.OrderRepository;
import com.pangtaek.cafekiosk.domain.order.OrderStatus;
import com.pangtaek.cafekiosk.domain.orderproduct.OrderProductRepository;
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
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.pangtaek.cafekiosk.domain.product.ProductType.HANDMADE;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles(value = "test")
class OrderStatisticsServiceTest {

    @Autowired
    private OrderStatisticsService orderStatisticsService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MailSendHistoryRepository mailSendHistoryRepository;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @MockitoBean
    private MailSendClient mailSendClient;

    @AfterEach
    void tearDown() {
        orderProductRepository.deleteAllInBatch();
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        mailSendHistoryRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("결제완료 주문들의 매출 통계 메일을 전송한다.")
    void sendOrderStatisticsMail() {
        // given
        Product product1 = createProduct(HANDMADE, "001", 1000);
        Product product2 = createProduct(HANDMADE, "002", 3000);
        Product product3 = createProduct(HANDMADE, "003", 5000);
        productRepository.saveAll(List.of(product1, product2, product3));

        LocalDateTime now = LocalDateTime.now();
        List<Product> productList = List.of(product1, product2, product3);

        Order order1 = createPaymentCompletedOrder(productList, LocalDateTime.of(2025, 8, 12, 23, 59));
        Order order2 = createPaymentCompletedOrder(productList, now);
        Order order3 = createPaymentCompletedOrder(productList, LocalDateTime.of(2025, 8, 18, 23, 59));
        Order order4 = createPaymentCompletedOrder(productList, LocalDateTime.of(2025, 8, 19, 23, 59));
        orderRepository.saveAll(List.of(order1, order2, order3, order4));

        when(mailSendClient.sendEmail(any(String.class), any(String.class), any(String.class), any(String.class)))
                .thenReturn(true);

        // when
        boolean result = orderStatisticsService.sendOrderStatisticsMail(LocalDate.of(2025, 8, 18), "test@test.com");

        // then
        assertThat(result).isTrue();

        List<MailSendHistory> historyList = mailSendHistoryRepository.findAll();
        assertThat(historyList)
                .hasSize(1)
                .extracting("content")
                .contains("총 매출 합계는 18000원입니다.");
    }

    private static Order createPaymentCompletedOrder(List<Product> productList, LocalDateTime now) {
        return Order.builder()
                .productList(productList)
                .orderStatus(OrderStatus.PAYMENT_COMPLETED)
                .registeredDateTime(now)
                .build();
    }

    private Product createProduct(ProductType type, String productNumber, int price) {
        return Product.builder()
                .type(type)
                .productNumber(productNumber)
                .price(price)
                .sellingStatus(ProductSellingStatus.SELLING)
                .name("메뉴 이름")
                .build();
    }
}