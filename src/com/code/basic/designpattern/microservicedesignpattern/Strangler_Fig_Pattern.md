# Strangler Fig Pattern - Complete Guide

## 🌳 What is the Strangler Fig Pattern?

**In Simple Words:**
The Strangler Fig Pattern is a migration strategy where you gradually replace a legacy system by building new services alongside it, routing traffic to new services piece-by-piece, until the old system can be decommissioned entirely.

Think of it like:
- **Fig Tree & Oak Tree**: A strangler fig vine grows around an oak tree, eventually taking over its function while the old tree dies naturally inside.
- **Renovating a House**: You renovate one room at a time while living in the house, rather than demolishing everything and starting over.
- **Employee Transition**: New colleague learns the job gradually while old employee mentors, eventually taking over all responsibilities.

## 📜 The Origin

This pattern is named after strangler fig vines, which wrap themselves around host trees and slowly take over, making the host tree expendable.

## 🏗️ Architecture Overview

### Before Migration
```
Legacy System
│
├─ Old Database Technology
├─ Outdated Frameworks
├─ Tightly Coupled Code
├─ Hard to Maintain
└─ Difficult to Scale
```

### During Migration (Strangler Fig)
```
┌─────────────────────────────────────────┐
│           API Gateway/Router            │
│    (Smart routing: old vs new)          │
└─────────────────────────────────────────┘
        ↓                              ↓
    ┌────────┐                  ┌──────────────┐
    │ Legacy │                  │  New System  │
    │ System │                  │  (Microsvcs) │
    │        │                  │              │
    │ 70%    │                  │ 30%          │
    │Traffic │                  │Traffic       │
    └────────┘                  └──────────────┘
        ↓                              ↓
    Old DB                          New DB
```

### After Migration
```
┌─────────────────────────────────────────┐
│           API Gateway/Router            │
│    (All traffic to new system)          │
└─────────────────────────────────────────┘
        ↓
    ┌──────────────┐
    │  New System  │
    │              │
    │ 100%         │
    │Traffic       │
    └──────────────┘
        ↓
    New DB

Legacy System: DECOMMISSIONED
```

## 🔑 Core Concepts

### 1. **Facade/API Gateway**
Acts as a router between old and new systems
```
Responsibilities:
- Route requests to appropriate backend
- Handle versioning
- Adapt responses if needed
- Gather metrics
- Enable feature flags
```

### 2. **Edge Cases**
Identify which features are critical vs nice-to-have
```
Examples:
- Core business logic (critical) → migrate first
- Rarely used features (nice-to-have) → migrate last
- Complex edge cases → keep in old system longer
```

### 3. **Rollback Capability**
Always be able to route traffic back to old system
```
Safety mechanism:
- If new system fails → route back to old
- No data corruption risk
- Can test incrementally
- Confidence building
```

### 4. **Parallel Run**
Both systems run simultaneously during transition
```
Benefits:
- Compare results
- Verify correctness
- Detect edge cases
- A/B test with real users
```

### 5. **Incremental Migration**
Move functionality gradually, not all at once
```
Phases:
- Phase 1: 10% traffic → new system
- Phase 2: 25% traffic → new system
- Phase 3: 50% traffic → new system
- Phase 4: 100% traffic → new system
- Phase 5: Decommission old system
```

## 💡 How Strangler Fig Pattern Works

### Step-by-Step Process

#### Step 1: Identify Boundaries
```
Analyze legacy system:
├─ Core domains
├─ Business capabilities
├─ Data boundaries
├─ External dependencies
└─ Usage patterns

Example (E-commerce):
├─ Product Catalog
├─ Shopping Cart
├─ Ordering
├─ Payment
├─ Shipping
└─ Notifications
```

#### Step 2: Prioritize Replacement Order
```
Decision Matrix:
┌─────────────┬──────────┬──────────────┐
│ Function    │ Urgency  │ Complexity   │
├─────────────┼──────────┼──────────────┤
│ Product     │ Medium   │ Low          │ ← Start here
│ Cart        │ High     │ Medium       │
│ Ordering    │ Critical │ High         │
│ Payment     │ Critical │ Very High    │
│ Shipping    │ Medium   │ Medium       │
│ Notif       │ Low      │ Low          │ ← End here
└─────────────┴──────────┴──────────────┘

Strategy:
- Start with low-risk, high-value services
- Build momentum
- Handle complex services later
```

