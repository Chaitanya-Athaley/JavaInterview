# Event Sourcing Pattern - Complete Guide

## What is Event Sourcing Pattern?

**In Simple Words:**
Event Sourcing stores every change to application state as a sequence of immutable events. Instead of saving only the latest state, the system saves the full history of what happened.

Traditional systems store current state:

```
Order Table
+----------+----------+----------+
| order_id | status   | amount   |
+----------+----------+----------+
| 101      | SHIPPED  | 2500     |
+----------+----------+----------+
```

Event Sourcing stores the history:

```
Event Store
+-----+----------+------------------+
| seq | order_id | event_type       |
+-----+----------+------------------+
| 1   | 101      | OrderCreated     |
| 2   | 101      | PaymentReceived  |
| 3   | 101      | OrderPacked      |
| 4   | 101      | OrderShipped     |
+-----+----------+------------------+
```

The current state is rebuilt by replaying events in order.

## Real-World Analogy

Think of a bank account.

### Traditional State Storage

```
Account Balance = Rs. 15,000
```

You know the balance, but not how it reached that value.

### Event Sourcing

```
AccountOpened       +0
MoneyDeposited      +20,000
MoneyWithdrawn      -3,000
ServiceCharge       -500
MoneyTransferred    -1,500

Current Balance = Rs. 15,000
```

Now you know both:
- Current balance
- Complete history of every change

## Problem Without Event Sourcing

In a normal CRUD system, every update overwrites the previous state.

```
Initial:
Order #101 status = CREATED

Update 1:
Order #101 status = PAID

Update 2:
Order #101 status = SHIPPED
```

Database now stores:

```
Order #101 status = SHIPPED
```

Problems:
- No built-in history
- Difficult to answer "how did this happen?"
- Audit logs are often incomplete or inconsistent
- Debugging production issues is harder
- Rebuilding read models is difficult
- Temporal queries need extra custom tables

## Solution With Event Sourcing

Store every business change as an event.

```
OrderCreated
PaymentReceived
OrderPacked
OrderShipped
```

The system never updates or deletes old events. It only appends new events.

```
Command -> Business Logic -> New Event -> Event Store
                                      |
                                      v
                                Projections
                                      |
                                      v
                                Read Models
```

## Architecture Overview

```
Client
  |
  v
+-------------------+
| Command API       |
| CreateOrder       |
| PayOrder          |
| ShipOrder         |
+---------+---------+
          |
          v
+-------------------+
| Aggregate         |
| Business Rules    |
| Current State     |
+---------+---------+
          |
          v
+-------------------+
| Event Store       |
| Append-only log   |
+---------+---------+
          |
          v
+-------------------+
| Event Handlers    |
| Projections       |
+---------+---------+
          |
          v
+-------------------+
| Read Database     |
| Query Models      |
+-------------------+
```

## Core Concepts

### 1. Event

An event is an immutable fact that something already happened.

Examples:

```
OrderCreated
PaymentReceived
OrderCancelled
ProductAddedToCart
MoneyDeposited
EmailChanged
```

Good event names use past tense because they represent completed facts.

Example event:

```json
{
  "eventId": "evt-1001",
  "eventType": "OrderCreated",
  "aggregateId": "order-101",
  "aggregateType": "Order",
  "version": 1,
  "timestamp": "2026-05-21T10:30:00Z",
  "data": {
    "orderId": "order-101",
    "customerId": "cust-45",
    "amount": 2500
  }
}
```

### 2. Event Store

The event store is an append-only database that stores events in order.

Characteristics:
- Events are appended, not updated
- Events are immutable
- Events are stored with version numbers
- Events can be replayed
- Events are the source of truth

Example:

```
+----------+---------+------------------+---------+
| stream   | version | event_type       | data    |
+----------+---------+------------------+---------+
| order-1  | 1       | OrderCreated     | {...}   |
| order-1  | 2       | PaymentReceived  | {...}   |
| order-1  | 3       | OrderShipped     | {...}   |
+----------+---------+------------------+---------+
```

### 3. Aggregate

An aggregate is a domain object that enforces business rules.

