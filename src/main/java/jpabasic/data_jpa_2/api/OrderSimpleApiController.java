package jpabasic.data_jpa_2.api;

import jpabasic.data_jpa_2.domain.Address;
import jpabasic.data_jpa_2.domain.Order;
import jpabasic.data_jpa_2.domain.OrderStatus;
import jpabasic.data_jpa_2.repository.OrderRepository;
import jpabasic.data_jpa_2.repository.OrderSearch;
import jpabasic.data_jpa_2.repository.OrderSimpleQueryDto;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

  private final OrderRepository orderRepository;

  @GetMapping("/api/v1/simple-orders")
  public List<Order> ordersV1() {
    List<Order> all = orderRepository.findAllByString(new OrderSearch());
    for (Order order : all) {
      order.getMember().getName();
      order.getDelivery().getAddress();
    }
    return all;
  }

  @GetMapping("/api/v2/simple-orders")
  public List<SimpleOrderDto> orderV2() {

    return orderRepository.findAllByString(new OrderSearch())
            .stream()
            .map(o -> new SimpleOrderDto(o))
            .collect(Collectors.toList());
  }

  @GetMapping("/api/v3/simple-orders")
  public List<SimpleOrderDto> orderV3() {

    return orderRepository.findAllWithMemberDelivery()
            .stream()
            .map(o -> new SimpleOrderDto(o))
            .collect(Collectors.toList());
  }

  @GetMapping("/api/v4/simple-orders")
  public List<OrderSimpleQueryDto> orderV4() {
    return orderRepository.findOrderDtos();
  }

  @Data
  public static class SimpleOrderDto {
    private Long orderId;
    private String name;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private Address address;

    public SimpleOrderDto(Order order) {
      this.orderId = order.getId();
      this.name = order.getMember().getName();
      this.orderDate = order.getOrderDate();
      this.orderStatus = order.getStatus();
      this.address = order.getDelivery().getAddress();
    }
  }
}
