package com.example.paymentservice.web;

import com.example.paymentservice.model.PaymentRequest;
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
@RequestMapping("/payments")
public class PaymentController {

  private final List<PaymentRequest> history = new ArrayList<>();
  private final AtomicInteger idCounter = new AtomicInteger(0);

  @PostMapping("/process")
  @ResponseStatus(HttpStatus.CREATED)
  public PaymentRequest pay(@RequestBody PaymentRequest request) {
    if (request.getOrderId() <= 0) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "orderId must be positive");
    }
    request.setId(idCounter.incrementAndGet());
    request.setStatus("SUCCESS");
    history.add(request);
    return request;
  }

  @GetMapping
  public List<PaymentRequest> all() {
    return history;
  }

  @GetMapping("/{id}")
  public PaymentRequest one(@PathVariable int id) {
    return history.stream()
        .filter(p -> p.getId() == id)
        .findFirst()
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));
  }
}
