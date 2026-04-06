# Deployment Diagram

## Overview

This document describes the deployment architecture of IntelliInvoice.
The system currently runs in a local development environment.
Production deployment is a future consideration.

---

## Deployment Diagram

![Deployment_diagram.png](/documentation/04_design/Design_diagrams/Deployment_diagrams/Deployment_diagram.png)
---

## Current Deployment — Development Environment

| Component        | Technology              | Location               |
|------------------|-------------------------|------------------------|
| Angular Frontend | Angular 21              | http://localhost:4200  |
| Quarkus Backend  | Java 21 + Quarkus 3.2.3 | http://localhost:8080  |
| Database         | PostgreSQL              | localhost:5432         |
| Image Storage    | AWS S3 (eu-north-1)     | External cloud service |
| AI Extraction    | Anthropic Claude Sonnet | External cloud service |

**Developer machine:** macOS, IntelliJ IDEA

---

## Communication

```text
       [ Browser (Angular) ]
               │
               ▼
             HTTP
               │
   ┌───────────┴───────────┐
   │  Quarkus Backend      │
   │  (Port 8080)          │
   └───────────┬───────────┘
               │
      ┌────────┴────────┬────────┐
      ▼                 ▼        ▼
     JPA              HTTPS    HTTPS
 (Hibernate)         (S3 SDK) (REST/AI)
      │                 │        │
      ▼                 ▼        ▼
[ PostgreSQL ]      [ AWS S3 ] [ Anthropic API ]
 (Port 5432)        (Storage)  (AI Engine) 
```

---

## Environment Variables

Sensitive credentials are never stored in the code. Instead, they are set as environment variables in the IntelliJ
Run Configuration and referenced in the application.properties file

# AWS Connectivity Settings

AWS_ACCESS_KEY_ID = <your-aws-access-key>
AWS_SECRET_ACCESS_KEY = <your-aws-secret-key>
AWS_REGION = eu-north-1

# S3 Bucket Configuration

AWS_BUCKET_NAME = intelliinvoice-files-wifi-2026

---

## Build and Run

### Development mode — two terminals

```bash
# Terminal 1 — Quarkus backend
cd backend
./mvnw quarkus:dev
# runs on http://localhost:8080

# Terminal 2 — Angular frontend
cd frontend
npm start
# runs on http://localhost:4200
```

### Production build — one command

```bash
# Builds backend + frontend into a single JAR
mvn package
```

---

## Project Structure

```text
intelli-invoice-parent/
├── backend/         # Quarkus REST API (AI Engine + AWS S3 Integration)
│   └── pom.xml
├── frontend/        # Angular 21 Web Interface
│   └── pom.xml
└── pom.xml          # Parent POM (Multi-module Configuration)
```

---

## CORS Configuration

Since Angular (port 4200) and Quarkus (port 8080) run on
different ports during development, CORS is handled by a
dedicated Java filter:

```java
@Provider
public class CorsFilter implements ContainerResponseFilter {
    // Allows requests from http://localhost:4200
}
```

---

## Future Considerations

- Docker containerization of the Quarkus backend
- Cloud deployment (e.g. AWS, Railway, or Render)
- CI/CD pipeline with GitHub Actions
- Production PostgreSQL managed database
- HTTPS and JWT-based authentication
- Native mobile app with camera capture