For example, an `Order` aggregate may enforce:
- Cannot ship an unpaid order
- Cannot cancel a shipped order
- Cannot accept payment twice

The aggregate is rebuilt from events before handling a command.

```
Load events for order-101
        |
        v
Replay events
        |
        v
Rebuild Order state
        |
        v
Apply command
        |
        v
Append new event
```

### 4. Command

A command is a request to do something.

Commands are not facts. They can succeed or fail.

Examples:

```
CreateOrder
ReceivePayment
ShipOrder
CancelOrder
ChangeEmail
```

Command vs Event:

| Command | Event |
|---|---|
| Request to do something | Fact that something happened |
| Can be rejected | Cannot be changed |
| Imperative | Past tense |
| `ShipOrder` | `OrderShipped` |

### 5. Projection

A projection converts events into read-friendly views.

Events:

```
OrderCreated
PaymentReceived
OrderShipped
```

Projection:

```
OrderSummaryView
+----------+---------+----------+
| order_id | status  | paid     |
+----------+---------+----------+
| 101      | SHIPPED | true     |
+----------+---------+----------+
```

The projection is not the source of truth. It can be rebuilt from events.

### 6. Snapshot

A snapshot stores aggregate state at a point in time so the system does not need to replay thousands of events every time.

```
Events 1 to 1000 -> Snapshot at version 1000
Events 1001 to 1010 -> Replay only recent events
```

Snapshots improve performance, but events remain the real source of truth.

## How Event Sourcing Works

### Step 1: User Sends Command

```
POST /orders/101/pay

Command:
ReceivePayment(orderId=101, amount=2500)
```

### Step 2: Load Previous Events

```
Event Store:
1. OrderCreated
```

### Step 3: Rebuild Aggregate State

```
OrderCreated -> Order status = CREATED
```

### Step 4: Apply Business Rules

```
Can receive payment?
- Order exists: yes
- Already paid: no
- Cancelled: no

Result: allowed
```

### Step 5: Append New Event

```
PaymentReceived(orderId=101, amount=2500)
```

### Step 6: Update Read Model

```
Order Summary:
orderId = 101
status = PAID
paid = true
```

## Complete Flow Example

```
1. Customer creates order
   Command: CreateOrder
   Event:   OrderCreated

2. Customer pays
   Command: ReceivePayment
   Event:   PaymentReceived

3. Warehouse packs order
   Command: PackOrder
   Event:   OrderPacked

4. Delivery team ships order
   Command: ShipOrder
   Event:   OrderShipped
```

Final state after replay:

```
Order ID: 101
Status: SHIPPED
Paid: true
Packed: true
Amount: Rs. 2500
```

## Java Example

### Event Interface

```java
public interface OrderEvent {
    String orderId();
}
```

### Events

```java
public class OrderCreated implements OrderEvent {
    private final String orderId;
    private final double amount;

    public OrderCreated(String orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public String orderId() {
        return orderId;
    }

    public double amount() {
        return amount;
    }
}
```

```java
public class PaymentReceived implements OrderEvent {
    private final String orderId;
    private final double amount;

    public PaymentReceived(String orderId, double amount) {
        this.orderId = orderId;
        this.amount = amount;
    }

    public String orderId() {
        return orderId;
    }

    public double amount() {
        return amount;
    }
}
```

```java
public class OrderShipped implements OrderEvent {
    private final String orderId;

    public OrderShipped(String orderId) {
        this.orderId = orderId;
    }

    public String orderId() {
        return orderId;
    }
}
```

### Aggregate

