# ADR 001: Architecture Decision

## Status
**Status:** Accepted
**Date:** 2026-02-12
**Decision Makers:** Madeha Mohammed 

## Context
The project is a cloud-deployed, backend-focused invoice processing system developed using the Java 11+ ecosystem.
It extracts structured data from physical retail invoices (clothing and shoes) using OCR and stores the data in a relational database.
The application supports multiple users with secure authentication, ensuring each user can store and access only their own invoices.
The architecture must ensure maintainability, extensibility, scalability, and alignment with professional software engineering standards.

### Current Situation

* The project is developed as a final academic backend system
* The system processes invoice images using OCR and stores structured data in a relational database
* The application supports multi-user authentication with user-specific invoice ownership
* The system is deployed in a cloud environment and designed to meet professional backend standards

### Driving Forces
- Maintainability through clear separation of concerns
- Secure multi-user data isolation
- Alignment with professional backend development standards

## Decision
The application is implemented using a layered architecture that separates REST controllers, business logic, domain models,
and persistence to support scalability and clean design principles.

### Chosen Solution
The system is structured into four logical layers: Presentation (REST controllers), Application/Service (business logic), Domain (core entities and rules), and Infrastructure (database access, LLM integration, and security configuration).

Instead of traditional OCR, the application integrates a Large Language Model (LLM) to extract structured invoice data from uploaded images. Each layer has a clearly defined responsibility and communicates only with adjacent layers to ensure loose coupling, maintainability, and scalability.

## Rationale
The decision supports clean design principles, long-term maintainability, and secure, scalable backend development.

### Benefits
- Improved maintainability through clear separation of concerns
- Enhanced scalability and extensibility for future feature expansion
- Secure and structured multi-user data isolation

### Drawbacks
- Increased initial architectural complexity compared to a simple monolithic design
- Higher development effort due to strict layer separation and modular structure

## Alternatives Considered

### Alternative 1: Simple Monolithic Architecture
**Description:** A simplified architecture where controllers directly interact with business logic and persistence components without strict layer separation.

**Pros:**
- Faster initial development
- Lower structural complexity

**Cons:**
- Poor maintainability as the system grows
- Tight coupling between components, reducing scalability

**Reason for Rejection:** The approach does not provide sufficient modularity and scalability for a professional, multi-user backend system.

### Alternative 2: Microservices Architecture

**Description:**  
A distributed architecture where invoice processing, user management, and LLM integration are implemented as independent services communicating over APIs.

**Pros:**
- High scalability and independent service deployment

**Cons:**
- Increased infrastructure and operational complexity

**Reason for Rejection:**  
Considered over-engineered for the academic scope and project timeline.


## Consequences

### Positive Consequences
- Clear architectural structure aligned with industry best practices
- Easier future enhancements and feature expansion

### Negative Consequences
- Increased initial development effort
- Requires strict discipline in maintaining layer boundaries

### Risks and Mitigations
- **Risk:** Tight coupling between layers over time
  - **Mitigation:** Enforce interface-based design and dependency injection principles

- **Risk:** Incorrect multi-user data isolation
  - **Mitigation:** Implement strict authorization checks and user-based query filtering

## Implementation

### Action Items
- [ ] Define layer responsibilities and module/package structure
- [ ] Implement LLM-based invoice extraction service and integration layer
- [ ] Add authorization checks to enforce user-specific invoice access

### Timeline
Implemented incrementally during development, starting with the layered structure, followed by JDBC-based persistence, LLM integration, and multi-user authorization.

### Dependencies
- Java 11+ (Plain Java)
- JDBC for database access
- Relational database (e.g., PostgreSQL)
- LLM API for structured invoice extraction
- Cloud hosting environment

## Validation
The decision is successful if the system demonstrates clear layer separation, secure multi-user data isolation, stable JDBC-based persistence, and reliable LLM-driven invoice data extraction in a cloud environment.

### Success Criteria
- Clear separation between presentation, service, and persistence layers is maintained
- Secure multi-user invoice ownership is correctly enforced

### Metrics
- Successful cloud deployment without architectural refactoring
- Zero unauthorized cross-user data access during testing


## Review
**Review Date:** N/A
**Review Notes:** Not yet reviewed. Architecture will be evaluated at project completion

## References
- Java SE 11 Official Documentation
- JDBC API Documentation
- General layered architecture and separation of concerns principles


## Related Decisions
- ADR-002: Technology Selection – Defines Plain Java, JDBC, LLM integration, and database choice.
- Architecture Overview – Describes the high-level system structure and layer responsibilities.

## Notes
This architecture is intentionally designed to balance academic project constraints with professional backend development standards.
