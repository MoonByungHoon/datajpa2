package jpabasic.data_jpa_2.controller;

import jpabasic.data_jpa_2.domain.Book;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Optional;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookForm {

  private Long id;
  private Book book;

  public BookForm(Long id, String name, Integer price, Integer stockQuantity, String author, String isbn) {
    this.id = id;
//    int unboxingPrice = (price == null) ? 0 : price;
//    int unboxingStockQuantity = (stockQuantity == null) ? 0 : stockQuantity;
    int unboxingPrice = Optional.ofNullable(price).orElse(0);
    int unboxingStockQuantity = Optional.ofNullable(stockQuantity).orElse(0);

    book = new Book(name, unboxingPrice, unboxingStockQuantity, author, isbn);
  }

  public Book changeToEntity(BookForm form) {
    return new Book(form.book.getName(),form.book.getPrice(),
            form.book.getStockQuantity(), form.book.getAuthor(), form.book.getIsbn());
  }
}
