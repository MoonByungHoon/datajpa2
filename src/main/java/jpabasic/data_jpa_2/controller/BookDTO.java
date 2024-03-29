package jpabasic.data_jpa_2.controller;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BookDTO {

  private String name;
  private int price;
  private int stockQuantity;
  private String author;
  private String isbn;

  public BookDTO(String name, int price, int stockQuantity, String author, String isbn) {
    this.name = name;
    this.price = price;
    this.stockQuantity = stockQuantity;
    this.author = author;
    this.isbn = isbn;
  }
}
