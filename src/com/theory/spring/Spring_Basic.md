Q1) How does Spring Boot auto-configuration work internally?
Answer: classpath dependencies,@EnableAutoConfiguration, Scans META-INF/spring.factories or AutoConfiguration.imports
Loads list of auto-configuration classes, @ConditionalOnClass → class exists on classpath?
@ConditionalOnMissingBean → user didn't define it?
@ConditionalOnProperty → property set in config?
Controls execution sequence-> @AutoConfigureAfter / @AutoConfigureBefore
Example (JPA)
Add spring-boot-starter-data-jpa
Auto-configures: DataSource, EntityManagerFactory, TransactionManager


Q2) how you will enhance performance of spring boot application. think in a way you have to give answer in interview.
Answer:
To improve the performance of a Spring Boot application, I would follow a systematic approach instead of jumping directly into changes. First, I would identify the bottleneck using monitoring and profiling tools such as Spring Boot Actuator, Micrometer, APM tools, JVM monitoring, and database query analysis. Performance tuning should always be data-driven.

The first area I usually focus on is the database, because in most enterprise applications that is the main bottleneck. I would check for slow queries, missing indexes, unnecessary joins, and N+1 query problems. If I’m using Spring Data JPA or Hibernate, I would review lazy loading versus eager loading, inspect the SQL being generated, and make sure we are fetching only the required data instead of entire entities. I would also use pagination for large result sets rather than loading everything into memory.

The second area is caching. If the application repeatedly reads the same data, I would introduce caching using Spring Cache with tools like Redis or Caffeine. This can significantly reduce database load and improve response time. Of course, I would also think about cache eviction strategy and data consistency so that performance improvement does not create stale data issues.

Next, I would optimize the application layer. I would reduce unnecessary object creation, avoid duplicate processing, and review the business logic for expensive operations. If the application is spending time waiting on I/O, such as external APIs, file systems, or remote services, I would consider asynchronous processing using @Async, message queues like Kafka or RabbitMQ, or even reactive programming if the use case truly benefits from it.

Another important area is thread pool and connection pool tuning. In Spring Boot, I would tune the embedded server thread pool, async executor pool, and especially the database connection pool such as HikariCP. If these are too small, requests wait; if too large, the system may waste resources or create contention. So I would tune them based on actual traffic, CPU cores, and database capacity.

I would also optimize API design. For example, I would send only the required fields through DTOs, enable compression if needed, and use pagination, filtering, or batching for large responses. Smaller payloads mean lower network cost and faster serialization and deserialization.

From an infrastructure and JVM perspective, I would review heap usage, garbage collection behavior, and startup overhead. If needed, I would tune JVM parameters, remove unused dependencies, and exclude unnecessary Spring Boot auto-configurations.

Finally, if the application still needs more capacity, I would focus on scalability. I would keep the service stateless as much as possible, externalize session or cache storage if needed, and scale horizontally using multiple instances behind a load balancer.

So overall, my approach is: measure first, identify the real bottleneck, optimize database and caching first, then tune threads, memory, and architecture. That gives both immediate performance gains and long-term scalability.”

Spring Boot App  → Actuator → Micrometer  →  Prometheus  →  Grafana

Q3: Diference betwee @restcontrolleradvice and @controlleradvice
Answer: @ControllerAdvice -> for MVC, may need @ResponseBody
@RestControllerAdvice -> for REST APIs, @ResponseBody already included

Q4: How Transactional propagation and isolation
Answer: Propagation decides how transactions interact with existing transactions, while isolation decides how safely concurrent transactions can read and write data.

@Transactional controls transaction behavior in Spring. Two important parts are propagation and isolation.

Propagation defines how a method should behave if another transaction already exists.

REQUIRED: join existing transaction, or create a new one if none exists
REQUIRES_NEW: always create a new transaction, suspending the current one
SUPPORTS: use existing transaction if available, otherwise run without one
MANDATORY: must run inside an existing transaction
NOT_SUPPORTED: run without transaction, suspending current one
NEVER: should not run inside a transaction
NESTED: run inside nested transaction with savepoint support
Isolation defines how one transaction is separated from another transaction when multiple transactions run at the same time.

READ_UNCOMMITTED: allows dirty reads
READ_COMMITTED: prevents dirty reads
REPEATABLE_READ: prevents dirty and non-repeatable reads
SERIALIZABLE: highest isolation, prevents phantom reads too, but slower

Q: How to handle versioning in microservice ?
Answer: In microservices, versioning is mainly used to handle API changes without breaking existing clients. The goal is backward compatibility and smooth migration.

