package jpabasic.data_jpa_2.api;

import jpabasic.data_jpa_2.domain.Address;
import jpabasic.data_jpa_2.domain.Order;
import jpabasic.data_jpa_2.domain.OrderItem;
import jpabasic.data_jpa_2.domain.OrderStatus;
import jpabasic.data_jpa_2.repository.OrderRepository;
import jpabasic.data_jpa_2.repository.OrderSearch;
import jpabasic.data_jpa_2.repository.query.OrderFlatDto;
import jpabasic.data_jpa_2.repository.query.OrderItemQueryDto;
import jpabasic.data_jpa_2.repository.query.OrderQueryDto;
import jpabasic.data_jpa_2.repository.query.OrderQueryRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

  private final OrderRepository orderRepository;
  private final OrderQueryRepository orderQueryRepository;

  @GetMapping("/api/v1/orders")
  public List<Order> ordersV1() {
    List<Order> all = orderRepository.findAllByString(new OrderSearch());
    for (Order order : all) {
      order.getMember().getName();
      order.getDelivery().getAddress();

      List<OrderItem> orderItems = order.getOrderItems();
      orderItems.stream().forEach(o -> o.getItem().getName());
    }
    return all;
  }

  @GetMapping("/api/v2/orders")
  public List<OrderDto> ordersV2() {
    return orderRepository.findAllByString(new OrderSearch())
            .stream()
            .map(o -> new OrderDto(o))
            .collect(toList());
  }

  @GetMapping("/api/v3/orders")
  public List<OrderDto> ordersV3() {

    return orderRepository.findAllWithItem()
            .stream()
            .map(o -> new OrderDto(o))
            .collect(toList());
  }
  @GetMapping("/api/v3.1/orders")
  public List<OrderDto> ordersV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                      @RequestParam(value = "limit", defaultValue = "100") int limit) {

    return orderRepository.findAllWithMemberDelivery(offset, limit)
            .stream()
            .map(o -> new OrderDto(o))
            .collect(toList());
  }

  @GetMapping("/api/v4/orders")
  public List<OrderQueryDto> ordersV4() {
    return orderQueryRepository.findOrderQueryDtos();
  }

  @GetMapping("/api/v5/orders")
  public List<OrderQueryDto> ordersV5() {
    return orderQueryRepository.findAllByDto_optimization();
  }

  @GetMapping("/api/v6/orders")
  public List<OrderQueryDto> ordersV6() {
    List<OrderFlatDto> flats = orderQueryRepository.findAllByDto_flat();

    return flats.stream()
            .collect(groupingBy(o -> new OrderQueryDto(o.getOrderId(),
                            o.getName(), o.getOrderDate(), o.getOrderStatus(), o.getAddress()),
                    mapping(o -> new OrderItemQueryDto(o.getOrderId(),
                            o.getItemName(), o.getOrderPrice(), o.getCount()), toList())
            )).entrySet().stream()
            .map(e -> new OrderQueryDto(e.getKey().getOrderId(),
                    e.getKey().getName(), e.getKey().getOrderDate(), e.getKey().getOrderStatus(),
                    e.getKey().getAddress(), e.getValue()))
            .collect(toList());
  }

  @Getter
  static class OrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
      this.orderId = order.getId();
      this.name = order.getMember().getName();
      this.orderDate = order.getOrderDate();
      this.orderStatus = order.getStatus();
      this.address = order.getDelivery().getAddress();
//      order.getOrderItems().stream().forEach(o -> o.getItem().getName());
      this.orderItems = order.getOrderItems()
              .stream()
              .map(orderitem -> new OrderItemDto(orderitem))
              .collect(toList());
    }
  }

  @Getter
  static class OrderItemDto {

    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemDto(OrderItem item) {
     this.itemName = item.getItem().getName();
     this.orderPrice = item.getOrderPrice();
     this.count = item.getCount();
   }
  }

}