#### Step 3: Build Facade Layer
```
API Gateway Implementation:
┌──────────────────────────┐
│    Request Router        │
├──────────────────────────┤
│ if feature_X == "old"    │
│   route to LegacyService │
│ else                     │
│   route to NewService    │
├──────────────────────────┤
│ Feature Flags:           │
│ - productCatalog=new     │
│ - shopping=old           │
│ - orders=new             │
└──────────────────────────┘
```

#### Step 4: Migrate First Microservice
```
Example: Migrate Product Catalog

1. Build new Product Service
   - New database
   - Modern tech stack
   - API endpoints

2. Set feature flag
   - productCatalog = "new"
   - Routes 1% traffic

3. Monitor
   - Compare responses
   - Track errors
   - Performance metrics

4. Redirect 1% of traffic to new service
   - Test with real users
   - Detect issues early
   - Minimal risk
```

#### Step 5: Gradually Increase Traffic
```
Week 1: 1% → New Service
        99% → Old Service

Week 2: 5% → New Service
        95% → Old Service

Week 3: 25% → New Service
        75% → Old Service

Week 4: 50% → New Service
        50% → Old Service (canary)

Week 5: 75% → New Service
        25% → Old Service

Week 6: 95% → New Service
        5% → Old Service

Week 7: 100% → New Service
        0% → Old Service

If issues detected:
→ Immediately shift traffic back
→ Rollback is instant
```

#### Step 6: Switch Off Old Service
```
Once 100% traffic on new service for period:
✓ Monitor for issues (2-4 weeks)
✓ Verify all functionality working
✓ Confirm no regressions
✓ Archive old data (legal requirement)
✓ Document old system
✓ Decommission old infrastructure
```

## 🎯 Real-World Scenarios

### Scenario 1: E-commerce Platform Migration

**Current State:**
- Monolithic Java application (10 years old)
- Oracle database (schema messy)
- Hard to scale and maintain
- Want to move to microservices + modern tech

**Migration Plan:**
```
Phase 1: Product Catalog Service (Week 1-4)
├─ Build new Product Service (Java Spring Boot)
├─ New PostgreSQL database
├─ API Gateway routes GET /products → new service
├─ Start: 5% traffic
└─ Ramp: 100% by Week 4

Phase 2: User Profile Service (Week 3-6)
├─ Old monolith had user profile
├─ Build dedicated User Service
├─ Start: 5% traffic (Week 3)
└─ Ramp: 100% by Week 6
   (Overlaps with Phase 1 for parallelization)

Phase 3: Order Service (Week 5-10)
├─ Most critical service
├─ Build new Order Service
├─ Handles payment coordination
├─ Start: 2% traffic (Week 5, careful!)
└─ Ramp: 100% by Week 10

Phase 4: Payment Service (Week 8-12)
├─ Integrate with Stripe/PayPal
├─ Complex transaction handling
├─ Start: 5% traffic (Week 8)
└─ Ramp: 100% by Week 12

Phase 5: Notification Service (Week 11-14)
├─ Email/SMS notifications
├─ Lower risk service
├─ Start: 10% traffic (Week 11)
└─ Ramp: 100% by Week 14

Week 15: Decommission Old Monolith
```

### Scenario 2: Financial Services Migration

**Current State:**
- Legacy COBOL system
- 30+ years old
- Critical for business
- Cannot have downtime
- Storing trillions in transactions

**Strangler Approach:**
```
Year 1: Read-Only Services
├─ Account Inquiry Service
├─ Transaction History Service
├─ Balance Lookup Service
└─ Start: 10% reads, rest to legacy

Year 2: Low-Risk Writes
├─ Transfer Between Own Accounts
├─ Recurring Transaction Management
├─ Alert Configuration
└─ Shift: 50% of write operations

Year 3: Complex Business Logic
├─ Loan Processing
├─ Interest Calculations
├─ Fee Management
└─ Parallel execution validates correctness

Year 4: Risky Operations
├─ Fraud Detection Algorithms
├─ Risk Assessment
├─ Regulatory Reporting
└─ Extensive testing and validation

Year 5: Final Cutover
├─ Remaining 5% traffic shifted
├─ Legacy system monitoring
├─ Archive procedures
└─ Decommission legacy
```

### Scenario 3: Social Media Platform Evolution

**Current State:**
- PHP monolith (5 years old)
- MySQL shared database
- Want microservices with Node.js
- Need zero downtime

