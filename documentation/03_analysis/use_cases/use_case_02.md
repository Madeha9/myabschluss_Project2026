# Use Case 02: Extract Invoice Data with AI

## Overview

**ID:** UC-02
**Name:** Extract Invoice Data with AI
**Primary Actor:** System (triggered automatically after UC-01)
**Secondary Actors:** Claude Sonnet AI (via LangChain4j), Quarkus Backend
**Brief Description:** After a successful invoice upload to AWS S3, the system
automatically sends the image URL to Claude Sonnet AI, which extracts structured
invoice data and returns it as a Java object. The system then validates the
extracted data and saves it to the PostgreSQL database.

## Preconditions

- UC-01 completed successfully
- Invoice image is stored in AWS S3 and the URL is available
- Anthropic Claude API is reachable
- LangChain4j is configured with the correct model (claude-3-sonnet)

## Postconditions

- Structured invoice data is saved in the PostgreSQL database:
    - Store name, invoice date, total amount, VAT, invoice number
    - Line items (description, quantity, unit price, line total)
- Return status is calculated (RETURNABLE / NON_RETURNABLE )
- Invoice is assigned a unique UUID and visible in the invoice list

## Main Success Scenario

1. System receives the S3 image URL from UC-01
2. System converts the URL to ASCII format to handle special characters
   in file names
3. System sends the image URL to Claude Sonnet via LangChain4j with
   the system prompt:
   "You are a specialized OCR and accounting assistant.
   Extract data from the provided invoice image.
   Return ONLY valid JSON."
4. Claude Sonnet reads the invoice image and returns a structured
   Java Invoice object via LangChain4j
5. System maps the extracted data to an InvoiceEntity:
    - store name, date, total amount, VAT, currency, invoice number
    - line items mapped to InvoiceItemEntity objects
6. System calls InvoiceReturnService to calculate return status
   and days remaining based on invoice date
7. System calls InvoiceValidationService to validate all extracted fields
8. System saves the InvoiceEntity and all InvoiceItemEntity objects
   to PostgreSQL via InvoiceRepository
9. System returns the saved invoice with its generated UUID
   to the Angular UI

## Alternative Flows

### Alternative Flow 1: Optional Fields Missing

**Condition:** Claude Sonnet could not extract VAT or invoice number
— these fields are optional

1. VAT is stored as null — treated as zero in math validation
2. Invoice number is stored as null — displayed as "—" in the UI
3. Use case continues successfully from step 6

## Exception Flows

### Exception Flow 1: AI Returns Invalid Format

**Condition:** Claude Sonnet cannot read the invoice or returns
text that is not valid JSON

1. LangChain4j throws a parsing exception
2. System catches the exception in InvoiceProcessingService
3. System throws InvoiceValidationException with error code
   INVALID_INVOICE_DATA
4. Angular UI displays:
   "AI Extraction Error: The AI could not read the invoice
   or returned an invalid format."
5. Use case ends in failure — nothing is saved to the database

### Exception Flow 2: Math Validation Fails

**Condition:** Extracted line items + VAT do not equal total amount

1. InvoiceValidationService detects the mismatch
2. System throws InvoiceValidationException with error code
   INVALID_INVOICE_DATA and details:
   "Math error! Items (x) + VAT (y) does not equal Total (z)"
3. Angular UI displays the validation error message
4. Use case ends in failure — nothing is saved to the database

### Exception Flow 3: Required Fields Missing

**Condition:** Claude Sonnet could not extract store name, date,
total amount, or currency

1. InvoiceValidationService detects missing required fields
2. System throws InvoiceValidationException with error code
   INVALID_INVOICE_DATA
3. Angular UI displays the specific missing field error
4. Use case ends in failure — nothing is saved to the database

### Exception Flow 4: Database Save Fails

**Condition:** PostgreSQL is unavailable or a constraint is violated

1. System catches the database exception
2. System throws InvoiceValidationException with error code
   DATABASE_ERROR
3. Angular UI displays: "Failed to save to database."
4. Use case ends in failure

## Special Requirements

- Claude Sonnet must return ONLY valid JSON — no preamble or markdown
- The S3 URL must be ASCII-encoded before sending to the AI
  to handle spaces and special characters in file names
- Math validation is mandatory:
  sum of all line items + VAT must equal total amount
- All validation uses the ErrorCode enum:
  INVALID_INVOICE_DATA, DATABASE_ERROR
- This use case is never triggered manually —
  it is always part of the UC-01 upload flow

## Frequency of Use

Triggered automatically after every successful invoice upload (UC-01)

## Open Issues

- Claude Sonnet may struggle with non-standard or handwritten receipts
- Invoice images with very low resolution or poor lighting
  may reduce extraction accuracy
- Timeout strategy for Claude API response is not yet configured
- Return days per store are not yet extracted by the AI —
  currently managed manually or defaulted