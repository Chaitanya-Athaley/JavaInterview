# Outbox Pattern - Complete Guide

## What is Outbox Pattern?

**In Simple Words:**
The Outbox Pattern stores a business data change and the event/message describing that change in the same database transaction. A separate publisher then reads the outbox table and sends those messages to a message broker.

It solves the problem of updating a database and publishing an event reliably.

Example:

```
Create Order
   |
   v
Save order in orders table
Save OrderCreated event in outbox table
   |
   v
Commit one database transaction
   |
   v
Background publisher sends event to Kafka/RabbitMQ
```

If the service crashes after the database commit, the event is still safe in the outbox table and can be published later.

## Real-World Analogy

Think of a courier office.

### Without Outbox

```
Employee updates order record
Employee calls delivery team

Problem:
If phone call fails, the order is updated but delivery team never knows.
```

### With Outbox

```
Employee updates order record
Employee puts delivery request into outgoing mail tray
Office messenger keeps checking tray and sends pending requests

If messenger is temporarily unavailable, the request still remains in the tray.
```

The outbox table is the outgoing mail tray.

## The Problem: Dual Write Failure

Microservices often need to do two things:

1. Save data to their own database
2. Publish an event to a message broker

Example:

```
Order Service:
1. INSERT order into database
2. Publish OrderCreated event to Kafka
```

This creates a dual write problem because two different systems must be updated.

### Failure Scenario 1: Database Succeeds, Event Publish Fails

```
1. Save order to database       -> success
2. Publish OrderCreated event   -> failure
```

Result:
- Order exists in database
- Payment Service never receives event
- Inventory Service never reserves stock
- Notification Service never sends confirmation

The system is inconsistent.

### Failure Scenario 2: Event Publish Succeeds, Database Fails

```
1. Publish OrderCreated event   -> success
2. Save order to database       -> failure
```

Result:
- Other services think order exists
- Order does not exist in Order Service database
- Downstream services process invalid data

### Why Not Use Distributed Transactions?

Distributed transactions, such as two-phase commit, can coordinate database and broker writes, but they are usually avoided in microservices.

Problems:
- Slow
- Complex
- Hard to scale
- Not supported by all brokers and databases
- Tight coupling between systems
- Operationally fragile

The Outbox Pattern avoids distributed transactions by using one local database transaction.

## Solution With Outbox Pattern

Instead of directly publishing the event during business logic, save it in an outbox table inside the same transaction as the business change.

```
Application Transaction
+----------------------------------+
| Insert/Update business data      |
| Insert event into outbox table   |
| Commit transaction               |
+----------------------------------+
```

Then a separate publisher sends the event.

```
Outbox Publisher
+----------------------------------+
| Read pending outbox messages     |
| Publish to broker                |
| Mark messages as published       |
+----------------------------------+
```

## Architecture Overview

```
Client
  |
  v
+---------------------+
| Order Service       |
| Business Logic      |
+----------+----------+
           |
           | Same DB transaction
           v
+-----------------------------+
| Order Database              |
|                             |
| orders table                |
| outbox_events table         |
+-------------+---------------+
              |
              | Polling or CDC
              v
+-----------------------------+
| Outbox Publisher / Relay    |
+-------------+---------------+
              |
              v
+-----------------------------+
| Message Broker              |
| Kafka / RabbitMQ / SNS/SQS  |
+-------------+---------------+
              |
              v
+-----------------------------+
| Consumer Services           |
| Payment, Inventory, Email   |
+-----------------------------+
```

## Core Concepts

### 1. Business Transaction

The business operation and event record are saved together.

```
BEGIN TRANSACTION;

INSERT INTO orders (...);
INSERT INTO outbox_events (...);

COMMIT;
```

If the transaction commits, both the order and event exist.
If the transaction rolls back, neither exists.

### 2. Outbox Table

The outbox table stores messages waiting to be published.

Example schema:

```sql
CREATE TABLE outbox_events (
    id VARCHAR(100) PRIMARY KEY,
    aggregate_type VARCHAR(100) NOT NULL,
    aggregate_id VARCHAR(100) NOT NULL,
    event_type VARCHAR(100) NOT NULL,
    payload TEXT NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_at TIMESTAMP NOT NULL,
    published_at TIMESTAMP NULL,
    retry_count INT NOT NULL DEFAULT 0,
    last_error TEXT NULL
);
```

Common columns:
- `id`: unique message id
- `aggregate_type`: entity type, such as `Order`
- `aggregate_id`: entity id, such as `order-101`
- `event_type`: event name, such as `OrderCreated`
- `payload`: JSON message body
- `status`: `PENDING`, `PROCESSING`, `PUBLISHED`, `FAILED`
- `created_at`: time event was created
- `published_at`: time event was published
- `retry_count`: number of publish attempts
- `last_error`: latest failure reason

