package com.example.orderservice.web;

import com.example.orderservice.model.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/orders")
public class OrderController {

  private final List<Order> orders = new ArrayList<>();
  private final AtomicInteger idCounter = new AtomicInteger(0);

  @GetMapping
  public List<Order> all() {
    return orders;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Order create(@RequestBody Order order) {
    if (order.getItem() == null || order.getItem().isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Item is required");
    }
    order.setId(idCounter.incrementAndGet());
    order.setStatus("PENDING");
    orders.add(order);
    return order;
  }

  @GetMapping("/{id}")
  public Order one(@PathVariable int id) {
    return orders.stream()
        .filter(o -> o.getId() == id)
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
  }
}
