package jpabasic.data_jpa_2.domain;

import jakarta.persistence.Embeddable;
import jpabasic.data_jpa_2.controller.MemberForm;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {
//  값 타입인 Address의 경우 변경을 하게 되면 래퍼런스를 참조 하는 JAVA의 특성상 문제를 일으킬 수 있다.
//  그렇기 때문에 값 타입의 경우에는 되도록이면 Setter같이 값을 변경하는 동작을 사용하지 않는 것이 좋다.
//  값을 초기에 생성자를 통해서 집어넣고 이후에는 변경이 불가한 형태로 써야 문제가 발생하지 않는다.

  private String city;
  private String street;
  private String zipcode;

  public Address(String city, String street, String zipcode) {
    this.city = city;
    this.street = street;
    this.zipcode = zipcode;
  }
}
