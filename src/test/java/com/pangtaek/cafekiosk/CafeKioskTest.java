package com.pangtaek.cafekiosk;

import com.pangtaek.cafekiosk.unit.CafeKiosk;
import com.pangtaek.cafekiosk.unit.beverage.Americano;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CafeKioskTest {

  @Test
    void add() {
      CafeKiosk cafeKiosk = new CafeKiosk();
      cafeKiosk.add(new Americano());

      System.out.println(">>> 담긴 음료 수: " + cafeKiosk.getBeverageList().size());
      System.out.println(">>> 담긴 음료: " + cafeKiosk.getBeverageList().get(0).getName());
  }
}
