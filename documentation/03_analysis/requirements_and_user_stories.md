# Requirements and User Stories

## Overview
This document defines the functional and non-functional requirements for the IntelliInvoice system. 
The system processes retail invoice images (clothing and shoes), extracts structured data using an LLM service,
and stores the results in a relational database.
## Functional Requirements

### FR-001: Upload Invoice
**Description:** The system shall allow the user to upload an invoice image (JPG/PNG)
**Priority:** High
**Status:** Approved

### FR-002: Store Original Invoice Image
**Description:** The system shall store the original uploaded invoice image in cloud storage.
**Priority:** High
**Status:**  Approved

### FR-003: Extract Invoice Data via LLM
**Description:** The system shall send the invoice image to an LLM service to extract structured invoice
data (store name, date, total amount, items,etc...).
**Priority:** High
**Status:** Approved

### FR-004: Store Structured Data
**Description:** The system shall store extracted invoice data in a relational database.
**Priority:** High
**Status:** Approved

### FR-005: View Invoice List
**Description:** The system shall display a list of all processed invoices including basic information (ID, date, store, total).
**Priority:** High
**Status:** Approved

### FR-006: View Invoice Details
**Description:** The system shall allow the user to view detailed structured data of a selected invoice.
**Priority:** High
**Status:** Approved

### FR-007: Delete Invoice
**Description:** The system shall allow the user to delete an invoice and its associated data.
**Priority:** Medium
**Status:** Approved

## Non-Functional Requirements

### NFR-001: Cloud Deployment
**Description:** The system must be deployable in a cloud environment
**Priority:** High
**Status:** Approved

### NFR-002: Maintainability
**Description:** The system must follow clean architecture principles to ensure maintainability and extensibility.
**Priority:** High
**Status:** Approved

### NFR-003: Performance
**Description:** Invoice processing should complete within a reasonable time (e.g., under 10 seconds per invoice, depending on LLM response time).
**Priority:** Medium
**Status:** Draft

### NFR-004: Data Integrity
**Description:** Extracted invoice data must be validated before being stored in the database.
**Priority:** High
**Status:** Approved

### NFR-005: Security
**Description:** The system must securely handle uploaded files and protect sensitive configuration data.
**Priority:** Medium
**Status:** Draft


# User Stories

## US-001: Upload and Process Invoice
**As a** user  
**I want** to upload an invoice file (image or PDF) 
**So that** the system can extract and store structured invoice data automatically

**Acceptance Criteria:**
- [ ] User can select an invoice image or PDF file
- [ ] System uploads and stores the original image/PDF in cloud storage
- [ ] System sends image/PDF to LLM service
- [ ] Extracted data is stored in the database
- [ ] Success or error message is displayed

**Priority:** High  
**Estimation:** 8 Story Points


## US-002: View Invoice List
**As a** user  
**I want** to see a list of all processed invoices  
**So that** I can manage and review them

**Acceptance Criteria:**
- [ ] System displays invoice list
- [ ] List includes ID, date, store, and total
- [ ] User can select an invoice

**Priority:** High  
**Estimation:** 5 Story Points


## US-003: View Invoice Details
**As a** user  
**I want** to see detailed information of a specific invoice  
**So that** I can review extracted data

**Acceptance Criteria:**
- [ ] User can click "Open" on an invoice
- [ ] System displays structured invoice details
- [ ] User can return to invoice list

**Priority:** High  
**Estimation:** 3 Story Points



## US-004: Delete Invoice
**As a** user  
**I want** to delete an invoice  
**So that** I can remove incorrect or unnecessary data

**Acceptance Criteria:**
- [ ] User can click "Delete"
- [ ] System removes invoice from database
- [ ] System removes associated image from cloud storage
- [ ] Confirmation message is displayed

**Priority:** Medium  
**Estimation:** 3 Story Points