**Migration:**
```
Month 1-2: Feed Service
├─ Build Node.js Feed Service
├─ Read from new event stream
├─ API Gateway: POST /feed → new
├─ Start: 5% traffic
└─ End: 100% traffic

Month 2-3: User Service
├─ Profile information
├─ Preferences
├─ Parallel with feed migration

Month 3-4: Post Service
├─ Create/edit/delete posts
├─ More complex due to FTS needs

Month 4-5: Comment Service
├─ Build separately from posts

Month 5-6: Notification Service
├─ Event-driven architecture

Month 6: Decommission PHP Monolith
```

## 📊 Strangler vs Big Bang Rewrite

### Big Bang Rewrite
```
Risky approach:
│
└─ Old System Running
└─ New System Being Built (Hidden)
└─ Cut over everything at once
└─ 🔴 HIGH RISK
   ├─ Long development time
   ├─ Easy to miss edge cases
   ├─ All or nothing cutover
   ├─ Immediate rollback catastrophic
   └─ Downtime expected
```

### Strangler Fig
```
Safe approach:
│
├─ Old System: 100% traffic
├─ New Service A: 1% traffic
├─ Gradually increase A: 1% → 100%
├─ New Service B: 1% traffic
├─ Gradually increase B: 1% → 100%
├─ ... repeat for each service
├─ Final: All on new system
└─ 🟢 LOW RISK
   ├─ Early error detection
   ├─ Real user validation
   ├─ Easy rollback anytime
   ├─ Zero downtime expected
   └─ Parallelization possible
```

## ✨ Benefits

### 1. **Low Risk**
- Systems run in parallel
- Early error detection
- Easy rollback at any time
- Minimal blast radius

### 2. **Zero Downtime**
- No cutover window needed
- Gradual migration
- Users unaware of changes
- Continuous availability

### 3. **Real User Validation**
- Test with actual traffic
- Detect edge cases early
- Different from load testing
- Real-world scenarios

### 4. **Parallel Development**
- Multiple teams
- Work on different services
- Don't wait for big bang
- Continuous delivery

### 5. **Rollback Capability**
- If issues detected, roll back
- No data loss risk
- Instant recovery
- Confidence building

### 6. **Reduced Time Pressure**
- No deadline pressure
- Take time to do right
- Thorough testing
- Quality over speed

### 7. **Learning & Validation**
- Proven approach before full cutover
- Training for operations team
- Documentation gathering
- Build institutional knowledge

### 8. **Business Continuity**
- Revenue keeps flowing
- No customer impact
- Competitive advantage
- Trust maintained

## ⚠️ Challenges

### 1. **Data Consistency**
- Old and new system share/duplicate data
- Synchronization complexity
- Potential inconsistencies
- Need validation logic

### 2. **Operational Complexity**
- Running two systems
- Double infrastructure
- More monitoring
- Complicated debugging

### 3. **Testing Difficulty**
- Hard to test all scenarios
- Edge cases hidden
- Parallel system testing complex
- Context switching overhead

### 4. **Performance Overhead**
- Facade/Router layer adds latency
- Data duplication
- Extra infrastructure
- Cost of running both systems

### 5. **Feature Parity**
- Must support same features
- Hidden features in old system
- Edge cases
- Legacy workarounds

### 6. **Skill Requirements**
- Team must understand both systems
- Different tech stacks
- Complex architecture
- Training needed

### 7. **Extended Timeline**
- Slower than big bang
- Months or years
- Commitment needed
- Resource allocation

### 8. **Sunk Cost Fallacy**
- Old system still requires maintenance
- Resources allocated to decommissioning
- Temptation to delay
- Commitment to finish required

## 🔄 Implementation Patterns

### Pattern 1: Feature Flag Router
```
Router implementation:
├─ If flag[feature] == "new"
│  └─ Route to new service
├─ Else
│  └─ Route to old service

Benefits:
- Easy to toggle
- A/B testing capable
- Instant rollback
- Granular control
```

### Pattern 2: Reverse Proxy
```
Nginx/HAProxy in front:
├─ Location /products
│  └─ proxy_pass http://new-service
├─ Location /orders
│  └─ proxy_pass http://old-service
├─ Location /users
│  └─ proxy_pass http://new-service

Benefits:
- Language agnostic
- Traffic splitting
- SSL termination
- Caching possible
```

### Pattern 3: API Adapter
```
Adapter layer:
├─ Receives all requests
├─ Converts old API to new
├─ Calls appropriate backend
├─ Converts response back to old format

Benefits:
- Decouples frontend from backend
- Gradual API evolution
- No client changes needed
```

### Pattern 4: Message Queue Synchronization
```
Dual writes:
├─ Write to old database (for legacy system)
├─ Publish event to queue
├─ New service subscribes
├─ New service updates its database

Benefits:
- Eventual consistency
- Fire and forget writes
- Scale independent
```

