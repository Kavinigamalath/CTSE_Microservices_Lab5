package com.example.itemservice.web;

import com.example.itemservice.model.Item;
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
@RequestMapping("/items")
public class ItemController {

  private final List<Item> items = new ArrayList<>(List.of(
      seed(1, "Book", 12.50),
      seed(2, "Laptop", 899.00),
      seed(3, "Phone", 499.00)
  ));

  private final AtomicInteger idCounter = new AtomicInteger(items.size());

  private static Item seed(int id, String name, double price) {
    Item item = new Item();
    item.setId(id);
    item.setName(name);
    item.setPrice(price);
    return item;
  }

  @GetMapping
  public List<Item> all() {
    return items;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public Item create(@RequestBody Item item) {
    item.setId(idCounter.incrementAndGet());
    if (item.getName() == null || item.getName().isBlank()) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Name is required");
    }
    items.add(item);
    return item;
  }

  @GetMapping("/{id}")
  public Item one(@PathVariable int id) {
    return items.stream()
        .filter(i -> i.getId() == id)
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item not found"));
  }
}
