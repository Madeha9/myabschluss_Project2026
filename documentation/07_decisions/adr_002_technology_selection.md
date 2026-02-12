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
- The project is developed by a single developer with academic training in Java-based backend development.
- Prior experience includes working with Java, relational databases, and layered architectural patterns.
- Spring Boot and related technologies are used as part of professional skill development and portfolio enhancement.
- The selected stack aligns with current industry standards and supports continued technical growth.

### Community and Support
- Spring Boot, Spring Security, and Hibernate are widely adopted industry-standard technologies with extensive documentation and large developer communities.
- PostgreSQL is a mature and globally supported open-source database system with strong community and enterprise backing.
- Flyway and OpenAPI are well-documented and actively maintained projects with stable release cycles.
- The broad ecosystem ensures long-term support, availability of learning resources, and rapid issue resolution.

### Cost Considerations
- All selected technologies (Spring Boot, Spring Security, PostgreSQL, Flyway, OpenAPI) are open-source and licensed under permissive licenses, resulting in no direct licensing costs
- Development tools are freely available for academic and non-commercial use
- Infrastructure costs are limited to optional cloud hosting and database services, depending on the selected deployment provider
- The open-source nature of the stack minimizes vendor lock-in and long-term financial risk

### Long-term Viability

- Spring Boot and Spring Security are actively maintained and widely adopted frameworks with strong enterprise backing and long-term roadmap stability
- PostgreSQL is a mature, globally trusted database system with continuous development and long-term community support
- Flyway and OpenAPI are established projects with stable release cycles and broad industry usage
- The selected technologies align with current industry standards, ensuring continued relevance and maintainability beyond the academic scope of the project

## Alternatives Considered

### Alternative Technology Stack 1
**Technologies:** Plain Java (Servlet-based), JDBC, PostgreSQL

**Evaluation:**

| Criterion           | Score (1-5) | Notes                                                                                       |
|---------------------|-------------|---------------------------------------------------------------------------------------------|
| Technical Fit       | 3           | Supports layered design but requires significant manual configuration and boilerplate code. |
| Team Experience     | 4           | Strong familiarity with core Java concepts.                                                 |
| Community Support   | 5           | Java ecosystem is mature and widely supported.                                              |
| Cost                | 5           | Fully open-source with no licensing costs.                                                  |
| Long-term Viability | 4           | Stable and reliable but less aligned with modern backend standards.                         |
| **Total**           | **21**      |                                                                                             |

**Reason for Rejection:**  
Although technically feasible, this approach would require extensive manual configuration, JDBC handling, and security implementation.
It increases development complexity and reduces maintainability compared to Spring Boot’s integrated ecosystem.


## Alternative Technology Stack 2
**Technologies:** Jakarta EE (JAX-RS), CDI, JPA/Hibernate, PostgreSQL

**Evaluation:**

| Criterion           | Score (1-5) | Notes                                                                                          |
|---------------------|-------------|------------------------------------------------------------------------------------------------|
| Technical Fit       | 4           | Provides a solid enterprise backend foundation and supports REST and persistence well.         |
| Team Experience     | 2           | Higher learning curve and less hands-on experience compared to Spring Boot.                    |
| Community Support   | 4           | Mature ecosystem with strong standards, but less beginner-friendly documentation and examples. |
| Cost                | 5           | Open-source and widely available implementations.                                              |
| Long-term Viability | 4           | Stable and standards-based, suitable for long-term maintenance.                                |
| **Total**           | **19**      |                                                                                                |

**Reason for Rejection:**  
While technically suitable, Jakarta EE introduces additional setup and deployment complexity (application server configuration) compared to Spring Boot.
Spring Boot provides faster development, simpler configuration, and a more streamlined ecosystem for the project scope and timeframe.


## Technology Evaluation Matrix

| Technology                   | Technical Fit | Team Experience | Community | Cost | Viability | Total  |
|------------------------------|---------------|-----------------|-----------|------|-----------|--------|
| Selected (Spring Boot Stack) | 5             | 4               | 5         | 5    | 5         | **24** |
| Alt 1 (Plain Java + JDBC)    | 3             | 4               | 5         | 5    | 4         | 21     |
| Alt 2 (Jakarta EE Stack)     | 4             | 2               | 4         | 5    | 4         | 19     |


## Consequences

### Positive Consequences
- The selected stack enables rapid backend development with reduced boilerplate configuration.
- Clear separation of concerns improves maintainability and testability.
- Built-in security support allows implementation of authenticated multi-user access.
- Version-controlled database migrations increase deployment reliability.
- Automated API documentation improves transparency and developer experience.
- The use of industry-standard technologies strengthens long-term maintainability and portfolio credibility.


### Negative Consequences
- The selected stack introduces additional complexity compared to a plain Java implementation.
- Framework dependencies increase abstraction and may require additional learning and configuration effort.

### Technical Debt
- Initial security configuration may be implemented at a basic level and refined in future iterations
- Role-based access control may be simplified during the initial implementation phase
- Database indexing and performance tuning may be postponed until real-world load scenarios are evaluated
- API versioning strategy is not fully defined and may require future refinement


### Learning Curve
- Deeper understanding of Spring Boot ecosystem and advanced configuration
- Practical implementation of Spring Security for authentication and authorization
- Managing database schema migrations using Flyway
- Designing secure and well-structured REST APIs following best practices


## Implementation

### Setup and Configuration
1. Set up the project structure and dependency management (Maven) and configure the development environment
2. Configure database connectivity and persistence (PostgreSQL, JPA/Hibernate) and initialize schema migrations (Flyway)
3. Implement REST endpoints and enable supporting features such as API documentation (OpenAPI) and authentication (if applicable)

### Training Needs
- [Training 1]
- [Training 2]

### Migration Strategy
Not applicable – the system is developed as a new implementation and does not replace an existing production system.


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
