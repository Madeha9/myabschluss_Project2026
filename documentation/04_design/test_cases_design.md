# Test Cases Design

## Overview

This document defines test cases for IntelliInvoice backend.
The focus is on unit testing, integration testing, and basic system validation

## Unit Test Cases

### Component: InvoiceProcessingService

#### TC-D-001: Process valid invoice image

**Component:** InvoiceProcessingService  
**Type:** Unit  
**Priority:** High

**Preconditions:**

- Valid invoice image provided
- LLM service returns structured JSON

**Test Steps:**

1. Call processInvoice() with valid image.
2. Mock LLM response.
3. Validate returned invoice object.

**Expected Results:**

- Invoice object is created.
- Extracted fields (date, total, store name) are correctly mapped.

**Test Data:**

- Sample invoice image
- Mocked LLM JSON response

#### TC-D-002: Handle invalid LLM response

**Component:** InvoiceProcessingService  
**Type:** Unit  
**Priority:** High

**Preconditions:**

- LLM returns incomplete or invalid JSON.

**Test Steps:**

1. Call processInvoice().
2. Simulate invalid LLM response.

**Expected Results:**

- Error is handled properly.
- No invoice is saved to database.

**Test Data:**

- Mock invalid JSON response

### Component: InvoiceRepository

#### TC-D-003: Save and retrieve invoice

**Component:** InvoiceRepository  
**Type:** Unit  
**Priority:** Medium

**Preconditions:**

- Valid invoice entity created.

**Test Steps:**

1. Save invoice.
2. Retrieve invoice by ID.

**Expected Results:**

- Retrieved invoice matches saved data.

**Test Data:**

- Sample invoice entity

## Integration Test Cases

### Integration Point: UploadController ↔ InvoiceProcessingService

#### TC-D-101: Upload and process invoice successfully

**Type:** Integration  
**Priority:** High

**Preconditions:**

- Backend running
- Database connected

**Test Steps:**

1. Send HTTP POST request with invoice image.
2. Trigger processing flow.

**Expected Results:**

- HTTP 200 response.
- Invoice stored in database.
- Image stored in storage.

#### TC-D-102: Upload fails with invalid file

**Type:** Integration  
**Priority:** Medium

**Preconditions:**

- Backend running

**Test Steps:**

1. Send POST request with invalid file type.

**Expected Results:**

- HTTP 400 error response.
- No data stored.

## System Test Cases

#### TC-D-201: Full invoice processing flow

**Type:** System  
**Priority:** High

**Test Steps:**

1. Upload invoice via API.
2. Verify extraction.
3. Retrieve invoice via GET endpoint.

**Expected Results:**

- Invoice appears in list endpoint.
- Stored data matches extracted data.

#### TC-D-202: View invoice details

**Type:** System  
**Priority:** Medium

**Test Steps:**

1. Request invoice by ID.

**Expected Results:**

- Correct invoice details returned.
- HTTP 200 response.

## Performance Test Cases

#### TC-D-301: Invoice processing response time

**Metric:** Response time  
**Target:** < 5 seconds per invoice

**Test Case Details:**

- Upload invoice and measure total processing time.
- Ensure system responds within acceptable limit.

## Security Test Cases

#### TC-D-401: Secure API access

**Security Aspect:** HTTPS communication

**Test Case Details:**

- Ensure API is accessible only via HTTPS.
- Verify no sensitive data (API keys, passwords) appears in logs.


## Coverage Analysis

- Core business logic (InvoiceProcessingService) fully covered by unit tests.
- Database operations tested.
- Main API endpoints covered by integration tests.
- End-to-end flow validated by system tests.

## Test Automation Strategy

- Unit tests implemented using JUnit and Mockito.
- Integration tests using REST endpoint testing.
- Tests executed automatically during build process.


## Security Test Cases

### TC-D-401: [Test Case Name]
**Security Aspect:** [Authentication/Authorization/Encryption/etc.]
[Test case details]

## Coverage Analysis
[Analysis of test coverage for components and design elements]

## Test Automation Strategy
[Describe which tests will be automated and how]
