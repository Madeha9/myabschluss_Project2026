# Requirements and User Stories

## Overview

This document defines the functional and non-functional requirements
for the IntelliInvoice system. The system allows users to upload retail
invoice images, automatically extract structured data using Claude Sonnet AI,
store the results in PostgreSQL, and manage invoices through an Angular web interface.

---

## Functional Requirements

### FR-001: Upload Invoice

**Description:** The system shall allow the user to upload an invoice
image (JPG, PNG) or PDF through the Angular web interface via
drag-and-drop or file browser.
**Priority:** High
**Status:** Implemented

### FR-002: Store Original Invoice Image

**Description:** The system shall store the original uploaded invoice
image in AWS S3 and save the image URL in the database.
**Priority:** High
**Status:** Implemented

### FR-003: Extract Invoice Data with AI

**Description:** The system shall send the S3 image URL to Claude Sonnet
AI via LangChain4j to extract store name, date, total amount, VAT,
invoice number, and line items.
**Priority:** High
**Status:** Implemented

### FR-004: Validate Extracted Data

**Description:** The system shall validate that extracted line items + VAT
equal the total amount before saving to the database.
**Priority:** High
**Status:** Implemented

### FR-005: Store Structured Invoice Data

**Description:** The system shall store extracted invoice data and line
items in PostgreSQL with a unique UUID.
**Priority:** High
**Status:** Implemented

### FR-006: Track Return Deadline

**Description:** The system shall automatically calculate the return
status (RETURNABLE, NON_RETURNABLE, SATISFIED) and days remaining
based on the invoice date.
**Priority:** High
**Status:** Implemented

### FR-007: View Invoice List

**Description:** The system shall display all stored invoices in a table
with store name, date, total amount, VAT, status badge, days left,
and image thumbnail.
**Priority:** High
**Status:** Implemented

### FR-008: Search Invoices by Store Name

**Description:** The system shall allow the user to search invoices
by store name using a search field.
**Priority:** Medium
**Status:** Implemented

### FR-009: Filter Invoices by Return Status

**Description:** The system shall allow the user to filter invoices
by return status using filter chips
(Returnable, Non-returnable, Satisfied).
**Priority:** Medium
**Status:** Implemented

### FR-010: View Invoice Detail

**Description:** The system shall display full invoice details including
line items, return window progress bar, and original image from AWS S3.
**Priority:** High
**Status:** Implemented

### FR-011: Delete Invoice

**Description:** The system shall allow the user to delete an invoice
and all its line items from the database with a confirmation step.
**Priority:** Medium
**Status:** Implemented

### FR-012: View Spending Analytics

**Description:** The system shall display monthly spending charts,
quarterly breakdown, and top stores ranking using Chart.js.
**Priority:** Medium
**Status:** Implemented

---

## Non-Functional Requirements

### NFR-001: Maintainability

**Description:** The system shall follow clean architecture principles
with a clear separation between controller, service, and repository layers.
**Priority:** High
**Status:** Implemented

### NFR-002: Data Integrity

**Description:** Extracted invoice data must pass math validation
(items + VAT = total) before being stored in the database.
**Priority:** High
**Status:** Implemented

### NFR-003: Performance

**Description:** Invoice processing should complete within a reasonable
time depending on Claude Sonnet AI response time
(typically under 15 seconds per invoice).
**Priority:** Medium
**Status:** Implemented

### NFR-004: Security

**Description:** AWS credentials and API keys must never be stored
in source code — they must be provided via environment variables only.
**Priority:** High
**Status:** Implemented

### NFR-005: Error Handling

**Description:** The system shall return meaningful error codes
(INVALID_FILE_FORMAT, FILE_UPLOAD_FAILED, INVALID_INVOICE_DATA,
DATABASE_ERROR) for all failure scenarios.
**Priority:** High
**Status:** Implemented

### NFR-006: Usability

**Description:** The Angular UI shall provide clear feedback for every
user action including loading states, success screens, and error messages.
**Priority:** Medium
**Status:** Implemented

---

## User Stories

### US-001: Upload and Process Invoice

**As a** user
**I want** to upload an invoice image or PDF
**So that** the system extracts and stores the invoice data automatically

**Acceptance Criteria:**

- [x] User can select a JPG, PNG, or PDF file via drag-and-drop or file browser
- [x] System validates the file format before processing
- [x] System uploads the original file to AWS S3
- [x] Claude Sonnet AI extracts store name, date, total, VAT, and line items
- [x] Math validation ensures extracted data is accurate
- [x] Success screen displays extracted data for confirmation
- [x] Error message is shown if any step fails with a specific error code

**Priority:** High
**Estimation:** 8 Story Points

---

### US-002: Track Return Deadline

**As a** user
**I want** to see how many days I have left to return each invoice
**So that** I never miss a return deadline

**Acceptance Criteria:**

- [x] Each invoice displays its return status badge
- [x] Days remaining are calculated automatically from the invoice date
- [x] Invoices expiring within 5 days are highlighted with an orange badge
- [x] Expired invoices are shown with a non-returnable badge

**Priority:** High
**Estimation:** 5 Story Points

---

### US-003: View and Manage Invoices

**As a** user
**I want** to view, search, filter, and delete my stored invoices
**So that** I can manage my invoice history efficiently

**Acceptance Criteria:**

- [x] Invoice list displays all invoices with thumbnails and status badges
- [x] User can search by store name
- [x] User can filter by return status
- [x] User can click View to see full invoice details and original image
- [x] User can delete an invoice with a confirmation step

**Priority:** High
**Estimation:** 5 Story Points

---

### US-004: View Spending Analytics

**As a** user
**I want** to see my spending patterns in charts
**So that** I can understand where and how much I am spending

**Acceptance Criteria:**

- [x] Monthly bar chart shows spending per month with a colour per month
- [x] Quarterly donut chart shows spending split across Q1-Q4
- [x] Top stores chart shows the 5 stores with highest total spend
- [x] User can filter charts by year and month

**Priority:** Medium
**Estimation:** 5 Story Points

---

### US-005: View Original Invoice Image

**As a** user
**I want** to view the original invoice image I uploaded
**So that** I can verify the receipt when needed for a return

**Acceptance Criteria:**

- [x] Image thumbnail is shown in the invoice list table
- [x] Clicking the thumbnail opens a modal with the full image
- [x] User can open the original image in a new browser tab
- [x] The AWS S3 URL is displayed in the modal

**Priority:** Medium
**Estimation:** 3 Story Points