package jpabasic.data_jpa_2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Category {

  @Id @GeneratedValue
  @Column(name = "category_id")
  private Long id;

  private String name;

//  Item과 Category의 관계를 다대다 관계로 사용하기 위해서
//  중간의 연결을 위한 테이블을 생성하는 것이다.
//  name에는 중간다리 역할을 할 테이블의 이름을 적어주고
//  joinColumns과 inverseJoinColumns에는 참조할 각 외래키를 지정해준다.
//  실무에서는 사용하지 않는 것이 좋다. 중간 테이블을 관리하기 힘들기 때문이다.
  @ManyToMany
  @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
  private List<Item> items = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Category parent;

  @OneToMany(mappedBy = "parent")
  private List<Category> child = new ArrayList<>();

  public Category(long l, String a) {
    this.id = l;
    this.name = a;
  }

  public void addChildCategory(Category child) {
    this.child.add(child);
    child.setParent(this);
  }
}
