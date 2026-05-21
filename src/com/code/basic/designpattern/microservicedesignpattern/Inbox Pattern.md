# Inbox Pattern - Complete Guide

## What is Inbox Pattern?

**In Simple Words:**
The Inbox Pattern stores incoming messages in a local database before or while processing them, so the service can safely handle duplicate messages, retries, crashes, and failures.

It is mainly used to make message consumption reliable.

Example:

```
Message arrives from broker
   |
   v
Save message id in inbox table
   |
   v
Process business logic
   |
   v
Mark message as processed
```

If the same message arrives again, the service checks the inbox table and avoids processing it twice.

## Real-World Analogy

Think of an office receiving courier packages.

### Without Inbox

```
Courier arrives
Employee opens package immediately
Employee performs work

Problem:
If the same package arrives again, employee may repeat the same work.
```

### With Inbox

```
Courier arrives
Reception records tracking number in register
Employee checks register before processing

If tracking number already exists:
    do not process again
```

The inbox table is the delivery register.

## Why Inbox Pattern is Needed

Message brokers usually provide at-least-once delivery.

That means:
- A message will be delivered
- It may be delivered more than once
- The consumer must handle duplicates safely

Duplicate messages can happen when:

```
1. Consumer receives message
2. Consumer processes business logic
3. Consumer crashes before acknowledging message
4. Broker sends same message again
5. Business logic may run twice
```

Without Inbox Pattern:
- Payment may be charged twice
- Inventory may be reduced twice
- Email may be sent twice
- Order status may be updated incorrectly
- Reports may count the same event multiple times

## Problem Without Inbox Pattern

Example: Payment Service consumes `OrderCreated`.

```
Broker sends OrderCreated(order-101)
        |
        v
Payment Service charges customer
        |
        v
Service crashes before broker acknowledgment
        |
        v
Broker redelivers same OrderCreated(order-101)
        |
        v
Payment Service charges customer again
```

This is dangerous because the consumer cannot assume each message is delivered exactly once.

## Solution With Inbox Pattern

Store every received message id in an inbox table and process messages idempotently.

```
Message received
        |
        v
Check inbox table
        |
        +-- already processed -> skip
        |
        +-- new message -> save and process
```

The inbox table acts as a memory of what the service has already seen.

## Architecture Overview

```
Message Broker
Kafka / RabbitMQ / SQS
        |
        v
+--------------------------+
| Consumer Service         |
|                          |
| 1. Receive message       |
| 2. Save to inbox table   |
| 3. Process business work |
| 4. Mark processed        |
+------------+-------------+
             |
             v
+--------------------------+
| Service Database         |
|                          |
| business tables          |
| inbox_messages table     |
+--------------------------+
```

## Core Concepts

### 1. Message Id

Every message must have a unique id.

Example:

```json
{
  "messageId": "evt-1001",
  "eventType": "OrderCreated",
  "aggregateId": "order-101",
  "createdAt": "2026-05-21T10:30:00Z",
  "payload": {
    "orderId": "order-101",
    "customerId": "cust-45",
    "amount": 2500
  }
}
```

The consumer uses `messageId` to identify duplicates.

### 2. Inbox Table

The inbox table records received messages and their processing status.

Example schema:

```sql
CREATE TABLE inbox_messages (
    message_id VARCHAR(100) PRIMARY KEY,
    message_type VARCHAR(100) NOT NULL,
    source_service VARCHAR(100) NULL,
    payload TEXT NOT NULL,
    status VARCHAR(30) NOT NULL,
    received_at TIMESTAMP NOT NULL,
    processed_at TIMESTAMP NULL,
    retry_count INT NOT NULL DEFAULT 0,
    last_error TEXT NULL
);
```

Common statuses:

```
RECEIVED
PROCESSING
PROCESSED
FAILED
IGNORED
```

### 3. Idempotent Consumer

An idempotent operation can run multiple times and still produce the same final result.

Example:

```
Set order status to PAID
```

This is safer than:

```
Add Rs. 2500 to paid amount
```

Because adding can happen twice, but setting the same status twice does not change the result.

### 4. Acknowledgment

The consumer should acknowledge the broker message only after the inbox record and business changes are safely committed.

```
Process message successfully
        |
        v
Commit database transaction
        |
        v
Acknowledge broker message
```