### 3. Message Relay / Publisher

The relay reads pending messages from the outbox and publishes them.

```
Every few seconds:
1. Find pending outbox rows
2. Lock rows for processing
3. Publish each event to broker
4. Mark successful rows as published
5. Retry failed rows later
```

### 4. Message Broker

The broker distributes events to other services.

Examples:
- Apache Kafka
- RabbitMQ
- AWS SNS/SQS
- Google Pub/Sub
- Azure Service Bus
- Apache Pulsar

### 5. Idempotent Consumers

Outbox publishing is usually at-least-once. This means consumers may receive the same event more than once.

Consumers must be idempotent:

```
If eventId already processed:
    ignore it
else:
    process event
    record eventId as processed
```

## How Outbox Pattern Works

### Step 1: Client Sends Request

```
POST /orders

{
  "customerId": "cust-45",
  "amount": 2500
}
```

### Step 2: Service Starts Local Transaction

```
BEGIN TRANSACTION
```

### Step 3: Save Business Data

```
INSERT INTO orders (id, customer_id, amount, status)
VALUES ('order-101', 'cust-45', 2500, 'CREATED');
```

### Step 4: Save Event to Outbox

```
INSERT INTO outbox_events (
    id,
    aggregate_type,
    aggregate_id,
    event_type,
    payload,
    status,
    created_at
) VALUES (
    'evt-1001',
    'Order',
    'order-101',
    'OrderCreated',
    '{"orderId":"order-101","customerId":"cust-45","amount":2500}',
    'PENDING',
    CURRENT_TIMESTAMP
);
```

### Step 5: Commit Transaction

```
COMMIT
```

Now both the order and the event are durable.

### Step 6: Publisher Sends Event

```
Outbox Publisher:
1. Reads evt-1001
2. Publishes OrderCreated to Kafka
3. Marks evt-1001 as PUBLISHED
```

### Step 7: Consumers React

```
Payment Service      -> process payment
Inventory Service    -> reserve stock
Notification Service -> send email
Analytics Service    -> record order
```

## Sequence Diagram

```
Client        Order Service        Database        Outbox Publisher        Broker
  |                |                   |                  |                  |
  | Create order   |                   |                  |                  |
  |--------------->|                   |                  |                  |
  |                | BEGIN TX          |                  |                  |
  |                |------------------>|                  |                  |
  |                | Insert order      |                  |                  |
  |                |------------------>|                  |                  |
  |                | Insert outbox row |                  |                  |
  |                |------------------>|                  |                  |
  |                | COMMIT            |                  |                  |
  |                |------------------>|                  |                  |
  | Success        |                   |                  |                  |
  |<---------------|                   |                  |                  |
  |                |                   | Poll pending     |                  |
  |                |                   |<-----------------|                  |
  |                |                   | Return event     |                  |
  |                |                   |----------------->|                  |
  |                |                   |                  | Publish event    |
  |                |                   |                  |----------------->|
  |                |                   | Mark published   |                  |
  |                |                   |<-----------------|                  |
```

## Java/Spring Boot Example

### Order Entity

```java
public class Order {
    private String id;
    private String customerId;
    private double amount;
    private String status;

    public Order(String id, String customerId, double amount) {
        this.id = id;
        this.customerId = customerId;
        this.amount = amount;
        this.status = "CREATED";
    }

    public String getId() {
        return id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatus() {
        return status;
    }
}
```

### Outbox Event Entity

```java
import java.time.LocalDateTime;

public class OutboxEvent {
    private String id;
    private String aggregateType;
    private String aggregateId;
    private String eventType;
    private String payload;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime publishedAt;
    private int retryCount;
    private String lastError;

    public OutboxEvent(String id, String aggregateType, String aggregateId,
                       String eventType, String payload) {
        this.id = id;
        this.aggregateType = aggregateType;
        this.aggregateId = aggregateId;
        this.eventType = eventType;
        this.payload = payload;
        this.status = "PENDING";
        this.createdAt = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public String getEventType() {
        return eventType;
    }

    public String getPayload() {
        return payload;
    }

    public void markPublished() {
        this.status = "PUBLISHED";
        this.publishedAt = LocalDateTime.now();
    }

    public void markFailed(String error) {
        this.status = "PENDING";
        this.retryCount++;
        this.lastError = error;
    }
}
```

### Service Method With One Transaction

