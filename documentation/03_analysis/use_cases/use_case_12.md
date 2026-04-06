# Use Case 12: Confirm Extracted Data Before Saving

## Overview

**ID:** UC-12
**Name:** Confirm Extracted Data Before Saving
**Primary Actor:** User
**Secondary Actors:** Quarkus Backend, PostgreSQL
**Brief Description:** After AI extraction the user reviews
the extracted invoice data and confirms or discards it
before it is saved to the database.

## Preconditions

- UC-02 completed successfully
- Extracted data is displayed in the Angular UI

## Postconditions

- If confirmed: invoice is saved to the database
- If discarded: nothing is saved and user can upload again

## Main Success Scenario

1. Angular UI displays the extracted data:
   store name, date, total, VAT, invoice number, status
2. User reviews the extracted fields
3. User clicks "Confirm and save"
4. System saves the invoice to the database
5. Angular UI displays the success screen

## Alternative Flows

### Alternative Flow 1: User Discards

**Condition:** Extracted data is incorrect or unwanted

1. User clicks "Discard"
2. Nothing is saved to the database
3. User is returned to the upload screen

## Frequency of Use

Every time an invoice is successfully processed by the AI