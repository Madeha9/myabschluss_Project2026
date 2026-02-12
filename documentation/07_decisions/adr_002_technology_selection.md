# ADR 002: Technology Selection

## Status
**Status:** Accepted
**Date:** 2026-02-11
**Decision Makers:** Madeha Mohammed 

## Context

The Smart Invoice system is developed as a final academic software engineering project. 
The system aims to digitalize physical retail invoices and provide structured storage, retrieval, 
and future AI-assisted analysis features. To achieve these objectives, a reliable backend architecture, database persistence
mechanism, and cloud-compatible deployment environment are required. The technology stack must support clean 
separation of concerns (Controller–Service–Repository), long-term maintainability, and alignment with industry
standards in Java-based backend development

### Problem Statement

The project requires a modern, maintainable Java backend stack that supports a layered architecture,
reliable persistence for invoice data in a relational database, and compatibility with cloud deployment.
The chosen technologies must reduce manual boilerplate (e.g., configuration and JDBC code) and enable future
extension (e.g., AI features) without redesigning the system

### Requirements

- The stack must support Java 11+
- The system must follow a layered architecture
- The backend must support REST API development
- The solution must use a relational database (PostgreSQL)
- The project must use Maven for build and dependency management
- The stack must support testing and maintainability

### Constraints
* Limited to the Java 11+ ecosystem, with Maven for build automation and JPA/Hibernate for persistence
* * Academic scope and timeframe, with no handling of sensitive banking/payment data.

## Decision
The Smart Invoice system will be implemented using a Java 11+ based technology stack, following a layered architecture 
The backend will be built with Spring Boot, using Maven for build automation,
JPA/Hibernate for persistence, and PostgreSQL as the relational database.

### Selected Technologies

#### Technology 1: Spring Boot
**Purpose:** Spring Boot serves as the core backend framework of the Smart Invoice system. 
It provides autoconfiguration, dependency management, embedded server support, and production-ready 
features required to implement a RESTful, layered Java backend. 
It enables rapid development while enforcing clean architectural separation (Controller–Service–Repository) and 
industry-standard design principles.
**Version:** Spring Boot 2.7x
**License:** Apache License 2.0 (Open-source)

#### Technology 2: Hibernate (JPA Implementation)
**Purpose:** Hibernate is used as the JPA (Jakarta Persistence API) implementation for object-relational mapping (ORM). 
It manages the mapping between Java entity classes and relational database tables, abstracts low-level JDBC operations,
and enables database interaction through repository-based data access patterns.
**Version:** Hibernate 5.6.x
**License:** GNU Lesser General Public License (LGPL) 2.1

#### Technology 3: PostgreSQL
**Purpose:**  
PostgreSQL is used as the primary relational database management system (RDBMS) for persistent storage of invoice data, 
structured metadata, and related entities. It ensures data integrity, ACID compliance, transactional consistency, and 
reliable query performance in a cloud-deployable environment.

**Version:**  
PostgreSQL 15.x (or the version provided by the selected cloud provider)

**License:**  
PostgreSQL License (Open-source, permissive)

#### Technology 4: OpenAPI (Swagger)

**Purpose:**  
OpenAPI (via Swagger UI) is used to provide automated, interactive documentation for the system’s RESTful APIs. 
It enables clear specification of endpoints, request/response schemas, and HTTP status codes, while allowing developers
to test API operations directly through a web interface. This improves maintainability, transparency, and developer experience.

**Version:**   
springdoc-openapi 1.6.x (compatible with Spring Boot 2.7.x)

**License:**  
Apache License 2.0 (Open-source)

#### Technology 5: Flyway

**Purpose:**  
Flyway is used for database schema versioning and migration management. It ensures that all database 
changes(e.g., table creation, column modifications) are applied in a controlled, versioned, and repeatable manner across
development and cloud environments. This guarantees consistency, traceability, and deployment reliability.

**Version:**  
Flyway 9.x (managed via Spring Boot dependency management)

**License:**  
Apache License 2.0 (Open-source)

#### Technology 6: Spring Security

**Purpose:**  
Spring Security provides authentication and authorization for the system, enabling secure user login and controlled access
to invoice data in a multi-user environment.

**Version:**  
Spring Security 6.x (managed via Spring Boot dependency management)

**License:**  
Apache License 2.0 (Open-source)


## Rationale


The selected technology stack was chosen to provide a structured, maintainable, and industry-aligned backend architecture.

