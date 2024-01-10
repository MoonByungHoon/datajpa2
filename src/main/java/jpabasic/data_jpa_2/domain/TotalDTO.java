package jpabasic.data_jpa_2.domain;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TotalDTO<A, B>{

  private A a;
  private B b;

  public TotalDTO(A a, B b) {
    this.a = a;
    this.b = b;
  }
}
