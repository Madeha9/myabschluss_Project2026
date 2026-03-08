# Use Case 03: View and Manage Invoices

## Overview
**ID:** UC-03  
**Name:** View and Manage Invoices  
**Primary Actor:** User  
**Secondary Actors:** Backend API  
**Brief Description:** The user views, searches, and deletes stored invoices.

## Preconditions
- At least one invoice is stored in the database

## Postconditions
- Requested invoice data is displayed
- Invoice may be deleted if requested

## Main Success Scenario
1. User requests a list of invoices.
2. System retrieves invoice metadata from the database.
3. User selects an invoice.
4. System returns detailed invoice information.
5. User optionally deletes an invoice.

## Alternative Flows

### Alternative Flow 1: Search Invoices
**Condition:** User provides search parameters
1. System filters invoices by date or store name.
2. Filtered results are returned.
3. Return to step 3 in main flow.

## Exception Flows

### Exception Flow 1: Invoice Not Found
**Condition:** Requested invoice ID does not exist
1. System returns error message.
2. Use case ends in failure.

## Special Requirements
- Fast query performance
- Secure access to invoice data

## Frequency of Use
Frequent (regular invoice review)

## Open Issues
- Pagination strategy
- Sorting options