```java
import java.util.ArrayList;
import java.util.List;

public class OrderAggregate {
    private String orderId;
    private double amount;
    private boolean created;
    private boolean paid;
    private boolean shipped;
    private final List<OrderEvent> newEvents = new ArrayList<>();

    public void createOrder(String orderId, double amount) {
        if (created) {
            throw new IllegalStateException("Order already exists");
        }

        applyNewEvent(new OrderCreated(orderId, amount));
    }

    public void receivePayment(double amount) {
        if (!created) {
            throw new IllegalStateException("Order does not exist");
        }
        if (paid) {
            throw new IllegalStateException("Order already paid");
        }
        if (this.amount != amount) {
            throw new IllegalArgumentException("Payment amount mismatch");
        }

        applyNewEvent(new PaymentReceived(orderId, amount));
    }

    public void shipOrder() {
        if (!paid) {
            throw new IllegalStateException("Cannot ship unpaid order");
        }
        if (shipped) {
            throw new IllegalStateException("Order already shipped");
        }

        applyNewEvent(new OrderShipped(orderId));
    }

    public void replay(List<OrderEvent> history) {
        for (OrderEvent event : history) {
            apply(event);
        }
    }

    public List<OrderEvent> getNewEvents() {
        return newEvents;
    }

    private void applyNewEvent(OrderEvent event) {
        apply(event);
        newEvents.add(event);
    }

    private void apply(OrderEvent event) {
        if (event instanceof OrderCreated) {
            OrderCreated orderCreated = (OrderCreated) event;
            this.orderId = orderCreated.orderId();
            this.amount = orderCreated.amount();
            this.created = true;
        } else if (event instanceof PaymentReceived) {
            this.paid = true;
        } else if (event instanceof OrderShipped) {
            this.shipped = true;
        }
    }
}
```

### Simple Event Store

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryEventStore {
    private final Map<String, List<OrderEvent>> streams = new HashMap<>();

    public List<OrderEvent> load(String aggregateId) {
        return new ArrayList<>(streams.getOrDefault(aggregateId, new ArrayList<>()));
    }

    public void append(String aggregateId, List<OrderEvent> events) {
        streams.computeIfAbsent(aggregateId, key -> new ArrayList<>()).addAll(events);
    }
}
```

### Command Handler

```java
public class OrderCommandHandler {
    private final InMemoryEventStore eventStore;

    public OrderCommandHandler(InMemoryEventStore eventStore) {
        this.eventStore = eventStore;
    }

    public void payOrder(String orderId, double amount) {
        OrderAggregate order = new OrderAggregate();
        order.replay(eventStore.load(orderId));

        order.receivePayment(amount);

        eventStore.append(orderId, order.getNewEvents());
    }
}
```

## Event Replay

Event replay means reading old events and applying them again.

Uses:
- Rebuild current state
- Recreate read models
- Debug production issues
- Create new projections
- Run historical analytics

Example:

```
Replay all Order events
        |
        v
Build new OrderAnalyticsView
        |
        v
No change needed in original write system
```

## Projections and Read Models

Event Sourcing is often paired with CQRS.

```
Write Side:
Commands -> Aggregates -> Event Store

Read Side:
Events -> Projections -> Query Database
```

Example projection:

```java
public class OrderSummaryProjection {
    public void handle(OrderEvent event) {
        if (event instanceof OrderCreated) {
            // Insert order summary row
        } else if (event instanceof PaymentReceived) {
            // Mark order as paid
        } else if (event instanceof OrderShipped) {
            // Mark order as shipped
        }
    }
}
```

Read models can be stored in:
- PostgreSQL
- MongoDB
- Elasticsearch
- Redis
- Cassandra
- Data warehouse

## Event Versioning

Events are permanent, so schemas must evolve carefully.

### Problem

Old event:

```json
{
  "eventType": "OrderCreated",
  "customerName": "Amit"
}
```

New event:

```json
{
  "eventType": "OrderCreated",
  "firstName": "Amit",
  "lastName": "Sharma"
}
```

The system must still understand old events.

### Common Solutions

- Add new optional fields instead of removing old fields
- Use event version numbers
- Transform old events during read
- Write upcasters to convert old event formats
- Avoid storing unnecessary UI-specific fields in domain events

## Concurrency Control

Event stores usually use optimistic concurrency.

Example:

```
Service A loads order-101 at version 3
Service B loads order-101 at version 3

