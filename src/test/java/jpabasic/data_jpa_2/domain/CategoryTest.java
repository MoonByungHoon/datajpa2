package jpabasic.data_jpa_2.domain;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CategoryTest {

  @Test
  public void test() {
    Member member = new Member(1L, "member1");

    Order order = new Order(2L);

    TotalDTO<Member, Order> totalDTO = new TotalDTO<>(member, order);

    System.out.println("totalDTO.getA().getName() = " + totalDTO.getA().getName());
    System.out.println("totalDTO.getB().getId() = " + totalDTO.getB().getId());

    Member member1 = new Member(2L, "member2");

    totalDTO.setA(member1);

    System.out.println("totalDTO = " + totalDTO.getA().getName());
  }
}