```java
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OutboxRepository outboxRepository;

    public OrderService(OrderRepository orderRepository,
                        OutboxRepository outboxRepository) {
        this.orderRepository = orderRepository;
        this.outboxRepository = outboxRepository;
    }

    @Transactional
    public String createOrder(String customerId, double amount) {
        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, customerId, amount);

        orderRepository.save(order);

        String payload = "{"
                + "\"orderId\":\"" + orderId + "\","
                + "\"customerId\":\"" + customerId + "\","
                + "\"amount\":" + amount
                + "}";

        OutboxEvent event = new OutboxEvent(
                UUID.randomUUID().toString(),
                "Order",
                orderId,
                "OrderCreated",
                payload
        );

        outboxRepository.save(event);

        return orderId;
    }
}
```

Important point:

```
orderRepository.save(order)
outboxRepository.save(event)
```

Both happen in the same transaction because of `@Transactional`.

### Outbox Publisher

```java
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OutboxPublisher {
    private final OutboxRepository outboxRepository;
    private final MessageBrokerClient messageBrokerClient;

    public OutboxPublisher(OutboxRepository outboxRepository,
                           MessageBrokerClient messageBrokerClient) {
        this.outboxRepository = outboxRepository;
        this.messageBrokerClient = messageBrokerClient;
    }

    @Scheduled(fixedDelay = 5000)
    public void publishPendingEvents() {
        List<OutboxEvent> events = outboxRepository.findPendingEvents(100);

        for (OutboxEvent event : events) {
            try {
                messageBrokerClient.publish(event.getEventType(), event.getPayload());
                event.markPublished();
                outboxRepository.save(event);
            } catch (Exception ex) {
                event.markFailed(ex.getMessage());
                outboxRepository.save(event);
            }
        }
    }
}
```

This is a simple polling publisher. Production systems should also handle locking, retries, backoff, and duplicate delivery.

## Polling Publisher Approach

The application runs a scheduled job that polls the outbox table.

```
Outbox Table
    |
    v
Scheduled Poller
    |
    v
Message Broker
```

Pros:
- Simple to implement
- Easy to understand
- Works with most databases
- No special infrastructure required

Cons:
- Polling adds delay
- Too much polling can load the database
- Need row locking for multiple publisher instances
- Need cleanup strategy

Example query:

```sql
SELECT *
FROM outbox_events
WHERE status = 'PENDING'
ORDER BY created_at
LIMIT 100;
```

For multiple publishers, use database locking where available:

```sql
SELECT *
FROM outbox_events
WHERE status = 'PENDING'
ORDER BY created_at
LIMIT 100
FOR UPDATE SKIP LOCKED;
```

## Change Data Capture Approach

Instead of polling manually, use Change Data Capture (CDC) to stream outbox table changes.

```
Database transaction log
        |
        v
CDC Tool, such as Debezium
        |
        v
Kafka topic
```

How it works:

```
1. Application inserts row into outbox table
2. Database writes it to transaction log
3. CDC tool reads transaction log
4. CDC tool publishes event to broker
```

Pros:
- Lower application complexity
- Near real-time publishing
- Less polling pressure on database
- Scales well for high event volume

Cons:
- Requires CDC infrastructure
- More operational setup
- Database-specific behavior matters
- Debugging can be harder

Popular CDC tools:
- Debezium
- Kafka Connect
- AWS Database Migration Service
- PostgreSQL logical replication
- MySQL binlog streaming

## Delivery Guarantees

Outbox Pattern usually provides **at-least-once delivery**.

That means:
- Every committed event should eventually be published
- The same event may be published more than once
- Consumers must handle duplicates

Why duplicates can happen:

```
1. Publisher sends event to broker
2. Broker receives event
3. Publisher crashes before marking row as PUBLISHED
4. Publisher restarts
5. Same outbox row is sent again
```

Consumer solution:

```sql
CREATE TABLE processed_messages (
    message_id VARCHAR(100) PRIMARY KEY,
    processed_at TIMESTAMP NOT NULL
);
```

Consumer logic:

```
if message_id exists in processed_messages:
    skip message
else:
    process message
    insert message_id into processed_messages
```

## Ordering

Ordering can matter for events from the same aggregate.

Example:

```
OrderCreated
PaymentReceived
OrderCancelled
```

Best practices:
- Include `aggregate_id`
- Include `event_version` or sequence number
- Publish events for the same aggregate to the same broker partition
- Process events per aggregate in order
- Avoid relying on global ordering across all services

Example outbox columns for ordering:

```sql
aggregate_id VARCHAR(100) NOT NULL,
aggregate_version BIGINT NOT NULL
```

## Retry and Failure Handling

Publishing can fail because:
- Broker is down
- Network is slow
- Payload is invalid
- Authentication fails
- Topic or queue is unavailable

Common retry strategy:

```
PENDING -> PROCESSING -> PUBLISHED
                  |
                  v
               PENDING again with retry_count + 1
```

