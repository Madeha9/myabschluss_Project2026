# Test Strategy

## Overview

This document outlines the testing approach for IntelliInvoice.
Testing was performed by a single developer using three levels:
unit, integration, and system testing.

---

## Test Levels

| Level       | Objective                                         | Tool       | Scope                                        |
|-------------|---------------------------------------------------|------------|----------------------------------------------|
| Unit        | Test repository and validation logic in isolation | JUnit      | InvoiceRepository, InvoiceValidationService  |
| Integration | Test REST API endpoints end to end                | Postman    | All /myinvoices and /UploadInvoice endpoints |
| System      | Test full user flows in the browser               | Angular UI | Upload, list, detail, analytics, delete      |
| Acceptance  | Verify features meet requirements                 | Angular UI | All implemented use cases                    |

---

## In Scope

- Invoice upload and AI extraction flow
- Database CRUD operations
- REST API endpoints
- Return status calculation
- Error handling and validation

## Out of Scope

- Performance and load testing
- Security and penetration testing
- Automated UI testing (e.g. Cypress)
- Multi-user or authentication testing

---

## Test Environment

- **Machine:** MacOS, IntelliJ IDEA
- **Backend:** Quarkus running on http://localhost:8080
- **Frontend:** Angular running on http://localhost:4200
- **Database:** PostgreSQL local instance
- **External:** AWS S3 and Anthropic Claude API

---

## Test Results

All 20 test cases passed with a **100% pass rate**.
No critical defects were found during testing.