# Test Cases Design

## Overview

This document defines the test cases for the IntelliInvoice backend.
Tests were executed using **JUnit** (unit tests), **Postman**
(integration tests), and the **Angular UI** (system tests).

---

## Unit Tests — JUnit

| ID       | Component                | Test                                      | Expected Result                                          |
|----------|--------------------------|-------------------------------------------|----------------------------------------------------------|
| TC-D-001 | InvoiceRepository        | Save invoice and retrieve by ID           | Retrieved invoice matches saved data                     |
| TC-D-002 | InvoiceRepository        | Save two invoices and call findAll()      | Both invoices returned in list                           |
| TC-D-003 | InvoiceRepository        | Save invoice then deleteById()            | Invoice and line items removed from database             |
| TC-D-004 | InvoiceValidationService | Validate entity where items + VAT ≠ total | InvoiceValidationException thrown — INVALID_INVOICE_DATA |

---

## Integration Tests — Postman

| ID       | Endpoint                              | Input             | Expected Result                           |
|----------|---------------------------------------|-------------------|-------------------------------------------|
| TC-D-101 | POST /UploadInvoice                   | Valid JPG invoice | HTTP 200 — invoice saved, image in AWS S3 |
| TC-D-102 | POST /UploadInvoice                   | .txt file         | HTTP 400 — INVALID_FILE_FORMAT            |
| TC-D-103 | GET /myinvoices                       | —                 | HTTP 200 — list of InvoiceResponseDTO     |
| TC-D-104 | GET /myinvoices/{id}                  | Valid UUID        | HTTP 200 — invoice detail returned        |
| TC-D-105 | DELETE /myinvoices/{id}               | Valid UUID        | HTTP 200 — invoice deleted from database  |
| TC-D-106 | GET /myinvoices/search?name=          | Store name        | HTTP 200 — matching invoices returned     |
| TC-D-107 | GET /myinvoices/spending?year=&month= | Year + month      | HTTP 200 — total spending returned        |

---

## System Tests — Angular UI

| ID       | Scenario            | Steps                        | Expected Result                               |
|----------|---------------------|------------------------------|-----------------------------------------------|
| TC-D-201 | Full upload flow    | Upload image → confirm data  | Invoice visible in list with correct fields   |
| TC-D-202 | View invoice detail | Click View on invoice        | Detail page shows fields, items, and S3 image |
| TC-D-203 | Filter by status    | Click Returnable filter chip | Only returnable invoices shown                |
| TC-D-204 | View analytics      | Navigate to Analytics page   | Charts render with monthly and quarterly data |

---

## Coverage Summary

| Type        | Tool       | Test Cases           | Requirements Covered                           |
|-------------|------------|----------------------|------------------------------------------------|
| Unit        | JUnit      | TC-D-001 to TC-D-004 | FR-004, FR-005, FR-011                         |
| Integration | Postman    | TC-D-101 to TC-D-107 | FR-001, FR-007, FR-008, FR-010, FR-011, FR-012 |
| System      | Angular UI | TC-D-201 to TC-D-204 | FR-001, FR-007, FR-009, FR-012                 |