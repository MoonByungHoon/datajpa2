package jpabasic.data_jpa_2;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jpabasic.data_jpa_2.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitDb {

  private final InitService initService;

  @PostConstruct
  public void init() {
    initService.dbInit1();
    initService.dbInit2();
  }

  @Component
  @Transactional
  @RequiredArgsConstructor
  static class InitService {
    private final EntityManager em;

    public void dbInit1() {
      Member member = createMember("userA", "인천", "1", "1111");
      em.persist(member);

      Book book1 = new Book("JPA1 Book", 10000, 100);
      em.persist(book1);

      Book book2 = new Book("JPA2 Book", 20000, 200);
      em.persist(book2);

      OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

      Delivery delivery = new Delivery();
      delivery.setAddress(member.getAddress());

      Order order = Order.createOrder(member, delivery, orderItem2, orderItem2);
      em.persist(order);
    }

    private static Member createMember(String name, String city, String street, String zipcode) {
      Member member = new Member();
      member.setName(name);
      member.setAddress(new Address(city, street, zipcode));
      return member;
    }

    public void dbInit2() {
      Member member = createMember("userB", "서울", "2", "2222");
      em.persist(member);

      Book book1 = new Book("SPRING1 Book", 30000, 250);
      Book book2 = new Book("SPRING2 Book", 40000, 400);
      em.persist(book1);
      em.persist(book2);

      OrderItem orderItem1 = OrderItem.createOrderItem(book1, 30000, 4);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 7);

      Delivery delivery = new Delivery();
      delivery.setAddress(member.getAddress());

      Order order = Order.createOrder(member, delivery, orderItem2, orderItem2);
      em.persist(order);
    }
  }

}
