# Test Cases

## Overview

This document contains the main test cases for the IntelliInvoice system,
covering the core functional requirements. Test cases were executed using
three approaches:

- **Angular UI** — end-to-end manual testing of all user interface flows
- **Postman** — manual REST API testing of all backend endpoints
- **JUnit** — automated unit tests for database CRUD operations
  (InvoiceRepository: save, findById, findAll, deleteById)

---

## TC-001: Upload Valid Invoice Image

**Requirement:** FR-001, FR-002, FR-003
**Use Case:** UC-01
**Priority:** High

**Preconditions:**

- Angular UI is running at http://localhost:4200
- Quarkus backend is running at http://localhost:8080
- A valid invoice image (JPG or PNG) is available

**Test Steps:**

1. Navigate to the Upload page
2. Select a valid JPG invoice image
3. Click "Process invoice"

**Expected Result:**

- File is uploaded to AWS S3
- Claude Sonnet extracts store name, date, total, VAT, and line items
- Success screen displays extracted data
- Invoice appears in the Invoice List

---

## TC-002: Reject Unsupported File Type

**Requirement:** FR-001
**Use Case:** UC-01
**Priority:** Medium

**Preconditions:**

- User is on the Upload page
- An unsupported file is available (e.g. .txt or .doc)

**Test Steps:**

1. Select an unsupported file
2. Click "Process invoice"

**Expected Result:**

- System rejects the file
- Error message displayed:
  "Unsupported format. Please use PDF, PNG, or JPG."
- Nothing is saved to the database

---

## TC-003: Math Validation Failure

**Requirement:** FR-004
**Use Case:** UC-02
**Priority:** High

**Preconditions:**

- An invoice image where line items + VAT
  do not equal the total amount

**Test Steps:**

1. Upload an invoice with incorrect totals
2. Click "Process invoice"

**Expected Result:**

- System rejects the extracted data
- Error message displayed:
  "Math error! Items + VAT does not equal Total"
- Nothing is saved to the database

---

## TC-004: View Invoice List

**Requirement:** FR-007
**Use Case:** UC-03
**Priority:** High

**Preconditions:**

- At least one invoice is saved in the database

**Test Steps:**

1. Navigate to the Invoice List page

**Expected Result:**

- All invoices are displayed in a table
- Each row shows store name, date, total,
  VAT, status badge, days left, and thumbnail
- Metric cards show correct counts

---

## TC-005: Search Invoice by Store Name

**Requirement:** FR-008
**Use Case:** UC-04
**Priority:** Medium

**Preconditions:**

- At least one invoice exists in the database

**Test Steps:**

1. Navigate to the Invoice List page
2. Type a store name in the search field

**Expected Result:**

- Table updates to show only matching invoices
- If no match found: "No invoices found" is displayed

---

## TC-006: Filter by Return Status

**Requirement:** FR-009
**Use Case:** UC-07
**Priority:** Medium

**Preconditions:**

- Invoices with different statuses exist in the database

**Test Steps:**

1. Navigate to the Invoice List page
2. Click the "Returnable" filter chip

**Expected Result:**

- Table shows only invoices with RETURNABLE status
- Other invoices are hidden

---

## TC-007: View Invoice Detail

**Requirement:** FR-010
**Use Case:** UC-05
**Priority:** High

**Preconditions:**

- At least one invoice exists in the database

**Test Steps:**

1. Click the View button on any invoice row

**Expected Result:**

- Detail page shows store name, date, total,
  VAT, status, days remaining, and line items
- Original invoice image is loaded from AWS S3
- Return window progress bar is displayed

---

## TC-008: Delete Invoice

**Requirement:** FR-011
**Use Case:** UC-06
**Priority:** Medium

**Preconditions:**

- At least one invoice exists in the database

**Test Steps:**

1. Click the Delete button on any invoice row
2. Confirm deletion in the dialog

**Expected Result:**

- Invoice is removed from the table
- Invoice and all line items are deleted from PostgreSQL

---

## TC-009: View Spending Analytics

**Requirement:** FR-012
**Use Case:** UC-08
**Priority:** Medium

**Preconditions:**

- At least one invoice exists in the database

**Test Steps:**

1. Navigate to the Analytics page
2. Select a year and month
3. Click Apply

**Expected Result:**

- Monthly bar chart renders with colour per month
- Quarterly donut chart shows Q1-Q4 breakdown
- Top stores horizontal bar chart shows top 5 stores

---

## TC-010: Handle AI Extraction Failure

**Requirement:** FR-003
**Use Case:** UC-02
**Priority:** Medium

**Preconditions:**

- A blurry or unreadable invoice image is available

**Test Steps:**

1. Upload a blurry or non-invoice image
2. Click "Process invoice"

**Expected Result:**

- System shows error:
  "AI could not read the invoice or returned an invalid format."
- Nothing is saved to the database

---

## Coverage Summary

| Test Case | Requirement            | Use Case | Priority |
|-----------|------------------------|----------|----------|
| TC-001    | FR-001, FR-002, FR-003 | UC-01    | High     |
| TC-002    | FR-001                 | UC-01    | Medium   |
| TC-003    | FR-004                 | UC-02    | High     |
| TC-004    | FR-007                 | UC-03    | High     |
| TC-005    | FR-008                 | UC-04    | Medium   |
| TC-006    | FR-009                 | UC-07    | Medium   |
| TC-007    | FR-010                 | UC-05    | High     |
| TC-008    | FR-011                 | UC-06    | Medium   |
| TC-009    | FR-012                 | UC-08    | Medium   |
| TC-010    | FR-003                 | UC-02    | Medium   |