package com.pishape.day1.producer;

import com.pishape.day1.config.RabbitMQConfig;
import com.pishape.day1.model.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {
    private final RabbitTemplate rabbitTemplate;

    public OrderProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrder(Order order) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.ORDER_QUEUE, order);
        System.out.println("📤 Sent OrderPlaced event: " + order.getOrderId());
    }
}

