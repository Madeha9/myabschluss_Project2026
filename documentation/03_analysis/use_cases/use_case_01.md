# Use Case 01: Upload Invoice

## Overview

**ID:** UC-01
**Name:** Upload Invoice
**Primary Actor:** User
**Secondary Actors:** AWS S3 (Cloud Storage), Quarkus Backend API, Claude Sonnet AI
**Brief Description:** The user uploads an invoice image or PDF through the Angular web
interface. The system validates the file, uploads it to AWS S3, sends it to Claude Sonnet
for data extraction, validates the extracted data, and saves everything to the database.

## Use Case Diagram

![use_case_01.png](../Diagrams%26Images/use_case_diagrams/use_case_png/use_case_01.png)

## Preconditions

- User has a readable invoice image (JPG, PNG) or PDF file saved on their device
- The Quarkus backend is running and reachable at http://localhost:8080
- AWS S3 bucket is accessible and credentials are configured
- Anthropic Claude API is available

## Postconditions

- Original invoice image is stored in AWS S3
- Extracted invoice data (store name, date, total, VAT, invoice number, line items)
  is saved in the PostgreSQL database
- Invoice is assigned a unique UUID and is visible in the invoice list
- Return status is automatically calculated and assigned

## Main Success Scenario

1. User navigates to the Upload page in the Angular UI
2. User selects an invoice file (JPG, PNG, or PDF) via drag-and-drop or file browser
3. User clicks "Process invoice"
4. System validates the file format and content type
5. System uploads the file to AWS S3 and receives the image URL
6. System sends the S3 image URL to Claude Sonnet AI for data extraction
7. Claude Sonnet returns structured invoice data as a Java object
8. System validates the extracted data:
    - Store name and date must be present
    - Total amount must be positive
    - Line items + VAT must equal the total amount
9. System calculates the return status based on invoice date and return days
10. System saves the invoice and line items to the PostgreSQL database
11. Angular UI displays a success screen with the extracted data

## Alternative Flows

### Alternative Flow 1: PDF Upload

**Condition:** User submits a PDF invoice instead of an image

1. User selects a PDF file
2. System validates PDF format and content type
3. Continue at step 5 in main flow

## Exception Flows

### Exception Flow 1: Unsupported File Type

**Condition:** File is not JPG, PNG, or PDF

1. System rejects the file in the validation step
2. Backend returns error code `INVALID_FILE_FORMAT`
3. Angular UI displays: "Unsupported format. Please use PDF, PNG, or JPG."
4. Use case ends in failure

### Exception Flow 2: AWS S3 Upload Fails

**Condition:** Cloud storage is unavailable or credentials are invalid

1. System catches the upload exception
2. Backend returns error code `FILE_UPLOAD_FAILED`
3. Angular UI displays the error message
4. Use case ends in failure

### Exception Flow 3: AI Extraction Fails

**Condition:** Claude Sonnet cannot read the invoice or returns invalid data

1. System catches the AI extraction exception
2. Backend returns error code `INVALID_INVOICE_DATA`
3. Angular UI displays: "AI could not read the invoice or returned an invalid format."
4. Use case ends in failure

### Exception Flow 4: Math Validation Fails

**Condition:** Extracted line items + VAT do not equal the total amount

1. System detects the mismatch during validation
2. Backend returns error code `INVALID_INVOICE_DATA` with details of the mismatch
3. Angular UI displays the validation error
4. Use case ends in failure

### Exception Flow 5: Database Save Fails

**Condition:** PostgreSQL is unavailable or a database error occurs

1. System catches the database exception
2. Backend returns error code `DATABASE_ERROR`
3. Angular UI displays an error message
4. Use case ends in failure

## Special Requirements

- Original invoice image is stored in AWS S3 — no binary data in the database
- Only the S3 URL is saved in the database as a TEXT field
- File validation checks both file extension and MIME content type
- Math validation is mandatory before any data is saved

## Frequency of Use

Frequent — multiple times per user whenever a new invoice is submitted

## Open Issues

- Maximum file size limit is not yet enforced on the frontend
- Camera capture directly in the browser is not supported in the current version —
  users must upload an existing file from their device