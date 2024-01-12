package jpabasic.data_jpa_2.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Setter @Getter
public class Order {

  @Id @GeneratedValue
  @Column(name = "order_id")
  private Long id;

//  Member와 다대일 관계를 형성하고 있다. 다대일의 경우 페치 전략이 EAGER(즉시 로딩)이기 때문에
//  성능 문제와 N + 1 문제등, 다양한 변수를 내제하고 있기 때문에 LAZY(지연 로딩) 전략으로
//  변경을 하고 사용하는 것이 좋다. 또한 @Joincolumn으로 키 관리를 담당하였다.
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  private Member member; //주문한 회원

//  Member와 Order의 관계처럼 같은 관계를 형성 하고 있다.
//  다른 점으로는 cascasde를 ALL로 설정해주어서 OrderItem의 생명주기를 Order과 같이 가져간다는 점이다.
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItem> orderItems = new ArrayList<>();

//  배송에 관련된 Delivery와 일대일 관계를 형성하고 있다.
//  생명 주기를 Delivery의 생명 주기를 Order와 같이 가져가고 있다.
//  또한 일대일 관계의 경우 페치 전략의 기존 값이 EAGER이기 때문에 LAZY로 변경하는 것이다.
  @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinColumn(name = "delivery_id")
  private Delivery delivery; //배송에 대한 정보

  private LocalDateTime orderDate; //주문 시간

//  주문 상태에 대한 경우 정해진 값 만을 받는 필드이다.
//  정해진 값 외에 별다른 값을 받을 이유가 없기 때문에
//  개발 작업상에서 생기는 변수에 대해서 확실한 차단을 위해서 Enum을 사용하는 것이다.
//  Enum으로 인해서 OrderStatus는 ORDER과 CANCEL을 제외한 값을 받을 수 없게 되었으며
//  개발 도중에 명확한 값만 받기 때문에 착오를 일으킬 실수가 줄어든다.
//  또한 EnumType에는 ORDINAL과 STRING 2가지 타입이 있는데
//  ORDINAL의 경우 Enum 클래스 안에 있는 값들을 순서대로 배열의 index처럼 인식해서
//  사용하게 되는데 이럴 경우 중간에 Enum클래스 안에 객체에 변경이 생기면
//  문제 발생 및 코드 수정을 해야하는 등. 유지 보수 및 직관성이 떨어지기 때문에
//  특별한 경우가 아니고서는 STRING으로 사용하는 것이 맞다.
  @Enumerated(EnumType.STRING)
  private OrderStatus status; //주문 상태 [ORDER, CANCEL]

  public void setMember(Member member) {
    this.member = member;

    //  연관관계에 있어서 명확히 하며, 데이터를 동기화 시키는 작업이다.
    member.getOrders().add(this);
  }

  public void addOrderItem(OrderItem orderItem) {
    orderItems.add(orderItem);
    orderItem.setOrder(this);
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
    delivery.setOrder(this);
  }

  //생셩 메서드
  public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
    Order order = new Order();
    order.setMember(member);
    order.setDelivery(delivery);
    for (OrderItem orderItem : orderItems) {
      order.addOrderItem(orderItem);
    }

    order.setStatus(OrderStatus.ORDER);
    order.setOrderDate(LocalDateTime.now());
    return order;
  }

  //비지니스 로직
  /**
   * 주문 취소
   */
  public void cancle() {
    if(delivery.getStatus() == DeliveryStatus.COMP) {
      throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
    }

    this.setStatus(OrderStatus.CANCEL);
    for (OrderItem orderItem : this.orderItems) {
      orderItem.cancel();
    }
  }

//  조회 로직
  /**
   * 전체 주문 가격
   */
  public int getTotalPrice() {
    int totalPrice = 0;

    for (OrderItem orderItem : orderItems) {
      totalPrice += orderItem.getTotalPrice();
    }

    return totalPrice;
  }
}
