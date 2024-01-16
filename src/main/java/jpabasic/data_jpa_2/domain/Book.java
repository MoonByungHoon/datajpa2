package jpabasic.data_jpa_2.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Entity
@DiscriminatorValue("Book")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter
public class Book extends Item{

  private String author;
  private String isbn;

  public Book(String name, int price, int stockQuantity, String author, String isbn) {
    super(name, price, stockQuantity);
    this.author = author;
    this.isbn = isbn;
  }

  public Book(String name, int orderPrice, int quantity) {
    super(name, orderPrice, quantity);
  }

  public Book(Long id, String name, int price, int stockQuantity, String author, String isbn) {
    super(id, name, price, stockQuantity);
    this.author = author;
    this.isbn = isbn;
  }
}
