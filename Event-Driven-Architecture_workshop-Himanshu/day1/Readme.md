# Day1 Assignment
# Event-Driven Architecture with Java and RabbitMQ - by Himanshu

This project provides a practical implementation of an Event-Driven Architecture (EDA) using a simple e-commerce order processing system. It demonstrates how a **Producer** service can publish an `OrderPlaced` event, how a **RabbitMQ** message broker can reliably route this event, and how a **Consumer** service can process it asynchronously.

This example showcases key EDA principles like loose coupling, asynchronous communication, and improved resilience, built with a modern Java stack.

## Technology Stack

*   **Java 21**: The latest Long-Term Support version of Java.
*   **Spring Boot 3.2.2**: Framework for building robust, stand-alone Java applications.
*   **RabbitMQ**: An open-source message broker used for asynchronous communication.
*   **Maven**: Build automation and dependency management tool.
*   **Docker**: For running the RabbitMQ instance in a containerized environment.

---

## Core Concept Questions

### 1. What is an event in Event-Driven Architecture?

In an event-driven system, an **event** is an immutable record of a significant business occurrence. Think of it as a factual statement that "something happened." It doesn't contain any instructions on what to do; it simply announces a change of state.

**Real-world Example (outside of technology):**
Imagine borrowing a book from a library. When the librarian scans your book, an event, let's call it `BookCheckedOut`, occurs. This single event triggers multiple independent processes without the librarian needing to manage them directly:
*   The **Library Catalog System** reacts by marking the book as unavailable.
*   The **User Account System** reacts by adding the book to your borrowed list and setting a due date.
*   The **Notification System** might react by scheduling a "due date reminder" email.
*   An **Analytics System** could react by incrementing a counter for that book's popularity.

The `BookCheckedOut` event is the central fact, and each system reacts to it independently, demonstrating the power of a decoupled, event-driven approach.

### 2. Compare Event-Driven Architecture (EDA) with Request-Response Architecture.

| Aspect | Request-Response Architecture | Event-Driven Architecture (EDA) |
| :--- | :--- | :--- |
| **Interaction** | **Synchronous & Blocking:** A client makes a request and is blocked, waiting for the server to process it and return a direct response. | **Asynchronous & Non-Blocking:** A producer sends an event and immediately moves on, without waiting for a response from the consumers. |
| **Coupling** | **Tightly Coupled:** The client (requester) must know the specific location (endpoint) and interface of the server (responder). | **Loosely Coupled:** The producer and consumer do not know about each other. They only communicate indirectly through the message broker. |
| **Scalability** | **Challenging:** Scaling often requires scaling the entire request-response chain together. A slow responder can create a bottleneck for the requester. | **Highly Scalable:** Producers and consumers can be scaled independently. If event processing is slow, you can simply add more consumer instances. |
| **Resilience** | **Brittle:** If the responding service is down, the client's request fails immediately. This can lead to cascading failures across the system. | **Highly Resilient:** If a consumer is down, the message broker acts as a buffer, holding events until the consumer recovers and can process them. The producer is unaffected. |
| **Advantages** | Simple, predictable, and easy to implement for direct interactions where an immediate answer is required. | Excellent for complex, distributed systems requiring high scalability, resilience, and flexibility. |
| **Disadvantages**| Can lead to performance bottlenecks and a tightly coupled "monolith" of services. | More complex to debug and monitor due to its asynchronous nature. Requires careful management of eventual consistency. |

### 3. How would you use EDA to manage an E-commerce order workflow?

**Placing an Order** → Producer publishes "OrderPlaced" event.

**Sending Confirmation Email** → Email Service subscribes to "OrderPlaced" and sends email asynchronously.

**Updating Inventory** → Inventory Service listens for "OrderPlaced" and reduces stock accordingly.

### 4. Why is EDA a great fit for Microservices and Cloud-Native systems?

1. **Loose Coupling** – Each service reacts to events independently, without direct dependencies.

2. **Elastic Scalability** – Consumers can be scaled horizontally to handle more events in peak traffic.

### 5. How does EDA help build scalable systems? (With Use Cases)

EDA enables scalability by allowing every part of a workflow to be scaled independently based on its specific needs. You can scale the number of consumers processing a particular event without impacting the producers or any other part of the system.

**Real-world Use Cases:**

1.  **Food Delivery App (e.g., DoorDash, Zomato):**
    *   **The Challenge:** When an order is placed, multiple things need to happen in parallel: the restaurant must be notified, a delivery driver must be found, the customer needs real-time tracking, and payment must be processed. A traditional request-response system would be slow and brittle.
    *   **The EDA Solution:** A single `OrderPlaced` event is published. This event is consumed independently by:
        *   A `Restaurant Service` that sends the order to the restaurant's terminal.
        *   A `Dispatch Service` that starts searching for nearby drivers.
        *   A `Payment Service` that processes the transaction.
        *   A `Tracking Service` that creates a tracking session for the customer.
            Each of these services can be scaled independently. If there's a surge in orders, you can add more instances of the Dispatch Service without affecting the restaurant notifications.

2.  **Financial Trading Platforms:**
    *   **The Challenge:** A trading platform needs to process millions of market data changes (events) per second, execute trades with extremely low latency, perform real-time risk analysis, and log everything for compliance.
    *   **The EDA Solution:** Every price change in the market is an event. These `MarketDataUpdated` events are broadcast through a high-speed message broker.
        *   `Trading Algorithm Services` consume these events to make buy/sell decisions.
        *   A `Risk Management Service` consumes them to continuously calculate portfolio risk.
        *   A `User Dashboard Service` consumes them to update real-time charts for users.
            This parallel, event-driven processing is the only way to achieve the high throughput and low latency required in financial markets.

---

## How to Run This Project

### Prerequisites

*   Java 21 JDK
*   Apache Maven
*   Docker Desktop

### Steps to Run

1.  **Start RabbitMQ using Docker:**
    Make sure Docker is running. In the project's root directory (`day1/`), run the following command to start the RabbitMQ container.
    ```bash
    docker run -d --hostname rabbit --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:3-management
    ```

2. **Run the Producer Application:**
    In a new terminal, navigate to the `day1` directory and run:
    ```bash
    mvn spring-boot:run
    ```

3. **Place an Order to Trigger an Event:**
    In a third terminal, use `curl` to send a POST request to the producer's API:
    ```bash
    curl -X POST http://localhost:8080/orders \
    -H "Content-Type: application/json" \
    -d '{"customerName": "John Doe", "product": "Laptop"}'
    ```
---

## Verification: Expected Output

After sending the `curl` command, you will see immediate output.

📤 Sent OrderPlaced event: 123e4567-e89b-12d3-a456-426614174000
📥 Received OrderPlaced event: 123e4567-e89b-12d3-a456-426614174000 | Customer: John Doe | Product: Laptop
✅ Order fulfilled successfully!

## Screenshots

Captured screenshots to show order placed successfully.
1. Screenshot from 2025-08-28 17-51-11.png
2. Screenshot from 2025-08-28 17-51-36.png
