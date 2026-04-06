# Use Case 05: View Invoice Detail

## Overview

**ID:** UC-05
**Name:** View Invoice Detail
**Primary Actor:** User
**Secondary Actors:** Quarkus Backend, PostgreSQL
**Brief Description:** The user opens a single invoice to view
full extracted data, line items, return window progress,
and the original image from AWS S3.

## Preconditions

- Invoice exists in the database
- User is on the Invoice List page

## Postconditions

- Full invoice details are displayed including original image

## Main Success Scenario

1. User clicks the View button on an invoice row
2. System calls GET /myinvoices/{id}
3. Backend retrieves the invoice and calculates return status
4. Angular UI displays:
    - Store name, date, total, VAT, currency, invoice number
    - Return status and days remaining
    - Return window progress bar
    - Line items table
    - Original invoice image loaded from AWS S3 URL

## Exception Flows

### Exception Flow 1: Invoice Not Found

**Condition:** Invoice ID does not exist in the database

1. Backend returns 404 with error code INVOICE_NOT_FOUND
2. Angular UI displays an error message

## Frequency of Use

Frequent — whenever user needs to check invoice details