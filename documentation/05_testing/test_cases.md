# Test Cases

## Overview

This document contains all executed test cases for IntelliInvoice.
Testing was done using **JUnit** (unit), **Postman** (integration),
and **Angular UI** (system and acceptance).

---

## Functional Test Cases

| ID       | Test                                    | Requirement | Tool    | Status |
|----------|-----------------------------------------|-------------|---------|--------|
| TC-F-001 | Upload valid JPG invoice                | FR-001      | Postman | Passed |
| TC-F-002 | Reject unsupported file type (.txt)     | FR-001      | Postman | Passed |
| TC-F-003 | AI extracts store, date, total, VAT     | FR-003      | Postman | Passed |
| TC-F-004 | Math validation — items + VAT = total   | FR-004      | JUnit   | Passed |
| TC-F-005 | Save invoice and line items to database | FR-005      | JUnit   | Passed |
| TC-F-006 | Return status calculated automatically  | FR-006      | JUnit   | Passed |

---

## Integration Test Cases

| ID       | Endpoint                              | Input        | Expected                       | Status |
|----------|---------------------------------------|--------------|--------------------------------|--------|
| TC-I-001 | POST /UploadInvoice                   | Valid JPG    | HTTP 200 — invoice saved       | Passed |
| TC-I-002 | POST /UploadInvoice                   | .txt file    | HTTP 400 — INVALID_FILE_FORMAT | Passed |
| TC-I-003 | GET /myinvoices                       | —            | HTTP 200 — invoice list        | Passed |
| TC-I-004 | GET /myinvoices/{id}                  | Valid UUID   | HTTP 200 — invoice detail      | Passed |
| TC-I-005 | DELETE /myinvoices/{id}               | Valid UUID   | HTTP 200 — invoice deleted     | Passed |
| TC-I-006 | GET /myinvoices/search?name=          | Store name   | HTTP 200 — filtered results    | Passed |
| TC-I-007 | GET /myinvoices/spending?year=&month= | Year + month | HTTP 200 — total spending      | Passed |

---

## System Test Cases

| ID       | Scenario                         | Tool       | Expected                   | Status |
|----------|----------------------------------|------------|----------------------------|--------|
| TC-S-001 | Full upload flow end to end      | Angular UI | Invoice visible in list    | Passed |
| TC-S-002 | View invoice detail and S3 image | Angular UI | All fields and image shown | Passed |
| TC-S-003 | Filter invoices by return status | Angular UI | Correct invoices shown     | Passed |
| TC-S-004 | View spending analytics charts   | Angular UI | Charts render correctly    | Passed |

---

## Acceptance Test Cases

| ID       | Scenario                                     | Criteria                           | Status |
|----------|----------------------------------------------|------------------------------------|--------|
| TC-A-001 | User uploads invoice and sees extracted data | Success screen with correct fields | Passed |
| TC-A-002 | User checks return deadline                  | Days remaining shown correctly     | Passed |
| TC-A-003 | User deletes an invoice                      | Invoice removed from list          | Passed |

---

## Test Summary

| Category    | Total  | Passed | Failed | Pass Rate |
|-------------|--------|--------|--------|-----------|
| Functional  | 6      | 6      | 0      | 100%      |
| Integration | 7      | 7      | 0      | 100%      |
| System      | 4      | 4 =    | 0      | 100%      |
| Acceptance  | 3      | 3      | 0      | 100%      |
| **Total**   | **20** | **20** | **0**  | **100%**  |