Service A appends event with expected version 3 -> success, version becomes 4
Service B appends event with expected version 3 -> rejected
```

This prevents conflicting updates.

## Snapshotting

Without snapshots:

```
Load 50,000 events
Replay all events
Handle command
```

With snapshots:

```
Load snapshot at version 49,000
Replay events 49,001 to 50,000
Handle command
```

Use snapshots when:
- Aggregates have long event histories
- Replay becomes slow
- Startup time becomes expensive
- Large projections need periodic checkpoints

## Benefits

- **Complete audit trail**: Every state change is recorded.
- **Time travel debugging**: Rebuild state at any point in time.
- **Reliable history**: Events are domain facts, not separate audit logs.
- **Rebuild read models**: New projections can be created from old events.
- **Strong business traceability**: Helpful for finance, orders, inventory, and compliance.
- **Natural integration with CQRS**: Events feed query models.
- **Event-driven integration**: Other services can react to domain events.

## Drawbacks

- **More complexity**: Harder than simple CRUD.
- **Event schema evolution**: Old events must remain readable.
- **Eventual consistency**: Read models may lag behind writes.
- **Storage growth**: Events keep accumulating.
- **Replay cost**: Large streams need snapshots and optimization.
- **Testing discipline**: Business rules must be tested through event history.
- **Operational overhead**: Requires event store, projections, retries, and monitoring.

## When to Use Event Sourcing

Use it when:
- Audit history is very important
- You need to know exactly how state changed
- Business workflows are event-heavy
- You need temporal queries
- You want to rebuild read models
- You are already using CQRS
- You need strong traceability for compliance
- Domain events are meaningful to the business

Good domains:
- Banking
- Payments
- Trading
- Order management
- Inventory
- Insurance claims
- Booking systems
- Workflow systems

## When Not to Use Event Sourcing

Avoid it when:
- Simple CRUD is enough
- The domain has little need for history
- Team is not ready for the complexity
- Reporting can be solved with normal audit tables
- Strong immediate read consistency is required everywhere
- Event schema evolution cannot be managed properly

## Event Sourcing vs Event-Driven Architecture

| Feature | Event Sourcing | Event-Driven Architecture |
|---|---|---|
| Main purpose | Store state as events | Communicate between services using events |
| Source of truth | Event store | Usually service database |
| Events represent | State changes | Notifications or integration messages |
| Replay required | Yes, core feature | Usually optional |
| Current state | Rebuilt from events | Stored normally |
| Common pairing | CQRS | Pub/Sub, message brokers |

Event Sourcing can publish events to an Event-Driven Architecture, but they are not the same pattern.

## Event Sourcing vs Audit Log

| Feature | Event Sourcing | Audit Log |
|---|---|---|
| Source of truth | Events | Current database state |
| Used to rebuild state | Yes | Usually no |
| Business behavior | Driven by events | Separate from main logic |
| Completeness | Required | Often best effort |
| Data model | Domain events | Log records |

## Best Practices

1. Store domain events, not technical database changes.
2. Use past-tense event names such as `OrderCreated`.
3. Keep events immutable.
4. Include event version and timestamp.
5. Use optimistic concurrency checks.
6. Keep aggregate streams focused and not too large.
7. Build projections idempotently.
8. Plan event schema evolution from the beginning.
9. Use snapshots only when replay performance requires them.
10. Monitor projection lag and failed event handlers.
11. Do not expose internal event schemas directly to external clients.
12. Keep commands and events separate.

## Common Mistakes

- Treating events like CRUD table rows
- Updating old events
- Using vague event names like `OrderUpdated`
- Putting too much data in every event
- Putting too little data in events
- Ignoring event versioning
- Making projections non-repeatable
- Expecting read models to be instantly consistent
- Using Event Sourcing for every service by default

## Interview Explanation

Event Sourcing is a pattern where the system stores every state change as an immutable event instead of storing only the latest state. The current state of an entity is rebuilt by replaying its events. This gives a complete audit trail, supports time travel debugging, and allows read models to be rebuilt. It works well with CQRS, but it adds complexity around event versioning, projections, snapshots, replay, and eventual consistency.

## Summary

Event Sourcing changes the source of truth from "current database row" to "ordered history of events." It is powerful for systems that need auditability, traceability, replay, and rich domain history. It should be used carefully because it introduces more complexity than normal CRUD, especially around event schema evolution and projection management.
