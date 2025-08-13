package com.pangtaek.cafekiosk.unit.order;

import com.pangtaek.cafekiosk.unit.beverage.Beverage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class Order {

    private LocalDateTime orderDateTime;
    private List<Beverage> beverageList;
}
