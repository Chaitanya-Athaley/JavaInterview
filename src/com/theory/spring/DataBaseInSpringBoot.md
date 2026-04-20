Q: call stored prodedure and  view in spring boot application
Answer:
1. Calling stored procedure
Common ways:
using @Procedure
using EntityManager
using JdbcTemplate

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Procedure(procedureName = "get_employee_count")
    Integer getEmployeeCount();
}

2. Calling database view
A view is treated like a normal table for read operations.
We usually map it as an entity.
@Entity
@Table(name = "employee_summary")
public class EmployeeSummary {}

@Repository
public interface EmployeeSummaryRepository extends JpaRepository<EmployeeSummary, Long> {
}
================================================================

Q: What is a connection pool in the context of database management, and what are the advantages of using it? 
Answer: A connection pool is a pool of reusable database connections maintained by the application. Instead of creating a new connection for each request, the pool provides an existing one, improving performance and reducing resource usage. eg HikariCP
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=10
spring.datasource.hikari.idle-timeout=600000
spring.datasource.hikari.max-lifetime=1800000
spring.datasource.hikari.connection-timeout=30000
spring.datasource.hikari.validation-timeout=5000
spring.datasource.hikari.auto-commit=true

=============================================================
Q: How does implement a connection pool class as a singleton, and why is it important to provide a get connection method?
A connection pool is implemented as a singleton so only one shared pool exists in the application. This avoids creating multiple pools and wasting database resources. The getConnection() method is important because it provides controlled and reusable access to database connections from the pool, which improves performance.
if you are using HikariCP, you usually do not need to create your own custom connection pool, HikariCP already provides connection pooling for you.

=========================================================
Q: what is Embeddable composite key @Embeddable ?
Answer:
@Embeddable is used in JPA to define a composite key class.

A composite key means the primary key is made of more than one column.

Instead of using a single id, we combine multiple fields to uniquely identify a record.

How it works

Create a separate key class
Mark it with @Embeddable
Put key fields inside that class
Use that class in entity with @EmbeddedId
Example

@Embeddable
public class EmployeeId implements Serializable {
    private Long companyId;
    private Long employeeId;

    // getters, setters, equals, hashCode
}
@Entity
public class Employee {

    @EmbeddedId
    private EmployeeId id;

    private String name;
}
Why use it

When one column is not enough to uniquely identify data
Useful in join tables or business keys
Helps represent composite primary key cleanly
Important points

@Embeddable class should implement Serializable
It should override equals() and hashCode()
It is used with @EmbeddedId
Interview answer

“@Embeddable is used in JPA to define a composite key class, where the primary key consists of multiple fields. That embeddable class is then used in the entity with @EmbeddedId.”
===========================================================

Q: Which design pattern will you use to inject dependency without using spring.
Answer: Constructor injection
Use constructor-based dependency injection manually.
If needed, use factory pattern to manage object creation.
=========================================================

Q: explain Transaction boundaries, propagation levels, common pitfalls
Answer:
Transaction boundary defines the scope within which multiple operations are executed as one unit of work. In Spring, it is usually managed using @Transactional. Propagation defines how a method behaves when another transaction already exists, such as REQUIRED or REQUIRES_NEW. Common pitfalls include self-invocation, rollback not happening for checked exceptions, long-running transactions, and wrong propagation settings
  
=========================================================
Q: wahy Spring proxy-based transaction may not work properly on private methods ?
Answer: 
Spring transaction management is proxy-based, and proxies cannot intercept private methods because private methods cannot be overridden. That is why @Transactional on a private method usually does not work.”
=========================================================