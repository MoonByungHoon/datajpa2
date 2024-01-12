package jpabasic.data_jpa_2.service;

import jakarta.persistence.EntityManager;
import jpabasic.data_jpa_2.domain.*;
import jpabasic.data_jpa_2.exception.NotEnoughStockException;
import jpabasic.data_jpa_2.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

  @Autowired EntityManager em;
  @Autowired OrderService orderService;
  @Autowired OrderRepository orderRepository;

  @Test
  public void 상품주문() throws Exception {
    //given
    Member member = createMember("회원1");

    Book book = createBook("JPA", 20000, 10);

    int orderCount = 2;

    //when
    Long orderid = orderService.order(member.getId(), book.getId(), orderCount);

    //then
    Order getOrder = orderRepository.findOne(orderid);

    assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
    assertEquals( 1, getOrder.getOrderItems().size(), "주문한 상품 가짓수가 정확한가.");
    assertEquals(book.getPrice() * orderCount, getOrder.getTotalPrice(), "주문 가격이 정상적으로 나오는가.");
    assertEquals(book.getStockQuantity(), 8, "주문 수량 재고가 제대로 줄었는가.");
  }

  @Test
  public void 주문취소() throws Exception {
    //given

    //when

    //then
  }
  @Test
  public void 상품주문_재고수량초과() throws Exception {
    //given
    Member member = createMember("회원1");
    Item item = createBook("JPA", 20000, 10);

    int orderCount = 1;

    //when

    //then
    assertThrows(NotEnoughStockException.class, () -> {
      orderService.order(member.getId(), item.getId(), orderCount);
    });
  }

  private Book createBook(String name, int orderPrice, int stockQuantity) {
    Book book = new Book();
    book.setName(name);
    book.setPrice(orderPrice);
    book.setStockQuantity(stockQuantity);
    em.persist(book);
    return book;
  }

  private Member createMember(String name) {
    Member member = new Member();
    member.setName(name);
    member.setAddress(new Address("인천", "서구", "신현동"));
    em.persist(member);
    return member;
  }

}