If the service crashes before acknowledgment, the broker may redeliver the message. The inbox table protects the service from duplicate processing.

## How Inbox Pattern Works

### Step 1: Message Arrives

```
OrderCreated {
  messageId: "evt-1001",
  orderId: "order-101",
  amount: 2500
}
```

### Step 2: Consumer Checks Inbox

```sql
SELECT message_id
FROM inbox_messages
WHERE message_id = 'evt-1001';
```

### Step 3: If Already Processed, Skip

```
Message found with status PROCESSED
        |
        v
Skip business logic
        |
        v
Acknowledge message
```

### Step 4: If New, Save and Process

```
BEGIN TRANSACTION;

INSERT INTO inbox_messages (...);
UPDATE inventory SET reserved = reserved + 1 WHERE product_id = 'p-10';
UPDATE inbox_messages SET status = 'PROCESSED';

COMMIT;
```

### Step 5: Acknowledge Broker Message

```
Database commit success
        |
        v
Broker acknowledgment
```

## Sequence Diagram

```
Broker        Consumer Service        Database
  |                  |                    |
  | Deliver event    |                    |
  |----------------->|                    |
  |                  | Check inbox        |
  |                  |------------------->|
  |                  | Not found          |
  |                  |<-------------------|
  |                  | Begin transaction  |
  |                  |------------------->|
  |                  | Insert inbox row   |
  |                  |------------------->|
  |                  | Business update    |
  |                  |------------------->|
  |                  | Mark processed     |
  |                  |------------------->|
  |                  | Commit             |
  |                  |------------------->|
  | Acknowledge      |                    |
  |<-----------------|                    |
```

Duplicate delivery:

```
Broker        Consumer Service        Database
  |                  |                    |
  | Deliver same evt |                    |
  |----------------->|                    |
  |                  | Check inbox        |
  |                  |------------------->|
  |                  | Already processed  |
  |                  |<-------------------|
  | Acknowledge      |                    |
  |<-----------------|                    |
```

## Java/Spring Boot Example

### Inbox Message

```java
import java.time.LocalDateTime;

public class InboxMessage {
    private String messageId;
    private String messageType;
    private String payload;
    private String status;
    private LocalDateTime receivedAt;
    private LocalDateTime processedAt;
    private int retryCount;
    private String lastError;

    public InboxMessage(String messageId, String messageType, String payload) {
        this.messageId = messageId;
        this.messageType = messageType;
        this.payload = payload;
        this.status = "RECEIVED";
        this.receivedAt = LocalDateTime.now();
    }

    public String getMessageId() {
        return messageId;
    }

    public void markProcessed() {
        this.status = "PROCESSED";
        this.processedAt = LocalDateTime.now();
    }

    public void markFailed(String error) {
        this.status = "FAILED";
        this.retryCount++;
        this.lastError = error;
    }
}
```

### Message Consumer

```java
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderCreatedConsumer {
    private final InboxRepository inboxRepository;
    private final InventoryRepository inventoryRepository;

    public OrderCreatedConsumer(InboxRepository inboxRepository,
                                InventoryRepository inventoryRepository) {
        this.inboxRepository = inboxRepository;
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public void handle(OrderCreatedMessage message) {
        if (inboxRepository.existsById(message.getMessageId())) {
            return;
        }

        InboxMessage inboxMessage = new InboxMessage(
                message.getMessageId(),
                "OrderCreated",
                message.getPayload()
        );

        inboxRepository.save(inboxMessage);

        inventoryRepository.reserveItems(
                message.getOrderId(),
                message.getItems()
        );

        inboxMessage.markProcessed();
        inboxRepository.save(inboxMessage);
    }
}
```

The important part:

```
1. Check message id
2. Save inbox row
3. Perform business update
4. Mark processed
```

All should happen in one local transaction.

### Safer Insert-First Approach

In high-concurrency systems, two duplicate messages may be processed at the same time. A safer approach is to insert the inbox row first and rely on the unique primary key.

```java
@Transactional
public void handle(OrderCreatedMessage message) {
    try {
        inboxRepository.insertReceivedMessage(
                message.getMessageId(),
                "OrderCreated",
                message.getPayload()
        );
    } catch (DuplicateMessageException ex) {
        return;
    }

    inventoryRepository.reserveItems(
            message.getOrderId(),
            message.getItems()
    );

    inboxRepository.markProcessed(message.getMessageId());
}
```