### Pattern 5: Change Data Capture (CDC)
```
Database level sync:
├─ Listen to old DB changes
├─ Parse change events
├─ Sync to new database
├─ No application changes

Benefits:
- Automatic sync
- At source of truth
- Minimal app changes
```

## 🎯 When to Use Strangler Fig

### ✅ Use When:

1. **Legacy System Still Working**
   - Not broken, just outdated
   - Stable operations
   - Team knows it well

2. **Cannot Risk Downtime**
   - Critical business operations
   - 24/7 availability required
   - Revenue depends on it

3. **Large System**
   - Monolithic architecture
   - Many interconnected features
   - Difficult to test holistically

4. **Uncertain Future**
   - Don't know exact target architecture
   - Want to experiment
   - Learn as you go

5. **Multiple Teams**
   - Different teams build different services
   - Parallel development needed
   - Avoid coordination overhead

6. **Long-Term System**
   - System will evolve further
   - Incremental improvements
   - Continuous delivery

### ❌ Don't Use When:

- System is broken and unusable
- Can schedule downtime
- Simple system
- Clear target architecture
- Immediate replacement needed
- Team too small

## 📚 Strangler Fig Checklist

### Pre-Migration
- [ ] Identify system boundaries
- [ ] Prioritize migration order
- [ ] Build API Gateway
- [ ] Set up feature flags
- [ ] Plan monitoring
- [ ] Team training
- [ ] Success metrics defined

### During Migration
- [ ] Build new microservice
- [ ] Write integration tests
- [ ] Feature flag created
- [ ] Gradual traffic routing (1% → 5% → 25% → 50% → 100%)
- [ ] Monitor performance
- [ ] Monitor errors
- [ ] Monitor user impact
- [ ] Document findings
- [ ] Rollback procedure tested

### Post-Migration
- [ ] 100% on new service for 2-4 weeks
- [ ] No issues detected
- [ ] Data archived
- [ ] Documentation complete
- [ ] Old system decommissioned
- [ ] Infrastructure cleaned up
- [ ] Team trained
- [ ] Lessons learned documented

## 🏆 Best Practices

### 1. **Start Small**
- Migrate simplest service first
- Build confidence
- Learn process
- Then tackle complex services

### 2. **Invest in Monitoring**
- Track old vs new system
- Compare performance
- Alert on discrepancies
- Real-time visibility

### 3. **Parallel Testing**
- Compare old vs new results
- Validate correctness
- Document edge cases
- Build confidence

### 4. **Maintain Rollback Path**
- Always route traffic back to old
- Test rollback procedure
- Document procedure
- Practice regularly

### 5. **Automate Comparisons**
- Automated test of both systems
- Flag discrepancies
- Quick feedback
- Early issue detection

### 6. **Document Everything**
- API contracts
- Data transformations
- Edge cases
- Configuration

### 7. **Gradual Ramp**
- Don't jump from 0% to 50%
- Gradual: 1%, 5%, 10%, 25%, 50%, 100%
- Catch issues at each step
- Multiple observation periods

### 8. **Team Communication**
- Regular status updates
- Celebrate milestones
- Share learnings
- Celebrate completion

## 🔗 Related Patterns

### Strangler Fig + Microservices
```
Perfect combination
- Each service strangled separately
- Independent timelines
- Parallel development
```

### Strangler Fig + Feature Flags
```
Enables precise control:
- Toggle routing per feature
- A/B test with users
- Instant rollback
```

### Strangler Fig + API Gateway
```
Essential infrastructure:
- Central routing point
- Request/response transformation
- Rate limiting
```

### Strangler Fig + Saga Pattern
```
For distributed transactions:
- Coordinates across old and new
- Event-driven coordination
```

### Strangler Fig + Monitoring
```
Critical for success:
- Detect discrepancies
- Monitor performance
- Alert on issues
```

## 📊 Migration Timeline Template

```
Service: Product Catalog
Target Tech: Spring Boot + PostgreSQL
Start Date: 2024-01-15

Week 1: Development
├─ API design
├─ Database schema
└─ Basic CRUD operations

Week 2: Development
├─ Complex business logic
├─ Validation
└─ Error handling

Week 3: Testing
├─ Unit tests
├─ Integration tests
├─ Performance tests

Week 4: Deployment Prep
├─ Staging deployment
├─ Production readiness
└─ Monitoring setup

Week 5: Gradual Rollout
├─ Day 1: 1% traffic (1,000 requests/day)
├─ Day 2-3: Monitor, no issues
├─ Day 4: 5% traffic (5,000 requests/day)
├─ Day 5-6: Monitor, no issues
├─ Day 7: 10% traffic (10,000 requests/day)
└─ ...

Week 9: Full Cutover
├─ 100% traffic on new service
├─ All features working
└─ Old service ready to deprecate
```

