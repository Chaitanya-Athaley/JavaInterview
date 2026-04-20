I worked at Carlone, a healthcare platform that provides cashless insurance claim processing. One of the key modules I contributed to was the referral patient processing system.

The problem we were solving was handling patient referrals coming from multiple business units (BUs). These referrals had inconsistent formats and required validation and enrichment before they could be processed downstream for claim initiation.

We built this as a Spring Boot-based microservice in an event-driven architecture. Incoming referral requests were received via REST APIs, validated using a combination of schema validation and business rules, and then persisted in a PostgreSQL database.

My primary responsibility was designing and implementing the referral processing pipeline. This included:

* Validating incoming requests for data integrity and business constraints
* Enriching the DTO with additional metadata required for downstream systems
* Ensuring idempotent processing to avoid duplicate referrals
* Publishing processed events to Kafka for asynchronous downstream consumption

One of the key challenges was handling duplicate and inconsistent referral data coming from multiple sources. To solve this, I implemented idempotency using unique request identifiers combined with database constraints, ensuring the system could safely handle retries without creating duplicate records.

We also optimized the flow by introducing asynchronous processing via Kafka, which decoupled upstream and downstream systems and improved overall system throughput.

As a result, the system was able to reliably process high volumes of referral requests with improved data consistency and reduced processing latency.


Q: Explain Microservices Architecture with your project example?
Answer:
In my project at Carlone, we followed a microservices architecture to handle
cashless insurance claims. The system was divided into multiple services such as:

* Referral Service (my module)
* Claim Processing Service
* Patient Service
* Notification Service

Each service had its own database to ensure data isolation and independent scalability.

The Referral Service was responsible for receiving patient referrals from multiple
business units, validating the requests, enriching the data, and publishing events
to Kafka for downstream processing.

We used REST APIs for synchronous communication and Kafka for asynchronous
event-driven communication between services.

This architecture allowed us to scale individual services independently,
improve fault isolation, and deploy features faster without impacting the entire system.
===============

please create image for all steps like, code push to github repo, then githubActions will start pipeline steps(maven build, docker build, docker push to ECR), then EKS worker node will fetch image from ECR and kubelet will run on EC2 , doing rolling updates using scheduler.