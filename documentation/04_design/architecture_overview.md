# Architecture Overview

## Introduction
This document describes the high-level architecture of the IntelliInvoice system.
The system allows a user to upload invoice images, extract structured data using an LLM, and store the results.

## Architecture Goals
- Simple and clean system design
- Separation between frontend and backend
- Easy to extend in the future
- Cloud-based storage for reliability

## Architecture Style
**Layered (3-tier) architecture**
- **Presentation layer:** Frontend UI
- **Application layer:** Backend REST API (business logic)
- **Data layer:** Database + cloud storage

## High-Level Architecture Diagram
![IntelliInvoice - High-Level Architecture.png](IntelliInvoice%20-%20High-Level%20Architecture.png)

## System Components

### Component 1: Frontend (Web UI)
**Purpose:** User interface for uploading and viewing invoices.
**Responsibilities:**
- Upload invoice image
- Display processing status (processing / done / error)
- Show invoice list
- Show invoice details

**Technologies:**
- Simple web interface (technology not fixed)


### Component 2: Backend (REST API)
**Purpose:** Handles business logic and integrates with external services.
**Responsibilities:**
- Receive uploaded invoice images
- Store original image in cloud storage
- Send image (or URL) to LLM service for extraction
- Validate extracted data (basic checks)
- Store structured invoice data in the database
- Provide endpoints for listing, viewing, and deleting invoices

**Technologies:**
- Java
- Quarkus

### Component 3: Database
**Purpose:** Stores structured invoice data.
**Responsibilities:**
- Persist invoice metadata and extracted fields
- Support queries for invoice list and details

**Technologies:**
- PostgreSQL (production)

### Component 4: Cloud Storage
**Purpose:**  
Stores original invoice images.
**Responsibilities:**
- Save original uploaded invoice image
- Provide access (URL) for backend processing and later reference

**Technologies:**
- Cloud object storage (e.g., S3 – Simple Storage Service, or S3-compatible storage)

### Component 5: LLM Service
**Purpose:** Extracts structured invoice data from images.
**Responsibilities:**
- Receive invoice image (or URL)
- Return structured invoice data (JSON)

**Technologies:**
- External LLM API/service


## Key Architectural Decisions
1. Use **LLM-based extraction** instead of classic OCR
   - **Rationale:** Better structured data extraction and future AI-focused work
   - **Alternatives Considered:** Traditional OCR libraries and rule-based parsing

2. Separate **Frontend** and **Backend**
   - **Rationale:** Better maintainability, clearer responsibilities, easier deployment
   - **Alternatives Considered:** Single monolithic application

## Quality Attributes

### Performance
- Keep endpoints lightweight
- LLM processing is treated as a separate step (can be async later)

### Security
- Protect API endpoints (future: authentication)
- Restrict cloud storage access (private bucket, signed URLs if needed)

### Scalability
- Backend can be containerized (Docker) and scaled independently
- LLM service is external and can be swapped or upgraded

### Maintainability
- Clear layering and separation of concerns
- Modular components (UI, API, storage, extraction)

## Constraints and Limitations
- Single user system (no login/authentication)
- LLM extraction depends on external service availability and cost

## Future Considerations
- Add user login (JWT-based authentication)
- Add multi-user support and roles
- Add better validation and error handling
- Add monitoring/logging and metrics
- Cloud deployment (CI/CD, staging/production environments)
- Add analytics and reporting (invoice statistics, summaries, trends)