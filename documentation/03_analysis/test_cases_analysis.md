# Test Cases Analysis

## Overview
This document contains test cases derived from the analysis phase, based on functional requirements and use cases 
for the IntelliInvoice system.

## Test Case Template


### TC-A-001: Upload & Process Valid Invoice Image
**Related Requirement:** FR-001
**Related Use Case:** UC-01
**Priority:** High
**Type:** Functional

**Preconditions:**
- User is on the Upload Invoice view
- A valid invoice image or PDF  file is available (JPG/PNG/PDF)

**Test Steps:**
1. Click **Select File**
2. Choose a valid invoice image or PDF file  (JPG/PNG)
3. Click **Upload & Process**

**Expected Results:**
- Upload succeeds and status shows **Processing → Done**
- Extracted invoice data is saved and appears in the invoice list

**Test Data:**
- Valid JPG invoice image


### TC-A-002: Reject Unsupported File Type
**Related Requirement:** FR-002
**Related Use Case:** UC-01
**Priority:** Medium
**Type:** Functional

**Preconditions:**
- User is on the Upload Invoice view
- An unsupported file is available (DOC/TXT)

**Test Steps:**
1. Click **Select File**
2. Choose an unsupported file (e.g., TXT,Doc)
3. Click **Upload & Process**

**Expected Results:**
- Upload is rejected
- User sees a clear error message (e.g., "Unsupported file type")

**Test Data:**
- Unsupported file type (e.g., .doc, .txt, .mp3)

### TC-A-003: View Invoice List
**Related Requirement:** FR-005
**Related Use Case:** UC-02
**Priority:** High
**Type:** Functional

**Preconditions:**
- At least one invoice was processed successfully

**Test Steps:**
1. Open **Invoice List**
2. Check the displayed list items

**Expected Results:**
- Invoice list shows saved invoices (ID, Date, Store, Total)
- Each row has **Open** and **Delete** actions

**Test Data:**
- Existing invoice record in database

### TC-A-004: View Invoice Details
**Related Requirement:** FR-006
**Related Use Case:** UC-03
**Priority:** High
**Type:** Functional

**Preconditions:**
- At least one invoice exists in the invoice list

**Test Steps:**
1. In the invoice list, click **Open** for an invoice
2. Review the details view

**Expected Results:**
- Invoice details are shown (structured extracted fields)
- Back button returns to invoice list

**Test Data:**
- Invoice created from  a valid JPG invoice image

### TC-A-005: Delete Invoice
**Related Requirement:** FR-007
**Related Use Case:** UC-04
**Priority:** Medium
**Type:** Functional

**Preconditions:**
- At least one invoice exists in the invoice list

**Test Steps:**
1. In the invoice list, click **Delete** for an invoice
2. Confirm deletion 

**Expected Results:**
- Invoice is removed from the list
- Invoice record is removed from database and image/PDF file  removed from storage 

**Test Data:**
- Existing invoice record

### TC-A-006: Handle LLM Extraction Failure
**Related Requirement:** FR-003
**Related Use Case:** UC-01
**Priority:** Medium
**Type:** Functional

**Preconditions:**
- User is on Upload Invoice view
- A low-quality/invalid invoice image is available

**Test Steps:**
1. Click **Select File**
2. Choose a low-quality image
3. Click **Upload & Process**

**Expected Results:**
- System shows an error or "Processing failed"
- No incorrect invoice data is stored

**Test Data:**
- Blurry or invalid invoice image


## Test Cases by Requirement

### Requirement FR-001
- TC-A-001

### Requirement FR-002
- TC-A-002

### Requirement FR-005
- TC-A-003

### Requirement FR-006
- TC-A-004

### Requirement FR-007
- TC-A-005

### Requirement FR-003
- TC-A-006


## Test Cases by Use Case

### Use Case UC-01
- TC-A-001
- TC-A-002
- TC-A-003
- TC-A-006

### Use Case UC-04
- TC-A-005
- 
### Use Case UC-03
- TC-A-004


## Coverage Analysis
All main functional requirements are covered by at least one test case.  The key user flows (upload/process, view list,
view details, delete) are tested, including one negative scenario (LLM extraction failure).