The most common way is API versioning, such as:

URI versioning: /api/v1/orders
Header versioning: custom header like API-Version: 1
Content negotiation: version in Accept header

Q: How to handle scaling and load balancing in microservice
Answer: Make microservices stateless so any instance can handle any request.
Use horizontal scaling, which means adding more service instances when traffic increases.
Use a load balancer to distribute requests among all available instances.
In cloud or Kubernetes, use auto-scaling based on CPU, memory, or request count.

Q: What are the best prectice for database design for microservice 
Answer:Use caching if the same data is read often.
Maintain data consistency using patterns like event-driven communication, Saga, or eventual consistency.
Include audit fields like created date, updated date, and version if needed.

Q:explain eventual consistency and event-driven communication ?
Answer: 
Event-driven communication means one microservice does something and then sends an event to tell other services that something happened.
Other services listen to that event and update their own data.
They do not call the same database directly.
Eventual consistency means all services may not have the updated data at the exact same moment, but after some time, they will become consistent.
Imagine you order a product on an online shopping app.

Order Service creates your order.

After creating the order, it publishes an event like: OrderPlaced.

Payment Service listens and processes payment.

Inventory Service listens and reduces stock.

Notification Service listens and sends confirmation message.

All these services do not update at the exact same second.

First order may be created, then payment happens, then stock updates, then email is sent.

For a short time, one system may show updated order while another is still processing.

After a little time, all systems become correct and aligned.

That is eventual consistency.

Simple daily life analogy:

Think of a school announcement.

Principal announces, “Tomorrow is a holiday.”

Class teacher hears it and tells students.

Transport team hears it and cancels buses.

Canteen hears it and stops food preparation.

Everyone does not act at the exact same second.

But after some time, the whole school system reflects the same decision.

That is how event-driven communication and eventual consistency work together.

Short interview answer:

Event-driven communication means services communicate by publishing and consuming events.
Eventual consistency means data may be temporarily different across services, but after some time all services become consistent.

Q: if one microservice experincing high latency due to slow downstream dependencies. how to fix it.
Answer: First, identify where the delay is happening.
Check whether latency is from database, external API, another microservice, or network.
Use logs, tracing, and monitoring tools to find the real bottleneck.
Then I would use timeout, circuit breaker, retry, fallback, caching, and async processing to reduce impact and improve resilience, Tune thread pool and connection pool so blocked calls do not exhaust all resources.,If traffic is high, scale the dependent service horizontally.

 Q: Eventual Consistancy vs strong consistancy
Answer: Strong consistency means all reads get the latest updated data immediately.
Eventual consistency means data may be temporarily different across services, but after some time all copies become consistent.

Q: how to handle if multiple microservices try to access shared common DataBase ?
Answer: If multiple microservices still need common data, handle it like this:
Keep one microservice as the owner of that data.
Other microservices should access that data through API calls or events.
Do not allow all services to directly read/write the same tables.

Q: how to rollback microservice after deployment ? and how to confirm system stability and testing 
Answer:
Deploy each microservice with a clear version like v1.2.0, v1.2.1
If a problem happens after deployment, switch back to the previous stable version
In Kubernetes, use deployment rollback
In Docker/VM, redeploy the previous stable image or artifact
Prefer blue-green deployment or canary deployment so rollback is faster and safer
Keep database changes backward compatible, because database rollback is harder than code rollback
confirm system stability after deployment or rollback:
Check health check endpoints
Monitor CPU, memory, response time, error rate, and throughput
Verify logs for exceptions and failures
Verify distributed tracing for service-to-service flow
Check alerts in monitoring tools like Datadog, Prometheus, Grafana
Run smoke tests on critical APIs
Run regression tests for major business flows
Validate downstream dependency behavior like DB, cache, message queue, and external APIs
====================================================

Q: @Component vs @Bean
Answer:@Component leverages classpath scanning for implicit bean registration, whereas @Bean provides explicit, fine-grained control over object creation—particularly essential for third-party integrations and conditional wiring.”
====================================================
Q: @Component vs @Configuration
Answer: @Component is a generic stereotype annotation used to register a class as a Spring bean, while @Configuration is a specialized annotation used for configuration classes that define beans using @Bean methods. @Configuration provides additional proxy behavior to manage bean lifecycle correctly
CGLIB stands for Code Generation Library. It is used to create objects dynamically at runtime by generating subclass proxies. used in AOP, @Transactional, @Configuration. so singleton behavior is preserved
====================================================



