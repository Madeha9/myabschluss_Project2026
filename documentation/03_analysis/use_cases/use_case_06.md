# Use Case 06: Delete Invoice

## Overview

**ID:** UC-06
**Name:** Delete Invoice
**Primary Actor:** User
**Secondary Actors:** Quarkus Backend, PostgreSQL
**Brief Description:** The user deletes a stored invoice
from the database.

## Use Case Diagram

![use_case_06.png](../Diagrams%26Images/use_case_diagrams/use_case_png/use_case_06.png)

## Preconditions

- Invoice exists in the database
- User is on the Invoice List or Invoice Detail page

## Postconditions

- Invoice and all its line items are removed from the database
- Invoice list is updated

## Main Success Scenario

1. User clicks the Delete button on an invoice
2. Angular UI shows a confirmation dialog
3. User confirms the deletion
4. System calls DELETE /myinvoices/{id}
5. Backend deletes the invoice and all related line items
   (CascadeType.ALL)
6. Angular UI removes the invoice from the table

## Alternative Flows

### Alternative Flow 1: User Cancels

**Condition:** User clicks Cancel in the confirmation dialog

1. Dialog closes
2. Invoice is not deleted
3. Use case ends without changes

## Exception Flows

### Exception Flow 1: Invoice Not Found

**Condition:** Invoice ID does not exist

1. Backend returns error code INVOICE_NOT_FOUND
2. Angular UI displays an error message

## Frequency of Use

Occasional — when user wants to remove an invoice