## 🎓 Interview Questions

1. **What is the Strangler Fig Pattern?**
   - Gradual replacement of legacy system
   - Build new services alongside old
   - Route traffic incrementally
   - Eventually decommission old system

2. **Advantages over Big Bang Migration?**
   - Low risk
   - Easy rollback
   - Real user validation
   - Zero downtime
   - Parallel development

3. **Challenges of Strangler Fig?**
   - Data consistency
   - Operational complexity
   - Duplicate infrastructure
   - Extended timeline
   - Testing difficulty

4. **How to handle data consistency?**
   - Dual writes
   - Change Data Capture
   - Message queue synchronization
   - Validation logic

5. **How to trace issues?**
   - Correlation IDs
   - Distributed logging
   - Alerting on discrepancies
   - A/B comparison

6. **When to completely remove old system?**
   - After stable on new system 2-4 weeks
   - No customer-facing issues
   - All data migrated/archived
   - Rollback no longer needed

## 💰 Cost Considerations

### Infrastructure Costs
```
Temporary increase:
├─ Two systems running
├─ Extra database
├─ API Gateway overhead
├─ Monitoring infrastructure

Typical: 40-60% increase during migration
After completion: Normal costs resume
```

### Team Costs
```
Resources needed:
├─ Development team (new service)
├─ Operations (maintain both)
├─ QA (testing both systems)
├─ DevOps (infrastructure)

Timeline: Months to years depending on system size
```

### Mitigation
```
- Use cloud infrastructure (scale easily)
- Automate testing
- Parallel team work
- Modern tech (faster development)
```

## 🌟 Key Principles

### 1. **Incrementalism**
- Small steps
- Early feedback
- Lower risk
- Better quality

### 2. **Observability**
- Monitor everything
- Compare systems
- Alert on issues
- Real-time visibility

### 3. **Reversibility**
- Easy rollback
- No permanent changes
- Safe experimentation
- Confidence building

### 4. **Pragmatism**
- Accept imperfections
- Keep what works
- Replace what doesn't
- Minimize disruption

### 5. **Continuity**
- Business keeps running
- Customers unaware
- Zero downtime
- Trust maintained

## 📋 Success Metrics

```
Technical Metrics:
├─ Error rate reduction
├─ Performance improvement
├─ Latency reduction
├─ Stability increase

Business Metrics:
├─ Zero downtime achieved
├─ Revenue impact (none/positive)
├─ Customer satisfaction maintained
├─ Operational efficiency improved

Team Metrics:
├─ Time to migrate
├─ Resource allocation
├─ Knowledge transfer
├─ Team satisfaction
```

---

## Strangler Fig Pattern Summary Card

| Aspect | Details |
|--------|---------|
| **Type** | Migration Pattern |
| **Purpose** | Gradually replace legacy system |
| **Main Benefit** | Low risk, zero downtime migration |
| **Complexity** | High (requires infrastructure) |
| **Risk Level** | Low |
| **Timeline** | Months to years |
| **Team Size** | Medium to large |
| **Downtime** | Zero |
| **Rollback** | Easy at any point |
| **Common Use** | Legacy system modernization |

---

## 🎓 Key Takeaways

1. **Strangler Fig enables safe migration** without company-wide risk
2. **Gradual rollout** catches issues early before they're catastrophic
3. **Parallel systems** add complexity but provide safety net
4. **Real user testing** is more valuable than load testing
5. **Clear metrics** essential for knowing when to advance
6. **Operator training** must happen during migration, not at cutover
7. **Celebrate milestones** - it's a long journey
8. **Not always faster** than big bang, but much safer

## 🚀 From Planning to Execution

```
Planning Phase (Weeks 1-4)
├─ Analyze legacy system
├─ Design new architecture
├─ Identify migration order
└─ Plan infrastructure

Execution Phase (Weeks 5-N)
├─ Build new services (potentially parallel)
├─ Deploy with 1% traffic
├─ Monitor and validate
├─ Gradually increase traffic
└─ Repeat for each service

Completion Phase (Final weeks)
├─ Ensure 100% on new system
├─ Archive old data
├─ Decommission old infrastructure
└─ Document lessons learned
```

