package com.pishape.day1.controller;

import com.pishape.day1.model.Order;
import com.pishape.day1.producer.OrderProducer;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderProducer orderProducer;

    public OrderController(OrderProducer orderProducer) {
        this.orderProducer = orderProducer;
    }

    @PostMapping
    public String placeOrder(@RequestBody Order order) {
        order.setOrderId(UUID.randomUUID().toString());
        orderProducer.sendOrder(order);
        return "Order placed successfully! ID: " + order.getOrderId();
    }
}