After too many retries:

```
PENDING -> FAILED
```

Failed messages should be visible to operations teams.

Useful columns:

```sql
retry_count INT,
next_retry_at TIMESTAMP,
last_error TEXT
```

## Cleanup and Archiving

Outbox tables grow continuously.

Common cleanup options:
- Delete published rows after a retention period
- Move published rows to archive table
- Partition table by date
- Keep failed rows longer
- Export old events to object storage

Example:

```sql
DELETE FROM outbox_events
WHERE status = 'PUBLISHED'
AND published_at < CURRENT_TIMESTAMP - INTERVAL '7 days';
```

## Benefits

- **Reliable event publishing**: Business changes and events are saved together.
- **No distributed transactions**: Uses local database transaction only.
- **Failure recovery**: Events survive service crashes.
- **Eventual consistency**: Downstream services are updated asynchronously.
- **Works with microservices**: Each service owns its database and events.
- **Auditable messages**: Pending and published events are visible in the database.
- **Broker independence**: Application logic is not tightly coupled to broker availability.

## Drawbacks

- **More moving parts**: Requires outbox table and publisher.
- **At-least-once delivery**: Consumers must be idempotent.
- **Eventual consistency**: Other services may see updates later.
- **Database growth**: Outbox table needs cleanup.
- **Polling overhead**: Polling can add database load.
- **Operational complexity**: Retries, locking, failures, and monitoring matter.
- **Ordering challenges**: Ordering must be designed carefully.

## When to Use Outbox Pattern

Use it when:
- A service updates its database and must publish an event
- You need reliable integration between services
- You want to avoid distributed transactions
- Message loss would cause business inconsistency
- Services communicate asynchronously
- You use event-driven microservices
- Broker downtime should not break business writes

Good examples:
- Order created event after saving order
- Payment completed event after saving payment
- Inventory changed event after updating stock
- User registered event after saving user
- Invoice generated event after saving invoice

## When Not to Use Outbox Pattern

Avoid it when:
- There is no database write involved
- Message loss is acceptable
- Simple synchronous calls are enough
- The service does not publish integration events
- The system is small and operational simplicity matters more
- You already use event sourcing and the event store is your publishing source

## Outbox Pattern vs Event Sourcing

| Feature | Outbox Pattern | Event Sourcing |
|---|---|---|
| Main goal | Reliable message publishing | Store state as events |
| Source of truth | Normal business tables | Event store |
| Event storage | Temporary or integration outbox | Permanent domain history |
| Current state | Stored in normal tables | Rebuilt from events |
| Complexity | Moderate | Higher |
| Common use | Microservice integration | Auditing, replay, domain history |

They can be used together, but they solve different problems.

## Outbox Pattern vs Direct Publish

| Feature | Direct Publish | Outbox Pattern |
|---|---|---|
| Database and broker consistency | Risky | Reliable |
| Failure recovery | Hard | Built in through outbox table |
| Implementation complexity | Simple | More complex |
| Duplicate messages | Possible | Possible |
| Message loss risk | Higher | Lower |
| Best for | Non-critical events | Critical integration events |

## Best Practices

1. Write business data and outbox event in the same transaction.
2. Use globally unique message ids.
3. Make consumers idempotent.
4. Add retry count and error details.
5. Monitor pending, failed, and delayed messages.
6. Use row locking if multiple publishers run.
7. Keep payload schema versioned.
8. Include aggregate id and event type.
9. Design ordering per aggregate, not globally.
10. Clean up or archive published events.
11. Avoid publishing directly inside the business transaction.
12. Keep event payloads stable and meaningful.

## Common Mistakes

- Saving business data and outbox event in separate transactions
- Publishing the message before committing the database transaction
- Assuming exactly-once delivery
- Forgetting idempotency in consumers
- Not monitoring stuck messages
- Letting the outbox table grow forever
- Using vague event names like `DataUpdated`
- Not storing enough data in the payload
- Not handling publisher crashes
- Running multiple pollers without row locking

## Interview Explanation

The Outbox Pattern solves the dual write problem in microservices. When a service needs to update its database and publish an event, it writes both the business data and the event record into an outbox table in the same local transaction. A separate relay or CDC process later reads the outbox and publishes messages to a broker. This avoids distributed transactions and prevents message loss, but it usually gives at-least-once delivery, so consumers must be idempotent.

## Summary

The Outbox Pattern is a reliability pattern for event-driven microservices. It ensures that when a business change is committed, the corresponding event is also durably recorded. A publisher sends the event asynchronously to a broker. The main tradeoffs are eventual consistency, duplicate delivery handling, and extra operational work around publishing, retries, locking, and cleanup.
