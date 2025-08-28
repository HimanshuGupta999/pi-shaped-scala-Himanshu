package com.pishape.day1.consumer;

import com.pishape.day1.config.RabbitMQConfig;
import com.pishape.day1.model.Order;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void receiveOrder(Order order) {
        System.out.println("📥 Received OrderPlaced event: " + order.getOrderId() +
                " | Customer: " + order.getCustomerName() +
                " | Product: " + order.getProduct());
        System.out.println("✅ Order fulfilled successfully!");
    }
}
