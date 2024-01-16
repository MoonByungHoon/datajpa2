package jpabasic.data_jpa_2.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Member {

  @Id @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  private String name;

//  임베디드 타입인 Address를 가지게 된다.
//  임베디드 타입의 경우 별도의 추적이 불가능하다.
//  그럼에도 사용 하는 이유는 객체 지향적인 코드를 작성하기 위함이다.
//  현제 프로젝트에서 Address에 있는 city, street, zipcode는
//  배송을 하는 delivery 엔티티에서도 필요한 필드를 가지고 있다.
//  즉 객체 지향적인 코드로 만들어서 공통된 필드를 한곳에서 관리하는 것이다.
//  테이블 구조에서는 별도의 테이블이 아닌
//  Member 테이블에 Address 필드들이 추가된 것으로 적용된다.
  @Embedded
  private Address address;

//  Member는 Order와 일대다 관계를 형성하고 있다.
//  mappedBy를 설정해줌으로 인해서 Member에서도 order를 조회 가능하게 되었다.
//  포랜키 관리는 Order가 하게되는 구조이다.
  @JsonIgnore
  @OneToMany(mappedBy = "member")
  private List<Order> orders = new ArrayList<>();

  public Member(Long id, String name) {
    this.id = id;
    this.name = name;
  }

  public Member(String name, Address address) {
    this.name = name;
    this.address = address;
  }

  public Member(String moon) {
    this.name = moon;
  }
}