This avoids a race condition between `existsById` and `save`.

## SQL Example

### Insert Message

```sql
INSERT INTO inbox_messages (
    message_id,
    message_type,
    payload,
    status,
    received_at
) VALUES (
    'evt-1001',
    'OrderCreated',
    '{"orderId":"order-101","amount":2500}',
    'RECEIVED',
    CURRENT_TIMESTAMP
);
```

If `message_id` is the primary key, duplicate inserts fail automatically.

### Mark Processed

```sql
UPDATE inbox_messages
SET status = 'PROCESSED',
    processed_at = CURRENT_TIMESTAMP
WHERE message_id = 'evt-1001';
```

### Find Failed Messages

```sql
SELECT *
FROM inbox_messages
WHERE status = 'FAILED'
ORDER BY received_at;
```

## Processing Strategies

### 1. Process Immediately

The consumer processes the message as soon as it arrives.

```
Broker -> Consumer -> Inbox insert + business logic -> Ack
```

Pros:
- Simple
- Low latency
- Easy to understand

Cons:
- Broker listener does more work
- Long processing may delay acknowledgment
- Harder to control retries separately

### 2. Store First, Process Later

The consumer first stores the incoming message. A background worker processes inbox rows.

```
Broker -> Consumer -> Inbox insert -> Ack
                              |
                              v
                       Background worker
                              |
                              v
                       Business processing
```

Pros:
- Fast broker acknowledgment
- Controlled retries
- Useful for heavy processing
- Better failure visibility

Cons:
- More moving parts
- Adds processing delay
- Requires worker scheduling

## Retry Handling

Messages can fail during processing because:
- Downstream service is unavailable
- Database deadlock occurs
- Payload is invalid
- Business rule fails
- Network call times out

Retry states:

```
RECEIVED -> PROCESSING -> PROCESSED
                    |
                    v
                 FAILED
                    |
                    v
                RETRY LATER
```

Useful columns:

```sql
retry_count INT,
next_retry_at TIMESTAMP,
last_error TEXT
```

Retry query:

```sql
SELECT *
FROM inbox_messages
WHERE status = 'FAILED'
AND retry_count < 5
AND next_retry_at <= CURRENT_TIMESTAMP;
```

## Dead Letter Handling

After too many retries, move the message to a dead letter state or dead letter queue.

```
FAILED with retry_count >= 5
        |
        v
DEAD_LETTER
```

Dead letter messages should be investigated manually or replayed after fixing the cause.

Common dead letter reasons:
- Invalid payload
- Unknown event type
- Missing required data
- Permanent business rule failure
- Consumer code bug

## Inbox Pattern With Outbox Pattern

Outbox and Inbox are often used together.

```
Service A
Business update + Outbox event
        |
        v
Message Broker
        |
        v
Service B
Inbox record + Business update
```

Combined reliability:
- Outbox protects message publishing
- Inbox protects message consuming
- Consumers handle duplicates safely
- System becomes eventually consistent

Example:

```
Order Service:
  Save order + save OrderCreated in outbox

Broker:
  Delivers OrderCreated, maybe more than once

Inventory Service:
  Save message id in inbox + reserve stock once
```

## Delivery Guarantees

Inbox Pattern helps achieve effectively-once processing for a service's business state, even when the broker delivers messages at least once.

Important wording:
- Broker delivery may still be at-least-once
- Consumer processing becomes idempotent
- Business side effects should happen once

This does not magically give true exactly-once delivery across all systems. It gives practical duplicate protection when implemented correctly.

## Ordering

Inbox Pattern handles duplicates, but it does not automatically solve ordering.

Example:

```
OrderCancelled arrives before OrderCreated
```

Possible solutions:
- Use broker partitioning by aggregate id
- Include sequence numbers
- Store out-of-order messages until earlier messages arrive
- Make handlers tolerant of missing previous events
- Use aggregate version checks

Example columns:

```sql
aggregate_id VARCHAR(100),
aggregate_version BIGINT
```

## Cleanup and Archiving

Inbox tables grow over time.

Common cleanup options:
- Keep processed rows for a retention period
- Archive old rows
- Partition by date
- Keep failed and dead-letter rows longer
- Store only message id after payload is no longer needed

Example cleanup:

```sql
DELETE FROM inbox_messages
WHERE status = 'PROCESSED'
AND processed_at < CURRENT_TIMESTAMP - INTERVAL '30 days';
```

Choose retention based on how long duplicate messages may be redelivered.

## Benefits

- **Duplicate protection**: Same message is not processed twice.
- **Reliable consumption**: Consumer can recover after crashes.
- **Idempotency support**: Message ids make deduplication practical.
- **Failure tracking**: Failed messages are visible in the database.
- **Retry control**: Retries can be managed safely.
- **Pairs well with Outbox**: Gives reliable publish and consume flow.
- **Local transaction**: Inbox record and business update can commit together.

## Drawbacks

- **More database writes**: Every message creates an inbox record.
- **More storage**: Inbox table needs retention and cleanup.
- **More complexity**: Requires status handling and retry design.
- **Ordering not automatic**: Must be solved separately if needed.
- **Consumer logic discipline**: Handlers must be idempotent.
- **Possible latency**: Store-first processing can add delay.
- **Operational work**: Failed and stuck messages must be monitored.

## When to Use Inbox Pattern

Use it when:
- Message duplicates would cause business problems
- Broker provides at-least-once delivery
- Consumer performs important side effects
- You need reliable event consumption
- You use Outbox Pattern in producer services
- You need retry and failure tracking
- Consumers update their own database from events

Good examples:
- Payment processing
- Inventory reservation
- Reward point calculation
- Invoice generation
- Order status updates
- Email notification deduplication
- Analytics events that must not double count

## When Not to Use Inbox Pattern

Avoid it when:
- Duplicate messages are harmless
- Consumer does not change state
- Processing is naturally idempotent without storage
- The system is small and simple
- Message volume is huge and approximate processing is acceptable
- Retention and cleanup overhead is not worth it

## Inbox Pattern vs Outbox Pattern

| Feature | Inbox Pattern | Outbox Pattern |
|---|---|---|
| Main goal | Reliable message consumption | Reliable message publishing |
| Table stores | Incoming messages | Outgoing messages |
| Protects against | Duplicate processing | Lost published events |
| Used by | Consumer service | Producer service |
| Common guarantee | Idempotent processing | At-least-once publishing |
| Key requirement | Unique message id | Same transaction as business write |

They are complementary patterns.

## Inbox Pattern vs Idempotent Consumer

| Feature | Inbox Pattern | Idempotent Consumer |
|---|---|---|
| Meaning | Stores received messages | Handles repeat processing safely |
| Implementation | Database table or durable store | Code and data design |
| Main use | Deduplication and retries | Safe repeated execution |
| Relationship | A way to implement idempotency | Broader concept |

Inbox Pattern is one common way to build an idempotent consumer.

## Best Practices

1. Require every message to have a unique id.
2. Put a unique constraint on `message_id`.
3. Save inbox record and business update in the same transaction.
4. Acknowledge broker messages only after commit.
5. Prefer insert-first deduplication for concurrency safety.
6. Make handlers idempotent.
7. Store retry count and last error.
8. Monitor failed and stuck messages.
9. Define cleanup and retention policy.
10. Include aggregate id and version when ordering matters.
11. Do not assume exactly-once broker delivery.
12. Keep dead-letter handling visible to operations teams.

## Common Mistakes

- Checking for duplicates without a unique database constraint
- Acknowledging broker messages before committing business changes
- Assuming duplicates will never happen
- Retrying forever without dead-letter handling
- Letting inbox table grow forever
- Not monitoring failed messages
- Mixing duplicate handling with unclear business logic
- Forgetting ordering requirements
- Processing side effects outside the database transaction without safeguards

## Interview Explanation

The Inbox Pattern is used by message consumers to process incoming messages reliably. The consumer stores each received message id in an inbox table and checks that table before processing. If the message was already processed, it is skipped. If it is new, the service saves the inbox record and performs the business update in the same local transaction. This protects against duplicate delivery and consumer crashes, but it requires idempotent handlers, retry handling, cleanup, and monitoring.

## Summary

The Inbox Pattern solves the reliable message consumption side of event-driven microservices. It protects consumers from duplicate messages by recording received message ids and processing each message safely. It is often paired with the Outbox Pattern: Outbox makes publishing reliable, while Inbox makes consuming reliable.