Spring Boot enables rapid development while supporting a clean layered architecture and RESTful API design.
PostgreSQL was selected for reliable relational data storage and strong transactional guarantees suitable for invoice data.

Flyway ensures controlled and versioned database schema evolution across environments.
OpenAPI provides automated REST API documentation, improving transparency and maintainability.

Spring Security enables authenticated multi-user access and controlled data authorization within the system.

Overall, the stack balances simplicity, scalability, and best practices while remaining appropriate for the academic scope of the project.


### Technical Fit

- The selected stack fully supports a layered architecture (Controller–Service–Repository), ensuring clear separation of concerns
- Spring Boot integrates seamlessly with Hibernate (JPA), PostgreSQL, Flyway, and Spring Security, reducing configuration complexity
- PostgreSQL provides strong transactional consistency (ACID compliance), which is essential for reliable invoice data management
- Flyway ensures structured schema evolution, supporting long-term maintainability
- OpenAPI integrates directly with Spring Boot, enabling automated API documentation without additional infrastructure
- The stack is fully compatible with cloud deployment environments


### Team Experience
- [Team's experience with the technology]

### Community and Support
- [Community size, support availability]

### Cost Considerations
- [Licensing costs, infrastructure costs]

### Long-term Viability
- [Technology roadmap, vendor stability]

## Alternatives Considered

### Alternative Technology Stack 1
**Technologies:** [List of technologies]

**Evaluation:**
| Criterion | Score (1-5) | Notes |
|-----------|-------------|-------|
| Technical Fit | [Score] | [Notes] |
| Team Experience | [Score] | [Notes] |
| Community Support | [Score] | [Notes] |
| Cost | [Score] | [Notes] |
| Long-term Viability | [Score] | [Notes] |
| **Total** | **[Total]** | |

**Reason for Rejection:** [Why not chosen]

### Alternative Technology Stack 2
**Technologies:** [List of technologies]

**Evaluation:**
| Criterion | Score (1-5) | Notes |
|-----------|-------------|-------|
| Technical Fit | [Score] | [Notes] |
| Team Experience | [Score] | [Notes] |
| Community Support | [Score] | [Notes] |
| Cost | [Score] | [Notes] |
| Long-term Viability | [Score] | [Notes] |
| **Total** | **[Total]** | |

**Reason for Rejection:** [Why not chosen]

## Technology Evaluation Matrix

| Technology | Technical Fit | Team Experience | Community | Cost    | Viability | Total       |
|------------|---------------|-----------------|-----------|---------|-----------|-------------|
| [Selected] | [Score]       | [Score]         | [Score]   | [Score] | [Score]   | **[Total]** |
| [Alt 1]    | [Score]       | [Score]         | [Score]   | [Score] | [Score]   | [Total]     |
| [Alt 2]    | [Score]       | [Score]         | [Score]   | [Score] | [Score]   | [Total]     |

## Consequences

### Positive Consequences
- [Consequence 1]
- [Consequence 2]

### Negative Consequences
- [Consequence 1]
- [Consequence 2]

### Technical Debt
[Any technical debt introduced by this decision]

### Learning Curve
[Required learning and training]

## Implementation

### Setup and Configuration
1. [Step 1]
2. [Step 2]
3. [Step 3]

### Training Needs
- [Training 1]
- [Training 2]

### Migration Strategy
[If migrating from another technology]

## Dependencies

### External Dependencies
- [Dependency 1]
- [Dependency 2]

### Version Compatibility
[Compatibility requirements]

## Risks and Mitigations

### Risk 1: [Risk Description]
**Probability:** [High/Medium/Low]
**Impact:** [High/Medium/Low]
**Mitigation:** [Mitigation strategy]

### Risk 2: [Risk Description]
**Probability:** [High/Medium/Low]
**Impact:** [High/Medium/Low]
**Mitigation:** [Mitigation strategy]

## Validation

### Success Criteria
- [Criterion 1]
- [Criterion 2]

### Performance Benchmarks
- [Benchmark 1]
- [Benchmark 2]

## Review
**Review Date:** [YYYY-MM-DD or N/A]
**Review Notes:** [Notes from review or "Not yet reviewed"]

## References
- [Official documentation]
- [Comparison articles]
- [Benchmark studies]

## Related Decisions
- [ADR-XXX]: [Brief description of relationship]

## Notes
[Any additional notes or comments]
