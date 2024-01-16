package jpabasic.data_jpa_2.domain;

import jakarta.persistence.*;
import jpabasic.data_jpa_2.exception.NotEnoughStockException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "DTYPE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public abstract class Item {

  @Id @GeneratedValue
  private Long id;

  private String name;
  private int price;
  private int stockQuantity;

  @ManyToMany(mappedBy = "items")
  private List<Category> categories = new ArrayList<>();

  public Item(Long id, String name, int price, int stockQuantity) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.stockQuantity = stockQuantity;
  }

//  비지니스 로직

  /**
   * 제고 증가.
   */
  public void addStock(int quantity) {
    this.stockQuantity += quantity;
  }

  public void removeStock(int quantity) {
    int restStock = this.stockQuantity - quantity;
    if (restStock < 0) {
      throw new NotEnoughStockException("재고가 0 이하로 내려갔습니다.");
    }
    this.stockQuantity = restStock;
  }

  public Item(String name, int price, int stockQuantity) {
    this.name = name;
    this.price = price;
    this.stockQuantity = stockQuantity;
  }

  public void changeItem(String name, int price, int quantity) {
    this.name = name;
    this.price = price;
    this.stockQuantity = quantity;
  }
}
