# Lessons Learned

## Project Overview

**Project:** IntelliInvoice
**Duration:** January 2026 — April 2026
**Team Size:** 1 developer
**Status:** Completed

---

## What Went Well

| Area               | What worked                                                                               |
|--------------------|-------------------------------------------------------------------------------------------|
| AI Integration     | LangChain4j + Claude Sonnet integration was straightforward and produced accurate results |
| AWS S3             | File upload and URL storage worked reliably from the first implementation                 |
| Architecture       | Clean 3-layer architecture made the code easy to understand and extend                    |
| Validation         | Math validation (items + VAT = total) caught real AI extraction errors during testing     |
| Maven multi-module | One `mvn package` builds frontend and backend together cleanly                            |
| Angular UI         | AI-assisted code generation significantly reduced frontend development time               |

---

## Challenges

| Challenge                                                                             | Impact                                 | How it was resolved                                                             |
|---------------------------------------------------------------------------------------|----------------------------------------|---------------------------------------------------------------------------------|
| CORS errors between Angular and Quarkus                                               | Upload failed from browser             | Created a Java CorsFilter class                                                 |
| Angular Material theme API changed in v21                                             | styles.scss errors                     | Switched to M3 `mat.theme()` API                                                |
| AI returns wrong totals for some invoices                                             | Math validation rejects valid invoices | Improved error message to show mismatch details                                 |
| AWS credentials management                                                            | Risk of exposing keys in code          | Used IntelliJ environment variables                                             |
| Quarkus version 2.1 incompatible with LangChain4j and AWS SDK                         | Build failed                           | Upgraded to Quarkus 3.2.3                                                       |
| Integrating Claude Sonnet, AWS S3, PostgreSQL and Angular all at once was too complex | Hard to debug errors                   | Built and tested each component independently first, then integrated one by one |

---

## Key Lessons Learned

1. **Start with architecture** — defining the layer structure first made everything else easier to implement and test

2. **AI integration is powerful but unpredictable** — Claude Sonnet works well for clear invoice images but struggles
   with blurry or non-standard formats. Always validate AI output.

3. **Environment variables from day one** — storing credentials in code causes security risks and refactoring effort
   later

4. **Test with Postman early** — testing endpoints with Postman before building the UI saved a lot of debugging time

5. **Documentation takes longer than expected** — allocate dedicated time for UML diagrams and technical documentation
6. **Incremental integration saves debugging time** — building and
   testing each component (PostgreSQL, AWS S3, Claude Sonnet, Angular)
   independently before connecting them together made the integration
   much easier to debug and understand

---

## Skills Gained

- Java 21 + Quarkus REST API development
- LangChain4j AI integration with Claude Sonnet
- AWS S3 cloud storage integration
- Angular 21 + Angular Material frontend development
- UML diagram creation with PlantUML
- Maven multi-module project management
- PostgreSQL + Hibernate JPA database mapping

---

## Recommendations for Future Projects

- Add JWT authentication from the start — retrofitting security is harder
- Use Flyway for database migrations from day one
- Implement camera capture for a better mobile upload experience
- Add automated tests with higher coverage using JUnit and Mockito

