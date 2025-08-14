package com.pangtaek.cafekiosk;

import com.pangtaek.cafekiosk.unit.CafeKiosk;
import com.pangtaek.cafekiosk.unit.beverage.Americano;
import com.pangtaek.cafekiosk.unit.beverage.Latte;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CafeKioskTest {

  @Test
    void add_manual_test() {
      CafeKiosk cafeKiosk = new CafeKiosk();
      cafeKiosk.add(new Americano());

      System.out.println(">>> 담긴 음료 수: " + cafeKiosk.getBeverageList().size());
      System.out.println(">>> 담긴 음료: " + cafeKiosk.getBeverageList().get(0).getName());
  }

  @Test
  void add() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    cafeKiosk.add(new Americano());

    assertThat(cafeKiosk.getBeverageList().size()).isEqualTo(1);
    assertThat(cafeKiosk.getBeverageList().get(0).getName()).isEqualTo("Americano");
  }

  @Test
  void addSeveralBeverages() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();

    cafeKiosk.add(americano, 2);

    assertThat(cafeKiosk.getBeverageList().get(0)).isEqualTo(americano);
    assertThat(cafeKiosk.getBeverageList().get(1)).isEqualTo(americano);
  }

  @Test
  void addZeroBeverages() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();

    assertThatThrownBy(() -> cafeKiosk.add(americano, 0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("음료는 1잔 이상 주문하실 수 있습니다.");
  }

  @Test
  void remove() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();

    cafeKiosk.add(americano);
    assertThat(cafeKiosk.getBeverageList())
            .hasSize(1);

    cafeKiosk.remove(americano);
    assertThat(cafeKiosk.getBeverageList())
            .isEmpty();
  }

  @Test
  void clear() {
    CafeKiosk cafeKiosk = new CafeKiosk();
    Americano americano = new Americano();
    Latte latte = new Latte();

    cafeKiosk.add(americano);
    cafeKiosk.add(latte);
    assertThat(cafeKiosk.getBeverageList())
            .hasSize(2);

    cafeKiosk.clear();
    assertThat(cafeKiosk.getBeverageList())
            .isEmpty();
  }
}
