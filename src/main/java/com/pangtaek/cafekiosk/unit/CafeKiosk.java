package com.pangtaek.cafekiosk.unit;

import com.pangtaek.cafekiosk.unit.beverage.Beverage;
import com.pangtaek.cafekiosk.unit.order.Order;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class CafeKiosk {

    private final List<Beverage> beverageList = new ArrayList<>();

    public void add(Beverage beverage) {
        beverageList.add(beverage);
    }

    public void add(Beverage beverage, int count) {
        if(count <= 0) {
            throw new IllegalArgumentException("음료는 1잔 이상 주문하실 수 있습니다.");
        }

        for (int i = 0; i < count; i++) {
            add(beverage);
        }
    }

    public void remove(Beverage beverage) {
        beverageList.remove(beverage);
    }

    public void clear() {
        beverageList.clear();
    }

    public int calculateTotalPrice() {
        if (beverageList.isEmpty()) return 0;
        return beverageList.stream()
                .mapToInt(Beverage::getPrice)
                .sum();
    }

    public Order createOrder() {
        return new Order(LocalDateTime.now(), beverageList);
    }
}
