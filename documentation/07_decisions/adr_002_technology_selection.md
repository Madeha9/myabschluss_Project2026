# ADR-002: Technology Selection

**Status:** Accepted
**Date:** 2026-02-11
**Author:** Madeha Mohammed

---

## Context

IntelliInvoice required a modern Java backend stack that supports
a layered architecture, REST API development, AI integration,
cloud storage, and relational database persistence — all within
a single-developer academic project timeline.

---

## Selected Technologies

| Technology       | Version         | Purpose                                          |
|------------------|-----------------|--------------------------------------------------|
| Java             | 21              | Core programming language                        |
| Quarkus          | 3.2.3           | Backend REST API framework                       |
| Hibernate / JPA  | via Quarkus     | ORM — maps Java entities to PostgreSQL tables    |
| PostgreSQL       | 15.x            | Relational database for invoice and item storage |
| LangChain4j      | via Quarkus     | AI integration layer for Claude Sonnet           |
| Claude Sonnet    | claude-3-sonnet | AI model for invoice data extraction             |
| AWS SDK          | v2              | AWS S3 integration for image storage             |
| Angular          | 21              | Frontend web interface                           |
| Angular Material | 21              | UI component library                             |
| Chart.js         | 4.4.1           | Spending analytics charts                        |
| Maven            | 3.x             | Build tool — multi-module project                |
| IntelliJ IDEA    | —               | Development environment                          |
| Git + GitHub     | —               | Version control and project management           |

---

## Rationale

**Quarkus over Spring Boot** — Quarkus is the framework taught
in the WiFi Vienna course. It provides fast startup, built-in
CDI dependency injection, and native Quarkus extensions for
JPA, REST, and LangChain4j integration.

**Claude Sonnet over traditional OCR** — LLM-based extraction
produces structured data directly without manual parsing rules.
More flexible and accurate for varying invoice formats.

**AWS S3 for image storage** — Only the URL is stored in
PostgreSQL. Keeps the database lightweight and images
accessible from anywhere.

**Angular for frontend** — Used to present and test the backend
results visually. Not the main focus of the project.

---

## Alternatives Considered

**Spring Boot** — rejected in favour of Quarkus since Quarkus
was used throughout the course and integrates natively with
LangChain4j and the Quarkus extension ecosystem.

**Traditional OCR** — rejected because Claude Sonnet produces
structured Java objects directly without manual field parsing.

---

## Consequences

**Positive:**

- Full stack works together with minimal configuration
- LangChain4j + Claude Sonnet handles AI extraction cleanly
- Maven multi-module builds frontend and backend in one command

**Negative:**

- Depends on two external paid services: AWS S3 and Anthropic API
- Claude Sonnet accuracy depends on image quality

---

## Success Criteria

- All REST endpoints function correctly with Quarkus
- Hibernate auto-creates and manages the database schema
- Claude Sonnet reliably extracts invoice data from images
- AWS S3 stores and serves invoice images via URL