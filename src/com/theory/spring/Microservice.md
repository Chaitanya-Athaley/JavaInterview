What design patterns are commonly used in microservices?
Answer: 
Common design patterns in microservices are API Gateway, Service Discovery, Circuit Breaker, Saga, Database per Service, Event-Driven Architecture, CQRS, Bulkhead, Retry, and Strangler Pattern.

How does Spring Boot simplify microservice development?
Answer:
| Problem               | Spring Boot Solution  |
| --------------------- | --------------------- |
| Boilerplate config    | Auto-configuration    |
| Deployment complexity | Embedded server       |
| Security setup        | Spring Security       |
| Service communication | Spring Cloud          |
| Monitoring            | Actuator + Micrometer |
| Fault tolerance       | Resilience4j          |
Production Checklist
✅ Use Spring Boot 3.x (Jakarta EE 10)
✅ Enable Actuator endpoints
✅ Secure APIs with OAuth2/JWT
✅ Use WebClient (avoid RestTemplate)
✅ Configure Resilience4j
✅ Externalize configs (Vault/K8s)
✅ Add OpenAPI (Swagger)
✅ Use Flyway for DB migrations
✅ Use Docker for deployment.

Q:What is the 12-Factor Principle?
Answer:
✔ Same code, different configs
✔ No reliance on system-installed libraries
✔ Config (Externalized), No hardcoding ⭐
✔ Replaceable without code change
✔ Build, Release, Run (Strict Separation)
✔ Use Redis / DB instead
✔ No external web server required (Spring Boot embedded)
✔ Concurrency (Scale Horizontally)
✔ Disposability (Fast Startup & Shutdown)
✔ Dev/Prod Parity
✔ Logs (Treat as Event Streams)
✔ Admin Processes (Run as One-Off Tasks)
==========================================================

Q:CQRS, CSRF, CORS ?
Answer:
CQRS stands for Command Query Responsibility Segregation
It means separating write operations and read operations
Command is used for create, update, delete
Query is used for fetch/read data

CSRF stands for Cross-Site Request Forgery
It is a security attack
In this attack, a malicious website tricks a logged-in user’s browser into sending unwanted requests to another trusted website

CORS stands for Cross-Origin Resource Sharing
It is a security feature,It controls whether a frontend from one origin can access resources from another origin
==========================================================

Q: How do you secure microservices (OAuth2, JWT, API Gateway)?
Answer:I secure microservices by using OAuth2 for authorization, JWT for stateless token-based authentication, and an API Gateway as the central entry point. The gateway validates tokens and enforces security policies, while microservices use the token claims for authorization. I also use HTTPS, role-based access control, and secure service-to-service communication.
User logs in through an Auth Server
Auth Server validates user and issues JWT token
Client sends token with each request
API Gateway validates the token
Gateway forwards request to the required microservice
Microservices can also validate the token or trust the gateway depending on design

OAuth2 handles authorization
JWT carries user identity securely
API Gateway centralizes authentication and security enforcement

==========================================================
Q: what is OAuth2 flow?
Answer: 
In OAuth2, the client redirects the user to the authorization server, where the user logs in and gives consent. The authorization server returns an authorization code, which the client exchanges for an access token. The client then uses that access token to call the resource server securely without knowing the user’s password.
==========================================================

how Refresh Token work ?
Answer:
Refresh token is used to obtain a new access token after the old one expires, without forcing the user to log in again. The client sends the refresh token to the authorization server, and if it is valid, the server issues a new access token.
 JWT is partial stateless : this is an important point: JWT access token is usually stateless, but the full OAuth2 system is not always completely stateless.

A JWT access token can be validated by checking:
signature
expiry
issuer
claims
JWT access token = mostly stateless
Refresh token flow = usually stateful
OAuth2 with JWT = can be partly stateless, not fully stateless in all parts
==========================
Q: how to implement idempotency in spring boot ?
Answer:
In Spring Boot, I make APIs idempotent by using an idempotency key. The client sends a unique key with the request, and the server stores that key along with the result. If the same request comes again with the same key, the server returns the previous response instead of processing it again. This is especially useful for payments and order creation APIs
Use unique key from client
Store response safely
Add expiry for old keys if needed
Handle concurrent requests properly
Use DB unique constraint or distributed lock to avoid duplicates

@PostMapping("/payments")
public ResponseEntity<String> createPayment(
        @RequestHeader("Idempotency-Key") String key,
        @RequestBody PaymentRequest request) {

    Optional<String> existingResponse = idempotencyService.getSavedResponse(key);
    if (existingResponse.isPresent()) {
        return ResponseEntity.ok(existingResponse.get());
    }

    String response = paymentService.processPayment(request);
    idempotencyService.saveResponse(key, response);

    return ResponseEntity.ok(response);
}

==========================================================
Q: which format rest service can accept ?
Answer: 
JSON most common
XML
Plain text
HTML
Form data
Multipart/form-data for file upload
Content-Type: application/xml
Accept: application/xml
==========================================================

