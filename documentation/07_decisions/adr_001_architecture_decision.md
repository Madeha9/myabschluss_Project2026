# ADR-001: Architecture Decision

**Status:** Accepted
**Date:** 2026-02-12
**Author:** Madeha Mohammed

---

## Context

IntelliInvoice is a backend-focused invoice processing system
built with Java 21 and Quarkus. The system needed a clear,
maintainable architecture that separates concerns, integrates
AI extraction and cloud storage, and remains extensible for
future features.

---

## Decision

The system uses a **layered 3-tier architecture** with strict
separation between the presentation, service, and data layers.

Presentation → RestAPI (InvoiceController, UploadController)
Application → Service (InvoiceProcessingService, InvoiceReturnService,
InvoiceValidationService)
Data → Repository (InvoiceRepository) + PostgreSQL
Infrastructure (CloudStorageService, InvoiceExtractor)

Instead of traditional OCR, the system integrates **Claude Sonnet AI**
via LangChain4j to extract structured invoice data from uploaded images.
Original images are stored in **AWS S3** — only the URL is saved in
the database.

---

## Rationale

| Reason                | Detail                                                                       |
|-----------------------|------------------------------------------------------------------------------|
| Maintainability       | Clear layer separation makes the code easy to understand and extend          |
| Testability           | Each layer can be tested independently                                       |
| Professional standard | Layered architecture is industry standard for Java backends                  |
| AI over OCR           | Claude Sonnet produces structured data directly without manual parsing rules |

---

## Alternatives Considered

**Simple monolithic architecture** — rejected because it leads to
tight coupling and poor maintainability as the system grows.

**Microservices architecture** — rejected because it is over-engineered
for a single-developer academic project with a limited timeline.

---

## Consequences

**Positive:**

- Clean, professional code structure
- Easy to add new features without breaking existing ones
- Each component has a single clear responsibility

**Negative:**

- More initial setup than a simple monolithic approach
- Requires discipline to maintain layer boundaries

---

## Dependencies

- Java 21 + Quarkus 3.2.3
- PostgreSQL + Hibernate JPA
- AWS S3 (eu-north-1)
- Anthropic Claude Sonnet via LangChain4j
- Angular 21 (frontend)

---

## Success Criteria

- Clear separation between controller, service, and repository layers
- AI extraction works reliably for standard invoice images
- All error cases handled with specific ErrorCode values